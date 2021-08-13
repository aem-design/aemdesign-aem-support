# Ansible Role: AEM License

[![Build Status](https://travis-ci.org/aem-design/ansible-role-aem-license.svg?branch=master)](https://travis-ci.org/aem-design/ansible-role-aem-license)

Install license to an AEM Instance.
> This role was developed as part of
> [AEM.Design](http://aem.design/)

## Requirements

None.

## Role Variables

Available variables are listed below, along with default values (see `defaults/main.yml`):

| Name                     	| Required 	| Default                                          	| Notes                                                 	|
|--------------------------	|----------	|--------------------------------------------------	|-------------------------------------------------------	|
| aem_license_key          	| yes      	|                                               	| license key submit value                              	|
| aem_license_name         	|          	| company                                           | company name submit value                             	|
| aem_license_path         	|        	| /system/granite/license/index.html               	| license page path                                     	|
| aem_license_console_path 	|          	| /system/console/productinfo                      	| license console path, used to check if license worked 	|
| aem_license_eula         	|          	| on                                               	| eula submit value                                     	|
| aem_license_post_eula    	|          	| eula                                             	| eula checkbox field name                              	|
| aem_license_post_name    	|          	| name                                             	| company name field name                               	|
| aem_license_post_key     	|          	| id                                               	| key field name                                        	|
|                          	|          	|                                                  	|                                                       	|
| aem_port                 	|          	| 4502                                           	| aem service port                                      	|
| aem_host                 	|          	| localhost                                      	| aem service host                                      	|
| aem_username           	|          	| admin                                             |                                                           |
| aem_password           	|          	| admin                                             |                                                           |
|                          	|          	|                                                  	|                                                       	|
| wait_delay               	|          	| 1                                                	| how long to wait between retries                      	|
| wait_timeout            	|          	| 1                                                	| how long to wait before terminating                      	|
| wait_retries            	|          	| 1                                                	| how many times to retry waiting                        	|
|                          	|          	|                                                  	|                                                       	|


## Dependencies

None.

## Example Playbook

```yaml
- hosts: all
  roles:
    - { role: aem_design.aem_license,
      aem_license_key: "your key",
      aem_license_name: "your company",
      aem_port: "4502",
      aem_host: "localhost",
    }
```

## License

Apache 2.0

## Author Information

This role was created by [Max Barrass](https://aem.design/).