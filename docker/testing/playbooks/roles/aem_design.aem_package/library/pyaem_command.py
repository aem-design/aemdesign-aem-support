#!/usr/bin/python

import docker
import json

from ansible.module_utils.basic import AnsibleModule


def main():
    fields = {
        "host": {"required": True, "type": "str"},
        "port": {"required": True, "type": "str"},
        "group_name": {"required": True, "type": "str"},
        "package_name": {"required": True, "type": "str"},
        "package_version": {"required": True, "type": "str"},
        "aem_username": {"required": True, "type": "str"},
        "aem_password": {"required": True, "type": "str", "no_log": True},
        "file_path": {"required": False, "type": "str"},
        "file_url": {"required": False, "type": "str"},
        "file_name": {"required": False, "type": "str"},
        "file_url_password": {"required": False, "type": "str", "no_log": True},
        "file_url_username": {"required": False, "type": "str"},
        "api_command": {"required": True, "type": "str"},
        "docker_host": {"required": False, "type": "str", "default": "unix://var/run/docker.sock"}
    }

    module = AnsibleModule(argument_spec=fields)

    # stick everything into a dictionary for easy reuse
    params = dict(
        host=module.params['host'],
        port=module.params['port'],
        group_name=module.params['group_name'],
        package_name=module.params['package_name'],
        package_version=module.params['package_version'],
        file_path=module.params['file_path'],
        file_url=module.params['file_url'],
        file_name=module.params['file_name'],
        file_url_password=module.params['file_url_password'],
        file_url_username=module.params['file_url_username'],

        aem_username=module.params['aem_username'],
        aem_password=module.params['aem_password'],
        api_command=module.params['api_command'],
        docker_host=module.params['docker_host'],

        container_image='aemdesign/ansible-playbook:centos7',
        container_image_volume='/ansible/playbooks',
        container_entrypoint='/usr/bin/python',
        container_user='root',
        container_remove=True,
        container_privileged=True,
        # mount package path into the container.
        container_volume="%s:/ansible/playbooks" % (
            '/tmp' if module.params['file_path'] is None else module.params['file_path'])
    )

    client = docker.APIClient(base_url=params["docker_host"])

    hostconfig = client.create_host_config()

    params['getfile'] = ""
    getfile = ""

    if params['file_url']:
        if params['file_url_username']:
            getfile = "password_mgr = urllib.request.HTTPPasswordMgrWithDefaultRealm(); " \
                      "password_mgr.add_password(None, " \
                      "'%(file_url)s', " \
                      "'%(file_url_username)s', " \
                      "'%(file_url_password)s'); " \
                      "auth_handler = urllib.request.HTTPBasicAuthHandler(password_mgr); " \
                      "opener = urllib.request.build_opener(auth_handler); " \
                      "urllib.request.install_opener(opener); "
        getfile += "urllib.request.urlretrieve('%(file_url)s', '%(file_name)s');"
        params['getfile'] = getfile % params

    # create pyaem2 init command
    params['pyaem_init'] = "aem = pyaem2.PyAem2('%(aem_username)s'," \
                           " '%(aem_password)s', '%(host)s', '%(port)s'); " % params
    params['pyaem_error'] = "print( json.dumps({ 'failed': True, 'msg': result.message }) if " \
                            "result.is_failure() else json.dumps({ 'msg': result.message }) ) "

    params['pyaem_cmd'] = ''
    params['pyaem_cmd1'] = ''

    # create pyaem function command
    if any(True for match in
           [
               'is_package_installed',
               'is_package_uploaded',
               'install_package_sync'
           ]
           if match in params['api_command']
           ):
        params['pyaem_cmd'] = "result = aem.%(api_command)s('%(group_name)s', '%(package_name)s', " \
                              "'%(package_version)s' ); " % params
    elif 'upload_package_sync' in params['api_command']:
        if not params['file_path']:
            module.fail_json(msg={
                'msg': 'Missing file Path',
                'api_command': params['api_command']
            })
            return False

        hostconfig = client.create_host_config(privileged=params['container_privileged'], binds=[
            params['container_volume']
        ])
        params['pyaem_cmd'] = "result = aem.upload_package_sync(" \
                              "'%(group_name)s', " \
                              "'%(package_name)s', " \
                              "'%(package_version)s', " \
                              "'/ansible/playbooks', " \
                              "force = 'true'); " % params
    else:
        module.fail_json(
            failed=True,
            msg={
                'msg': 'Unsuported api_command',
                'api_command': params['api_command']
            }
        )
        return False

    # join pyaem init and function commands
    params['pyaemscript'] = "-c \"import json; import pyaem2; import urllib.request; " \
                            "%(getfile)s " \
                            "%(pyaem_init)s " \
                            "%(pyaem_cmd)s " \
                            "%(pyaem_error)s \"" % params

    # create helper for debugging
    params['pyaemscriptmanual'] = "docker run -it --privileged " \
                                  "--entrypoint %(container_entrypoint)s " \
                                  "-v %(container_volume)s " \
                                  "%(container_image)s " \
                                  "%(pyaemscript)s" % params

    container = client.create_container(
        image=params['container_image'],
        command=params['pyaemscript'],
        user=params['container_user'],
        volumes=[params['container_image_volume']],
        entrypoint=[params['container_entrypoint']],
        host_config=hostconfig
    )
    containerstart = client.start(container=container.get('Id'))

    logs = ''
    for char in client.logs(container=container, stream=True):
        logs = logs + char.decode('utf-8')

    try:
        logsjson = json.loads(logs)
    except Exception as err:  # noqa: F841
        logsjson = {}

    if 'failed' in logsjson:
        stepFailed = logsjson['failed']
    else:
        stepFailed = False

    if params['container_remove']:
        client.remove_container(container=container.get('Id'))

    result = {
        'cmd': params['pyaemscript'],
        'logs': logs,
        'container': container,
        'test': params['pyaemscriptmanual'],
        'status': containerstart
    }

    module.exit_json(
        failed=stepFailed,
        dd=stepFailed,
        msg=logsjson,
        result=result
    )
    return True


if __name__ == '__main__':
    main()
