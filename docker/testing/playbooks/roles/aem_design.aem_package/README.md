# Ansible Role: AEM Package

[![build](https://github.com/aem-design/ansible-role-aem-package/actions/workflows/build.yml/badge.svg)](https://github.com/aem-design/ansible-role-aem-package/actions/workflows/build.yml)

Install Content Packages to AEM Instance.
> This role was developed as part of
> [AEM.Design](http://aem.design/)

## Requirements

None.

## Role Variables

Available variables are listed below, along with default values (see `defaults/main.yml`):

| Name                       	| Required 	| Default   	| Notes                                                   	|
|----------------------------	|----------	|-----------	|---------------------------------------------------------	|
| aem_host                   	| yes      	| localhost 	| aem host to use                                           |
| aem_port                   	| yes      	| 4502      	| aem host port to use                                      |
| aem_username               	| yes      	| admin     	| user name to use for aem host                             |
| aem_password               	| yes      	| admin     	| password to use for aem host                              |
|                           	|          	|           	|                                                       	|
| maven_repository_url          |       	| http://localhost | will be used to get and store packages if available    |
| maven_repository_username     |           | admin         | nexus user to use for upload of downloaded files          |
| maven_repository_password     |           | admin123      | nexus password to use for upload of downloaded files      |
|                           	|          	|           	|                                                       	|
| simple_name                	| yes      	|           	| simple name for package                                 	|
| group_name                 	| yes      	|           	| group of package                                        	|
| package_name               	| yes      	|           	| package name                                            	|
| package_version            	| yes      	|           	| package name                                            	|
| package_url                	| yes      	|           	| package url                                             	|
| file_name                  	| yes      	|           	| download filename                                       	|
| file_override              	| yes      	|           	| override package name that has been downloaded          	|
| file_override_package_name 	| yes      	|           	| package name to override                                	|
| file_url_username            	|       	|           	| will be used when downloading file                    	|
| file_url_password           	|       	|           	| will be used when downloading file                    	|
|                           	|          	|           	|                                                       	|
| install_package_ansible       |           | false         | install package using ansible script, you will need pyaem2 installed |
| install_package_docker        |           | true          | install package using docker container, you will need to pass docker_host |
| docker_host                    |           | unix://var/run/docker.sock | host where to run the docker container for executing pyaem2 commands |
|                           	|          	|           	|                                                       	|

## Dependencies

This role depends on roles:
 
- `aem_design.aem_license`
- `aem_design.aem_verify`

## Example Playbook

```yaml
- hosts: all
  include_role:
    name: aem_package
  vars:
    aem_host: "{{ aem_host }}"
    aem_port: "{{ aem_port }}"
    aem_username: "{{ aem_username }}"
    aem_password: "{{ aem_password }}"
    simple_name: "{{ item.simple_name }}"
    group_name: "{{ item.group_name }}"
    package_name: "{{ item.package_name }}"
    package_version: "{{ item.version }}"
    package_url: "{{ item.package_url }}"
    file_name: "{{ item.file_name }}"
    file_override: "{{ item.file_override | default(false) }}"
    file_override_package_name: "{{ item.file_override_package_name | default('') }}"
    file_url_username: "{{ adobe_cloud_username }}"
    file_url_password: "{{ adobe_cloud_password }}"
    install_package_ansible: "true"
  with_items: "{{ package_files }}"
  when:
    - package_files is defined
    - item is defined
```

with vars

```yaml

package_files:
  ## SERVICE PACKS

  - {
    simple_name: "adobe servicepack 1",
    file_name: 'aem-service-pkg-6.5.1.zip',
    version: '6.5.1',
    group_name: 'adobe/cq650/servicepack',
    package_name: 'aem-service-pkg',
    package_url: "https://www.adobeaemcloud.com/content/companies/public/adobe/packages/cq650/servicepack/AEM-6.5.1.0/jcr%3acontent/package/file.res/AEM-6.5.1.0-6.5.1.zip"
  }


```

## License

Apache 2.0

## Author Information

This role was created by [Max Barrass](https://aem.design/).
