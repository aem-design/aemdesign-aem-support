# Ansible Role: AEM

[![build](https://github.com/aem-design/ansible-role-aem/actions/workflows/build.yml/badge.svg)](https://github.com/aem-design/ansible-role-aem/actions/workflows/build.yml)
[![Ansible Role](https://img.shields.io/ansible/role/d/43185)](https://galaxy.ansible.com/aem_design/aem/)
[![Ansible Role](https://img.shields.io/ansible/role/43185)](https://galaxy.ansible.com/aem_design/aem/)

Create AEM Containers in your stack.
> This role was developed as part of
> [AEM.Design](http://aem.design/)

## Requirements

None.

## Role Variables

| Name                   	| Required 	| Default                                                                                                                                              	| Notes                                	|
|------------------------	|----------	|------------------------------------------------------------------------------------------------------------------------------------------------------	|--------------------------------------	|
| docker_image_user      	|          	| aemdesign                                                                                                                                            	|                                      	|
| docker_image_name      	|          	| aem                                                                                                                                                  	|                                      	|
| docker_image           	|          	| {{ docker_image_user }}/{{ docker_image_name }}                                                                                                      	|                                      	|
| docker_image_tag       	|          	| 6.5.0                                                                                                                                                	|                                      	|
| docker_container_name  	|          	| author                                                                                                                                               	|                                      	|
| docker_timezone        	|          	| Australia/Melbourne                                                                                                                                  	|                                      	|
|                        	|          	|                                                                                                                                                      	|                                      	|
| aem_port               	|       	| 4502                                                                                                                                                 	|                                      	|
| aem_debug_port         	|          	| 58242                                                                                                                                                	|                                      	|
| aem_imageserver_port   	|          	| 57345                                                                                                                                                	|                                      	|
| aem_debug              	|          	| false                                                                                                                                                	|                                      	|
| aem_debug_opts         	|          	| -Dcom.sun.management.jmxremote.port={{ aem_debug_port }} -Dcom.sun.management.jmxremote.ssl=false -Dcom.sun.management.jmxremote.authenticate=false  	|                                      	|
|                        	|          	|                                                                                                                                                      	|                                      	|
| aem_runmode            	|          	| -Dsling.run.modes=author,crx3,crx3tar,nosamplecontent                                                                                                	|                                      	|
| aem_jvm_opts           	|          	| -server -Xms1024m -Xmx1024m -XX:MaxDirectMemorySize=256M -XX:+CMSClassUnloadingEnabled -Djava.awt.headless=true -Dorg.apache.felix.http.host=0.0.0.0 	|                                      	|
| aem_start_opts         	|          	| start -c /aem/crx-quickstart -i launchpad -p 8080 -a 0.0.0.0 -Dsling.properties=conf/sling.properties                                                	|                                      	|
|                        	|          	|                                                                                                                                                      	|                                      	|
|                        	|          	|                                                                                                                                                      	|                                      	|
| docker_published_ports 	|          	|                                                                                                                                                      	|                                      	|
|                        	|          	| "0.0.0.0:{{ aem_port }}:8080/tcp",                                                                                                                   	|                                      	|
|                        	|          	| "0.0.0.0:{{ aem_debug_port }}:58242/tcp",                                                                                                            	|                                      	|
|                        	|          	| "0.0.0.0:{{ aem_imageserver_port }}:57345/tcp"                                                                                                       	|                                      	|
|                        	|          	|                                                                                                                                                      	|                                      	|
| docker_volumes         	|          	|                                                                                                                                                      	|                                      	|
|                        	|          	| "author-repository:/aem/crx-quickstart/repository:z",                                                                                                	|                                      	|
|                        	|          	| "author-logs:/aem/crx-quickstart/logs:z",                                                                                                            	|                                      	|
|                        	|          	| "author-backup:/aem/backup:z"                                                                                                                        	|                                      	|
|                        	|          	|                                                                                                                                                      	|                                      	|
| docker_host                |           | unix://var/run/docker.sock | host where to run the docker container |
|                        	|          	|                                                                                                                                                      	|                                      	|


## Dependencies

This role depends on roles, you will need to add these to your ansible dependencies and download before running this role:
 
- `aem_design.aem_license`
- `aem_design.aem_package`
- `aem_design.aem_verify`

## Example Playbook

This will start aem with default ports and without volumes mounted

```yaml
  hosts: all
  vars:
    aem_access_port: "4502"
    aem_access_host: "localhost"
    aem_register_license_key: "{{ service_aem_license_key }}"
    aem_register_license_name: "{{ service_aem_license_name }}"
    test_debug_hide: true
  tasks:
    - name: run aem author istance
      include_role:
        name: aem_design.aem
      vars:
        docker_published_ports: [
          "5502:8080",
          "59242:58242",
          "59345:57345"
        ]
        docker_volumes: []
        docker_container_name: "{{ test_container_name }}"
        debug_hide: "{{ test_debug_hide }}"
        docker_host: "unix://tmp/docker.sock"
    - name: ensure container is registered
      include_role:
        name: aem_design.aem_license
      vars:
        aem_license_key: "{{ service_aem_license_key }}"
        aem_license_name: "{{ service_aem_license_name }}"
        aem_port: "{{ aem_access_port }}"
        aem_host: "{{ aem_access_host }}"
        debug_hide: "{{ test_debug_hide }}"
    - name: ensure instance is ready
      include_role:
        name: aem_design.aem_verify
      vars:
        aem_port: "{{ aem_access_port }}"
        aem_host: "{{ aem_access_host }}"
        debug_hide: "{{ test_debug_hide }}"
    - name: install aem packages
      include_role:
        name: aem_design.aem_package
      vars:
        aem_port: "{{ aem_access_port }}"
        aem_host: "{{ aem_access_host }}"
        maven_repository_url: "{{ item.maven_repository_url | default('') }}"
        debug_hide: "{{ test_debug_hide }}"
        simple_name: "{{ item.simple_name }}"
        group_name: "{{ item.group_name }}"
        package_version: "{{ item.package_version }}"
        package_name: "{{ item.package_name }}"
        package_url: "{{ item.package_url }}"
        file_name: "{{ item.file_name }}"
        file_url_username: "{{ item.file_url_username | default('') }}"
        file_url_password: "{{ item.file_url_password | default('') }}"
        install_package_ansible: "{{ item.install_package_ansible }}"
        install_package_docker: "{{ item.install_package_docker }}"
        docker_host: "{{ item.docker_host }}"
      loop: [
        {
          maven_repository_url: "http://{{ dockerhost_ip.stdout }}",
          debug_hide: "false",
          simple_name: "vanityurls using docker",
          group_name: "Adobe",
          package_version: "1.0.2",
          package_name: "vanityurls-components",
          package_url: "https://www.adobeaemcloud.com/content/companies/public/adobe/packages/\
                            cq600/component/vanityurls-components/jcr%3acontent/package/file.res/vanityurls-components-1.0.2.zip",
          file_name: "vanityurls-components-1.0.2.zip",
          file_url_username: "{{ service_adobe_cloud_username }}",
          file_url_password: "{{ service_adobe_cloud_password }}",
          install_package_ansible: false,
          install_package_docker: true,
          docker_host: "unix://tmp/docker.sock"
        },
        {
          debug_hide: "false",
          simple_name: "acs twitter using docker",
          group_name: "day_internal/consulting",
          package_version: "1.0.0",
          package_name: "com.adobe.acs.bundles.twitter4j-content",
          package_url: "https://github.com/Adobe-Consulting-Services/com.adobe.acs.bundles.twitter4j/\
                          releases/download/com.adobe.acs.bundles.twitter4j-1.0.0/com.adobe.acs.bundles.twitter4j-content-1.0.0.zip",
          file_name: "com.adobe.acs.bundles.twitter4j-content-1.0.0.zip",
          install_package_ansible: true,
          install_package_docker: false,
          docker_host: "unix://tmp/docker.sock"
        },
        {
          maven_repository_url: "http://{{ dockerhost_ip.stdout }}",
          debug_hide: "false",
          simple_name: "vanityurls using docker",
          group_name: "Adobe",
          package_version: "1.0.2",
          package_name: "vanityurls-components",
          package_url: "https://www.adobeaemcloud.com/content/companies/public/adobe/packages/\
                            cq600/component/vanityurls-components/jcr%3acontent/package/file.res/vanityurls-components-1.0.2.zip",
          file_name: "vanityurls-components-1.0.2.zip",
          file_url_username: "{{ service_adobe_cloud_username }}",
          file_url_password: "{{ service_adobe_cloud_password }}",
          install_package_ansible: true,
          install_package_docker: false,
          docker_host: "unix://tmp/docker.sock"
        },
        {
          debug_hide: "false",
          simple_name: "acs twitter using docker",
          group_name: "day_internal/consulting",
          package_version: "1.0.0",
          package_name: "com.adobe.acs.bundles.twitter4j-content",
          package_url: "https://github.com/Adobe-Consulting-Services/com.adobe.acs.bundles.twitter4j/\
                          releases/download/com.adobe.acs.bundles.twitter4j-1.0.0/com.adobe.acs.bundles.twitter4j-content-1.0.0.zip",
          file_name: "com.adobe.acs.bundles.twitter4j-content-1.0.0.zip",
          install_package_ansible: false,
          install_package_docker: true,
          docker_host: "unix://tmp/docker.sock"
        }
      ]
```

## Role Info

To check role info run the following:

```bash
docker run --rm aemdesign/ansible-playbook:centos7 ansible-galaxy info aem_design.aem
```

## License

Apache 2.0

## Author Information

This role was created by [Max Barrass](https://aem.design/).
