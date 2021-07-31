#!/usr/bin/python

from ansible.module_utils.basic import AnsibleModule

import pyaem2


def main():
    fields = {
        "host": {"required": True, "type": "str"},
        "port": {"required": True, "type": "str"},
        "group_name": {"required": True, "type": "str"},
        "package_name": {"required": True, "type": "str"},
        "package_version": {"required": True, "type": "str"},
        "aem_username": {"required": True, "type": "str"},
        "aem_password": {"required": True, "type": "str", "no_log": True},
        "file_path": {"required": True, "type": "str"}
    }

    module = AnsibleModule(argument_spec=fields)

    host = module.params['host']
    port = module.params['port']
    group_name = module.params['group_name']
    package_name = module.params['package_name']
    package_version = module.params['package_version']
    file_path = module.params['file_path']

    aem_username = module.params['aem_username']
    aem_password = module.params['aem_password']

    try:
        aem = pyaem2.PyAem2(aem_username, aem_password, host, port)
        result = aem.upload_package_sync(group_name, package_name, package_version, file_path, force='true')

        module.exit_json(
            failed=False,
            changed=True,
            msg=result.message
        )

    except pyaem2.PyAem2Exception as err:
        module.fail_json(
            failed=True,
            changed=False,
            username=aem_username,
            msg=str(err)
        )


if __name__ == '__main__':
    main()
