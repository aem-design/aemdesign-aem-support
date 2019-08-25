AEM Design - AEM Config
=======================

Directory Layout
=======
```
src/
   main/  
      content/
        jcr_root/
           apps/
            aemdesign/
                config/               # Runmode configuration for default 
                config.author/        # Runmode configuration for author instance on all environements
                config.author.<env>/           # Runmode configuration for author on an specific environment server 
                config.author.localdev/     # Runmode configuration for author on local development 
                config.ldap/          # Runmode configuration for ldap 
                config.publish/      # Runmode configuration for publish instance on all environements
                config.publish.<env>/           # Runmode configuration for publish on an specific environment server 
                config.publish.localdev/     # Runmode configuration for publish on local development 
```