---
layout: page
---

<!-- markdownlint-disable MD025 -->
# {{ page.title | default: site.title }}

<p>{{ site.description }}</p>

<!-- markdownlint-disable MD033 -->
<table class="table table-hover">
  <thead>
    <tr>
      <th scope="col">#</th>
      <th scope="col">Service</th>
      <th scope="col">Description</th>
      <th scope="col">URLs</th>
      <th scope="col">Port, Debug</th>
    </tr>
  </thead>
  <tbody>
    <tr>
      <th scope="row"><img src="logos/governance.foundation.png" alt="{{ site.env.SITE_TITLE}}" class="logo"></th>
      <td>Project Dashboard</td>
      <td>this page</td>
      <td><a target="_blank" class="btn btn-primary" href="{{ site.env.DOMAIN_URL }}">Open</a>
      </td>
      <td></td>
    </tr>
    <tr>
      <th scope="row"><img src="logos/Traefik.logo.png" alt="Traefik" class="logo"></th>
      <td>Traefik</td>
      <td>environment proxy for all services</td>
      <td><a target="_blank" class="btn btn-primary" href="{{ site.env.TRAEFIK_URL }}/dashboard/">Open</a></td>
      <td>8080</td>
    </tr>
    <tr>
      <th scope="row"><img src="logos/github-dark.png" alt="github"  class="logo"></th>
      <td>Github</td>
      <td>repo</td>
      <td>
          <a target="_blank" class="btn btn-primary" href="{{ site.env.GIT_REPO }}"><i class="fab fa-github"></i> Github</a>
      </td>
      <td></td>
    </tr>
    <tr>
      <th scope="row"><img src="logos/aem-design.png" class="logo"></th>
      <td>Author</td>
      <td>author instance</td>
      <td><a target="_blank" class="btn btn-primary" href="{{ site.env.AUTHOR_URL }}">author.localhost</a>
        <a target="_blank" class="btn btn-secondary" href="{{ site.env.AUTHOR_URL }}/crx/de/index.jsp">CRX/DE</a>
        <a target="_blank" class="btn btn-secondary" href="{{ site.env.AUTHOR_URL }}/crx/packmgr/index.jsp">Packages</a>
        <a target="_blank" class="btn btn-secondary" href="{{ site.env.AUTHOR_URL }}/system/console/bundles">Bundles</a>
        <a target="_blank" class="btn btn-secondary" href="{{ site.env.AUTHOR_URL }}/miscadmin">Miscadmin</a>
        <a target="_blank" class="btn btn-secondary" href="{{ site.env.AUTHOR_URL }}/libs/replication/treeactivation.html">Activate Tree</a>
      </td>
      <td>4502, 30303</td>
    </tr>
    <tr>
      <th scope="row"><img src="logos/aem-design.png" class="logo"></th>
      <td>Publish</td>
      <td>publish instance</td>
      <td><a target="_blank" class="btn btn-primary" href="{{ site.env.PUBLISH_URL }}">publish.localhost</a>
        <a target="_blank" class="btn btn-secondary" href="{{ site.env.PUBLISH_URL }}/crx/de/index.jsp">CRX/DE</a>
        <a target="_blank" class="btn btn-secondary" href="{{ site.env.PUBLISH_URL }}/crx/packmgr/index.jsp">Packages</a>
        <a target="_blank" class="btn btn-secondary" href="{{ site.env.PUBLISH_URL }}/system/console/bundles">Bundles</a>
        <a target="_blank" class="btn btn-secondary" href="{{ site.env.PUBLISH_URL }}/miscadmin">Miscadmin</a>
      </td>
      <td>4503, 30304</td>
    </tr>
    <tr>
      <th scope="row"><img src="logos/Apache_Feather_Logo.svg" class="logo"></th>
      <td>Dispatcher</td>
      <td>apache with dispatcher module</td>
      <td><a disabled target="_blank" class="btn btn-primary"  href="{{ site.env.DISPATCHER_URL }}">dispatcher.localhost</a></td>
      <td>9090, 9433</td>
    </tr>
    <tr>
      <th scope="row"><img src="logos/selenium_grid_logo_square.png" class="logo"></th>
      <td>Selenium Hub</td>
      <td>selenium grid hub with chrome node</td>
      <td><a target="_blank" class="btn btn-primary" href="{{ site.env.SELENIUMHUB_URL }}">seleniumhub.localhost</a>
        <a target="_blank" class="btn btn-secondary" href="{{ site.env.SELENIUMHUB_URL }}/grid/console">Console</a>
      </td>
      <td>32768</td>
    </tr>
    <tr>
      <th scope="row"><img src="logos/Traefik.logo.png" class="logo"></th>
      <td>Traefik</td>
      <td>environment proxy for all services</td>
      <td><a target="_blank" class="btn btn-primary" href="{{ site.env.DOMAIN_URL }}:8080">Open</a></td>
      <td>8080</td>
    </tr>
    <tr>
      <th scope="row"><img src="logos/aem-design.png" class="logo"></th>
      <td>AEM.Design</td>
      <td>more info on the project</td>
      <td><a target="_blank" class="btn btn-primary" href="${PUBLIC_URL}">Site</a>
          <a target="_blank" class="btn btn-primary" href="${GIT_ORG_URL}"><i class="fab fa-github"></i>Git Org</a>
      </td>
      <td></td>
    </tr>
  </tbody>
</table>
