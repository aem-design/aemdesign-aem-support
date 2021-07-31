# Ansible Role: AEM Verify

[![Build Status](https://travis-ci.org/aem-design/ansible-role-aem-verify.svg?branch=master)](https://travis-ci.org/aem-design/ansible-role-aem-verify)

This role tests AEM instance if its available.
> This role was developed as part of
> [AEM.Design](http://aem.design/)

## Requirements

None.

## Role Variables

Available variables are listed below, along with default values (see `defaults/main.yml`):

    wait_delay: 1
    aem_schema: "http"
    aem_host: "localhost"
    aem_port: 4502
    aem_username: "admin"
    aem_password: "admin"
    
    debug_hide: true
    
    url_login: "/libs/granite/core/content/login.html"
    url_packages: "/crx/packmgr/service.jsp"
    url_bunldes_json: "/system/console/bundles.json"
    url_bunldes: "/system/console/bundles"

    
To specify service that should be used as target for tests set following variable

    aem_schema: "http"
    aem_host: "localhost"
    aem_port: 4502
    aem_username: "admin"
    aem_password: "admin"

## Dependencies

None.

## Example Playbook

```yaml
- hosts: all
  roles:
    - aem_design.aem_verify
```

## License

Apache 2.0

## Author Information

This role was created by [Max Barrass](https://aem.design/).