# AEM Design - AEM Testing Framework

Automated Functional Testing Framework using Docker and Selenium Grid to test AEM Sites.

This will test your specified instance of AEM using Docker container. 

To check which parameters can be used check `test-spec.ps1` for Windows and you can run `./test-spec  --help` to check parameters for Linux/Bash execution.

## Prerequisites

You will need to have the following software installed to ensure you can contribute to development of this codebase:

* [Update to Windows 10 20H2](#Update-to-Windows-10-1809) - this will give your windows updates to run WSL2 and install needed applications.
* [Download and install Java 1.8](https://www.oracle.com/au/java/technologies/javase/javase-jdk8-downloads.html) - you will need this to ensure your code runs on AEM.
* [Docker Desktop](https://www.docker.com/products/docker-desktop) - this will be used by scripts to run tests
* [Powershell 7](https://github.com/PowerShell/PowerShell/releases) - this will make your windows terminal work check with `$PSVersionTable`
* [Windows Terminal](https://github.com/microsoft/terminal/releases) - a wrapper for all terminal available on windows
* [Windows 10 WSL2](https://docs.microsoft.com/en-us/windows/wsl/install-win10) - allow Windows, Docker and Powershell to work like one!
* [Ubuntu 20 for Windows](https://docs.microsoft.com/en-us/windows/wsl/install-manual) - this will allow you to do awesome Linux!
* [Update your Ubuntu Mounts](#Update-your-Ubuntu-Mounts) - this will make your drive appear at root
* [Enable Windows 10 Long Files Names](#Enable-Windows-10-Long-File-Names) - this will allow Windows to have long filenames.
* [Install Git Bash](https://gitforwindows.org/) - this will allow you to run git in terminal
* [Add Git Path Windows Path](#Add-Git-Path-Windows-Path) - this will allow you to run git and other helper functions in powershell and will make your powershell sing!
* [Enable WSL2 on Windows 10](#Enable-WSL2) - this will allow you to run windows apps from Ubuntu sub-shell.
* [Intellij Ultimate](https://www.jetbrains.com/idea/download) - this will be your primary IDE, please install following plugin as well.
  * Plugin: [IntelliVault](https://plugins.jetbrains.com/plugin/7328-intellivault) - configure the plugin to use `vlt` in  

You can now prepare your AEM and project for testing 

* [Run AEM in Docker](#Run-AEM-in-Docker) - start a fresh copy of AEM running in Docker Container, wait for it to load and [http://localhost:4502](http://localhost:4502) and enter your license key.
* [Deploy Project Content](#Deploy-Project-Content) - deploy project code and content your AEM for testing
* [GPG using Kleopatra](https://tau.gr/posts/2018-06-29-how-to-set-up-signing-commits-with-git/) - will ensure your commits are from you!
* [Add your normal user to Docker Users Group](#Add-your-normal-user-to-Docker-Users-Group) - this will allow your to run docker from your account. 

All of this software is going to make your life awesome!

### Deploy Project Content

[Back to Prerequisites](#Prerequisites)

To ensure you have the latest content for testing deploy parent project, run following command in the parent folder:

```powershell
mvn -DskipTests=true  -P autoInstallPackage -P autoInstallBundle clean install
```

### Run AEM in Docker

[Back to Prerequisites](#Prerequisites)

To start a fresh copy of AEM running in Docker Container run following commnad

```powershell
docker run --name author648 -e "TZ=Australia/Sydney" -e "AEM_RUNMODE=-Dsling.run.modes=author,crx3,crx3tar,forms,localdev" -e "AEM_JVM_OPTS=-server -Xms248m -Xmx1524m -XX:MaxDirectMemorySize=256M -XX:+CMSClassUnloadingEnabled -Djava.awt.headless=true -Dorg.apache.felix.http.host=0.0.0.0 -Xdebug -Xrunjdwp:transport=dt_socket,server=y,address=58242,suspend=n" -p4502:8080 -p30303:58242 -d aemdesign/aem:6.4.8.0
```

### Update your Ubuntu Mounts

[Back to Prerequisites](#Prerequisites)

https://nickjanetakis.com/blog/setting-up-docker-for-windows-and-wsl-to-work-flawlessly#ensure-volume-mounts-work

```
sudo nano sudo nano /etc/wsl.conf
```

Add this content into `/etc/wsl.conf`:

```
[automount]
root = /
options = "metadata"
```

### Enable WSL2

[Back to Prerequisites](#Prerequisites)

To enable WSL2 on windows run the following command in an elevated powershell prompt:

```powershell
Enable-WindowsOptionalFeature -Online -FeatureName $("VirtualMachinePlatform", "Microsoft-Windows-Subsystem-Linux")
```

### Update to Windows 10 20H2

[Back to Prerequisites](#Prerequisites)

You will need to update your windows machine to at least version 20H2 preferred (1809 min), use Windows Update and click "Check for updates Available at Microsoft"

### Add your normal user to Docker Users Group

[Back to Prerequisites](#Prerequisites)

Run the following command in an elevated powershell prompt:

```powershell
Add-LocalGroupMember -Group "docker-users" -Member "<YOUR USER NAME>"
```

### Enable Windows 10 Long File Names

[Back to Prerequisites](#Prerequisites)

To check if your registry entry value for long filenames support:

```powershell
reg query HKEY_LOCAL_MACHINE\SYSTEM\ControlSet001\Control\FileSystem /v LongPathsEnabled
```

To enable Windows 10 long filename run following command and restart your computer:

```powershell
reg add HKEY_LOCAL_MACHINE\SYSTEM\ControlSet001\Control\FileSystem /v LongPathsEnabled /t REG_DWORD /d 0x1 /f 
```

## Add Git Path Windows Path

[Back to Prerequisites](#Prerequisites)

You need to add following paths to your System Path environment variable:

* C:\Program Files\Git\bin - this contains main git
* C:\Program Files\Git\usr\bin - this contains helpers that are available on linux

## Report Output

Test execution will generate sub-directory in the project with test run results.
This is done so that you can run multiple test cycles at the same time with different parameters.  

* `remote-seleniumhub-chrome` - will be named as per driver name passed as parameter `TEST_DRIVER_NAME`
  * `generated-docs` - as the Ascii docs output of the final report
  * `spock-reports` - has the json and Markdown generated results after execution 
  * `surefire-reports` - has the content for report run output
  * `test-classes` - has the compiled tests
  * `test-reports` - has the outputs of the component execution HTML and images

## Base screenshots

When executing Screenshot test they will be saved in `src/test/screenshots` first time the spec is ran. 
You can commit these files to git, and they will be used as a reference source for next run.  


## Usage

Start Selenium Hub and Node

```powershell
.\seleniumhub-start
```

Using Powershell 7 execute selected tests run following command:

```powershell
.\test-spec.ps1 -TEST_PORT 4502 -TEST_HOST 10.0.0.12 -TEST_SELENIUM_URL http://10.0.0.12:32768/wd/hub -TEST_SPECS NotificationCardA*
```

On Linux you can do following command:

```bash
./test-spec  --host 192.168.1.12 --url http://192.168.1.12:32768/wd/hub --tests NotificationCardA*
```

Above commands will only execute `NotificationCardAuthorSpec`.

Update `test-list` with test you want to run without specifying parameters, this should be a complete list of test you want to run.

```powershell
.\test-spec.ps1 -TEST_PORT 4512 
```

Stop Selenium Hub and Node

```powershell
.\seleniumhub-stop
```


## Reference Documentation

### AEM Authoring Guides

- [Authoring Pages](https://docs.adobe.com/docs/en/aem/6-0/author/page-authoring.html)
- [Authoring - the Environment and Tools](https://docs.adobe.com/docs/en/aem/6-0/author/page-authoring/author-environment-tools.html)
- [Components for Page Authoring](https://docs.adobe.com/docs/en/aem/6-0/author/page-authoring/default-components/editmode.html)

### GEB Info

- [Geb Github Projects](https://github.com/geb)
- [Geb Maven Sample](https://github.com/geb/geb-example-maven)
- [Spock and Geb in Action](http://www.slideshare.net/InfoQ/taming-functional-web-testing-with-spock-and-geb)
- [Taming Functional Web Testing with Spock and Geb](http://www.slideshare.net/InfoQ/taming-functional-web-testing-with-spock-and-geb)

# Maven Reports

- [Spock Reports Extension](https://github.com/renatoathaydes/spock-reports)
- [Surefire Reports](http://maven.apache.org/surefire/maven-surefire-report-plugin/usage.html)


# ASCII Doc Reports
Adding screenshots to Test Steps is done by detecting a text string in code block and then adding picking a screenshot to show.
Update the following line in ```spect-template.ad```:

```
    def codeCheckFileGen = /[\t ]*report|designRef|takeScreenshot.*/
```

For multi-screenshot print check following block to ensure columns are printed.

```
    if (filePathFull.endsWith(pathCheckEndImage)) {
        if (filePathFull.startsWith(pathCheckStartSource)) {
            blockScreenshots = blockScreenshots << """ .Source\n"""
        } else if (filePathFull.endsWith(pathCheckEndDiff)) {
            blockScreenshots = blockScreenshots << """ a| .Diff\n"""
        } else if (!filePathFull.startsWith(pathCheckStartSpec)) {
            if (it.files.size() > 1) {
                blockScreenshots = blockScreenshots << """ a| """
            }
            blockScreenshots = blockScreenshots << """ .Current\n"""
        }
```

If HTML report are not showing expected result check base templates in ```spock-reports/.ad``` these are converted to html.

# Driver Specific Parameters

## BrowserStack Variables

You can pass following variable via command line to specify browserstack parameters.

For more information please see following reference site [https://www.browserstack.com/automate/capabilities](https://www.browserstack.com/automate/capabilities)

| Internal Name                      | Command Line Param                 | Browserstack Variable        |
|------------------------------------|------------------------------------|------------------------------|
| GEB_BROWSERSTACK_USERNAME          | geb.browserstack.username          |                              |
| GEB_BROWSERSTACK_AUTHKEY           | geb.browserstack.authkey           |                              |
| GEB_BROWSERSTACK_SCHEMA            | geb.browserstack.schema            |                              |
| GEB_BROWSERSTACK_HOST              | geb.browserstack.host              |                              |
| GEB_BROWSERSTACK_URL               | "SCHEMA://USERNAME:AUTHKEY@HOST"   |                              |
| GEB_BROWSERSTACK_BROWSER           | geb.browserstack.browser           | browser                      |
| GEB_BROWSERSTACK_BROSWER_VERSION   | geb.browserstack.browserversion    | browser_version              |
| GEB_BROWSERSTACK_BUILD             | geb.browserstack.build             | build                        |
| GEB_BROWSERSTACK_OS                | geb.browserstack.os                | os                           |
| GEB_BROWSERSTACK_OS_VERSION        | geb.browserstack.osversion         | os_version                   |
| GEB_BROWSERSTACK_DEBUG             | geb.browserstack.debug             | browserstack.debug           |
| GEB_BROWSERSTACK_DEVICE            | geb.browserstack.device            | device                       |
| GEB_BROWSERSTACK_REALMOBILE        | geb.browserstack.device            | realMobile                   |
| GEB_BROWSERSTACK_RESOLUTION        | geb.browserstack.resolution        | resolution                   |
| GEB_BROWSERSTACK_LOCAL             | geb.browserstack.local             | browserstack.local           |
| GEB_BROWSERSTACK_LOCALID           | geb.browserstack.localIdentifier   | browserstack.localIdentifier |
| GEB_BROWSERSTACK_PROJECT           | geb.browserstack.project           | project                      |
| GEB_BROWSERSTACK_ACCESPTSSL        | geb.browserstack.acceptSslCerts    | acceptSslCerts               |
| GEB_BROWSERSTACK_PLATFORM          | geb.browserstack.platform          | platform                     |
| GEB_BROWSERSTACK_SELENIUMVERSION   | geb.browserstack.selenium_version  | selenium_version             |
| GEB_BROWSERSTACK_NAME              | geb.browserstack.name              | name                         |
| GEB_BROWSERSTACK_CONSOLE           | geb.browserstack.console           | browserstack.console         |
| GEB_BROWSERSTACK_VIDEO             | geb.browserstack.video             | browserstack.video           |
| GEB_BROWSERSTACK_NETWORKLOGS       | geb.browserstack.networkLogs       | browserstack.networkLogs     |
| GEB_BROWSERSTACK_TIMEZONE          | geb.browserstack.timezone          | browserstack.timezone        |
| GEB_BROWSERSTACK_GEOLOCATION       | geb.browserstack.geoLocation       | browserstack.geoLocation     |
| GEB_BROWSERSTACK_NETWORKSPEED      | geb.browserstack.customNetwork     | browserstack.customNetwork   |
| GEB_BROWSERSTACK_DEVICEORIENTATION | geb.browserstack.deviceOrientation | deviceOrientation            |
