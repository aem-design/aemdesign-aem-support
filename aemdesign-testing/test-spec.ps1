Param(
  [string]$DOCKER_SELENIUMHUB_NODE_NAME = "selenium-hub-node-chrome",
  [string]$DOCKER_SELENIUMHUB_NAME = "selenium-hub",
  [string]$DOCKER_NETWORK_NAME = "selenium-grid",
  [string]$LOG_PATH = "..\logs",
  [string]$LOG_PEFIX = "test-spec",
  [string]$LOG_SUFFIX = ".log",
  [string]$DOCKER_LOGS_FOLDER = "..\logs",
  [string]$DRIVER_FOLDER = "${PWD}\drivers",
  [switch]$LOGIN = $true,
  [switch]$REPORT = $true,
  [switch]$DEBUG = $false,
  [switch]$SILENT = $false,
  [string]$TEST_SELENIUMHUB_SCHEME = (&{If($SELENIUMHUB_SCHEME -eq $null) {"http"} else {$SELENIUMHUB_SCHEME}}),
  [string]$TEST_SELENIUMHUB_PORT = (&{If($SELENIUMHUB_PORT -eq $null) {"32768"} else {$SELENIUMHUB_PORT}}),
  [string]$TEST_SELENIUMHUB_SERVICE = (&{If($SELENIUMHUB_SERVICE -eq $null) {"/wd/hub"} else {$SELENIUMHUB_SERVICE}}),
  [string]$TEST_SELENIUMHUB_HOST = (&{If($SELENIUMHUB_HOST -eq $null) {"localhost"} else {$SELENIUMHUB_HOST}}),
  [string]$TEST_SELENIUM_URL = "${TEST_SELENIUMHUB_SCHEME}://${TEST_SELENIUMHUB_HOST}:${TEST_SELENIUMHUB_PORT}${TEST_SELENIUMHUB_SERVICE}",
  [string]$TEST_IMAGE = "aemdesign/centos-java-buildpack",
  [switch]$TEST_OPEN_REPORT = $false,
  [string]$TEST_REPORT_PATH = "generated-docs/summary.html",
  [string]$TEST_DRIVER_NAME = "remote-seleniumhub-chrome",
  [string]$AEM_SCHEME = "http",
  [string]$AEM_HOST = "$( (Get-NetIPAddress | Where-Object {$_.AddressState -eq "Preferred" -and $_.ValidLifetime -lt "24:00:00"}).IPAddress)",
  [string]$AEM_PORT = "4502",
  [string]$AEM_USERNAME = "admin",
  [string]$AEM_PASSWORD = "admin",
  [string]$TEST_SPECS = "$( (Get-Content ".\test-list") -join ",")",
  [string]$TEST_WORKSPACE = "",
  [switch]$TEST_LOGIN = $false,
  [string]$TEST_MAVEN_CONFIG = "",
  [switch]$TEST_DISPATCHER = $false,
  [switch]$TEST_SKIP_CONVERT = $false,
  [switch]$TEST_USING_MAVEN = $false,
  [string]$FUNCTIONS_URI = "https://github.com/aem-design/aemdesign-docker/releases/latest/download/functions.ps1",
  [string]$TEST_VIEWPORTS = "$( (Get-Content ".\test-viewports") -join ",")"
)

$SKIP_CONFIG = $true
$PARENT_PROJECT_PATH = ".."

. ([Scriptblock]::Create((([System.Text.Encoding]::ASCII).getString((Invoke-WebRequest -Uri "${FUNCTIONS_URI}").Content))))

Function Get-MavenCommand
{
  [Cmdletbinding()]
  [Alias("getMavenCommand")]
  param
  (
    [Parameter(ValueFromPipeline)]
    [string]$DISPATCHER = $args[0],
    [string]$DRIVER = $args[1],
    [string]$AEM_HOST = $args[2],
    [string]$LOGIN = $args[3],
    [string]$MAVEN_CONFIG = $args[4],
    [string]$AEM_PASSWORD = $args[5],
    [string]$AEM_PORT = $args[6],
    [string]$AEM_SCHEME = $args[7],
    [string]$SPECS = $args[8],
    [string]$SELENIUM_URL = $args[9],
    [string]$AEM_USERNAME = $args[10],
    [string]$VIEWPORTS = $args[11],
    [string]$PROJECT_ROOT_DIR = $args[12],
    [string]$PARENT_PROJECT_NAME = $args[13],
    [string]$CURRENT_PROJECT_NAME = $args[14]
  )

  if ( -Not( $TEST_SKIP_CONVERT ) ) {
    $MAVEN_EXTRAS = ""
  }

  return "mvn clean test ${MAVEN_EXTRAS} -D""geb.env=${DRIVER}"" -D""project.buildDirectory=${DRIVER}"" -D""aem.scheme=${AEM_SCHEME}"" -D""aem.host=${AEM_HOST}"" -D""aem.port=${AEM_PORT}"" -D""aem.username=${AEM_USERNAME}"" -D""aem.password=${AEM_PASSWORD}"" -D""test=${SPECS}"" -D""selenium.huburl=${SELENIUM_URL}"" -D""login.req=${LOGIN}"" -D""test.dispatcher=${DISPATCHER}"" -D""test.viewports=${VIEWPORTS}"" -D""project.rootdir=${PROJECT_ROOT_DIR}"" -D""project.rootname=${PARENT_PROJECT_NAME}"" -D""project.currentname=${CURRENT_PROJECT_NAME}"" ${MAVEN_CONFIG}"

}

Function Do-RunTests
{
  [Cmdletbinding()]
  [Alias("runTests")]
  param(
    [string[]]$TEST_DRIVER_NAME = $args[0]
  )

  $script:DRIVERS_EXEC = $TEST_DRIVER_NAME
  $DRIVER_LENGTH = $TEST_DRIVER_NAME.Length

  $COUNT = 1

  Foreach ($DRIVER IN $TEST_DRIVER_NAME) {
    printSectionLine "Starting test: [$COUNT/$DRIVER_LENGTH] $DRIVER"
    runTest "$DRIVER"
    $COUNT = $COUNT + 1
  }
}


Function Do-RunTest
{
  [Cmdletbinding()]
  [Alias("runTest")]
  param(
    [string]$DRIVER = $args[0]
  )

  printSectionBanner "RUNNING TEST FOR DRIVER: ${DRIVER}"
  printSectionLine "Maven MAVEN_COMMAND:"
  printSectionLine "${MAVEN_COMMAND}"

  $CONTAINER_EXIST = "$(docker ps -q -f name=${DRIVER})"
  $CONTAINER_EXITED = "$(docker ps -aq -f status=exited -f name=${DRIVER})"

  printSectionLine "CONTAINER_EXIST: ${CONTAINER_EXIST}"
  printSectionLine "CONTAINER_EXITED: ${CONTAINER_EXITED}"

  if ( [string]::IsNullOrEmpty(${CONTAINER_EXIST}) ) {
    if ( -Not ( [string]::IsNullOrEmpty(${CONTAINER_EXITED}) ) ) {
      # cleanup
      printSectionLine "Removing container:"
      $(docker rm ${DRIVER})
    }
    $PROJECT_ROOT_DIR="$( Resolve-Path ${PARENT_PROJECT_PATH} )"
    $MAVEN_DIR="$(Resolve-Path ~/.m2)"

    printSectionLine "Project Root Directory: ${PROJECT_ROOT_DIR}"
    printSectionLine "Maven Directory: ${MAVEN_DIR}"

    $PARENT_PROJECT_WITH_GIT = $(getGitDir)
    $PARENT_PROJECT_WITH_GIT = ( $PARENT_PROJECT_WITH_GIT -split "\.git" )[0]
    $PARENT_PROJECT_WITH_GIT_NAME = $( Resolve-Path "${PARENT_PROJECT_WITH_GIT}" | Split-Path -Leaf )
    $PARENT_PROJECT_LOCATION = $( Resolve-Path "${PWD}\.." )
    $PARENT_PROJECT_NAME = $( Resolve-Path "$PARENT_PROJECT_LOCATION" | Split-Path -Leaf )
    $PROJECT_NAME = $( Resolve-Path "${PWD}" | Split-Path -Leaf )

    $CURRENT_PROJECT_LOCATION = "${PARENT_PROJECT_WITH_GIT_NAME}/${PROJECT_NAME}"

    printSectionLine "Parent Project with GIT Directory: ${PARENT_PROJECT_WITH_GIT}"
    printSectionLine "Parent Project with GIT Directory Name: ${PARENT_PROJECT_WITH_GIT_NAME}"
    printSectionLine "Parent Project Name: ${PARENT_PROJECT_NAME}"
    printSectionLine "Current Project Name: ${PROJECT_NAME}"
    printSectionLine "Current Project Location: ${CURRENT_PROJECT_LOCATION}"
    printSectionLine "Testing Directory: ${CURRENT_PATH}"
    printSectionLine "Testing Sub Directory: ${PARENT_PROJECT_LOCATION}"

    $MAVEN_COMMAND=$(getMavenCommand "${TEST_DISPATCHER}" "${DRIVER}" "${AEM_HOST}" "${TEST_LOGIN}" "${TEST_MAVEN_CONFIG}" "${AEM_PASSWORD}" "${AEM_PORT}" "${AEM_SCHEME}" "${TEST_SPECS}" "${TEST_SELENIUM_URL}" "${AEM_USERNAME}" "$([system.String]::Join(" ", $TEST_VIEWPORTS))" "${PROJECT_ROOT_DIR}" "${PARENT_PROJECT_NAME}" "${CURRENT_PROJECT_LOCATION}")

    if ( -Not( ${TEST_USING_MAVEN} ) )
    {


      # run docker container as current use
      # set work directory to be current project
      # mount parent project directory into build
      # mount .m2 into /build/.m2 as we are running as user
      # pass maven command location for .m2 dir
      # run bash with login to allow usage of RVM
      # auto-remove container after its done
      $DOCKER_COMMAND="docker run -d --rm --net=host --name ${DRIVER} -v ${PARENT_PROJECT_WITH_GIT}:/build/${PARENT_PROJECT_WITH_GIT_NAME} -v ${MAVEN_DIR}:/build/.m2 -w ""/build/${CURRENT_PROJECT_LOCATION}"" ${TEST_IMAGE} bash -l -c '${MAVEN_COMMAND} -Dmaven.repo.local=/build/.m2/repository' "

      debug "${DOCKER_COMMAND}"

      printSubSectionStart "Docker Command Execute"

      Invoke-Expression "${DOCKER_COMMAND}"

      printSubSectionEnd "Docker Command Execute"
    } else {
      printSubSectionStart "Direct Maven Command Execute"
      debug "${MAVEN_COMMAND}"
      Invoke-Expression "${MAVEN_COMMAND}"
      printSubSectionEnd "Direct Maven Command Execute"

      printSubSectionStart "Convert Reports"
      Invoke-Expression ".\asciidoctor-convert-reports $(&{If($SILENT) {"-SILENT"}})"
      printSubSectionEnd "Convert Reports"
    }


  } else {
    printSectionLine "Container is already running: $DRIVER"
  }

}


Function Do-MonitorTests
{
  [Cmdletbinding()]
  [Alias("monitorTests")]
  param(
    [string]$TEST_DRIVER_NAME = $args[0]
  )

  $script:OPEN_REPORTS = $TEST_DRIVER_NAME
  $OPEN_REPORTS_LENGTH = $OPEN_REPORTS.Length

  if (Test-Path "${DOCKER_LOGS_FOLDER}" -PathType leaf)
  {
    New-Item -ItemType "directory" -Path "${DOCKER_LOGS_FOLDER}"
  }

  printSubSectionStart "Watch Logs and Save to Log file"

  $LOGFILE="${DOCKER_LOGS_FOLDER}\docker-log-stdout-$(DateStamp).log"
  printSectionLine "CONTAINERS: ${OPEN_REPORTS}"
  printSectionLine "LOGFILE: ${LOGFILE}"

  $DOCKER_COMMAND = "docker logs -f ${TEST_DRIVER_NAME}"

  Invoke-Expression -Command "${DOCKER_COMMAND}" | Tee-Object -Append -FilePath "${LOGFILE}"

  printSubSectionEnd "Watch Logs and Save to Log file"

  if ( ${TEST_OPEN_REPORT} )
  {
    openReports "${OPEN_REPORTS}"
  }


}


Function Do-OpenReports
{
  [Cmdletbinding()]
  [Alias("openReports")]
  param(
    [string[]]$TEST_DRIVER_NAME = $args[0]
  )

  $script:OPEN_REPORTS = $TEST_DRIVER_NAME
  $OPEN_REPORTS_LENGTH = $OPEN_REPORTS.Length

  printSubSectionStart "Open Reports"

  printSectionLine "REPORTS: $@"

  $COUNT = 1

  Foreach ($DRIVER IN $OPEN_REPORTS_LENGTH) {
    printSectionLine "CHECKING REPORT: [${COUNT}/${OPEN_REPORTS_LENGTH}] ${DRIVER}"
    printSectionLine "OPENING REPORT: ${DRIVER}/$TEST_REPORT_PATH"

    $REPORT_PATH = ".\${DOCKER_LOGS_FOLDER}\generated-docs\html\summary.html"
    if (Test-Path "${REPORT_PATH}" -PathType leaf)
    {
      Invoke-Item "${REPORT_PATH}"
    }

    $REPORT_PATH = ".\${DOCKER_LOGS_FOLDER}\generated-docs\pdf\summary.pdf"
    if (Test-Path "${REPORT_PATH}" -PathType leaf)
    {
      Invoke-Item "${REPORT_PATH}"
    }


    $COUNT = $COUNT + 1
  }

  # Walk thought reports and see if the reports are ready to open
  printSubSectionEnd "Open Reports"


}

$CURRENT_PATH = ${PWD}

# update selenium url if localhost and not passed
if ( $SELENIUMHUB_HOST -eq "localhost" -Or ([string]::IsNullOrEmpty(${TEST_SELENIUM_URL})) )
{
  debug "Selenium hub url not specified or set to localhost, updating to use local ip" "info"
  $script:TEST_SELENIUM_URL="${TEST_SELENIUMHUB_SCHEME}://${LOCAL_IP}:${TEST_SELENIUMHUB_PORT}${TEST_SELENIUMHUB_SERVICE}"
}

#update host
if ( $AEM_HOST -eq "localhost" )
{
  debug "Test host is set as localhost, updating to use local ip" "info"
  $script:AEM_HOST="${LOCAL_IP}"
}

printSectionBanner "Test Configuration:" "warn"
printSectionLine "AEM_SCHEME:             ${AEM_SCHEME}"
printSectionLine "AEM_HOST:               ${AEM_HOST}"
printSectionLine "AEM_PORT:               ${AEM_PORT}"
printSectionLine "TEST_SELENIUM_URL:      ${TEST_SELENIUM_URL}"
printSectionLine "TEST_SPECS:             ${TEST_SPECS}"
printSectionLine "TEST_VIEWPORTS:         ${TEST_VIEWPORTS}"
printSectionLine "TEST_WORKSPACE:         ${TEST_WORKSPACE}"
printSectionLine "TEST_LOGIN?             ${TEST_LOGIN}"
printSectionLine "TEST_MAVEN_CONFIG:      ${TEST_MAVEN_CONFIG}"
printSectionLine "TEST_DISPATCHER?:       ${TEST_DISPATCHER}"
printSectionLine "TEST_SKIP_CONVERT?:     ${TEST_SKIP_CONVERT}"
printSectionLine "TEST_USING_MAVEN?:      ${TEST_USING_MAVEN}"

$script:AEM_AVAILABLE=$(testServer "${AEM_SCHEME}://${AEM_HOST}:${AEM_PORT}")
$script:HUB_AVAILABLE=$(testServer ("${TEST_SELENIUM_URL}").Replace("/wd/hub","") )

printSectionLine "Is Selenium Hub at ${TEST_SELENIUM_URL} available? ${HUB_AVAILABLE}"
printSectionLine "Is AEM at ${AEM_SCHEME}://${AEM_HOST}:${AEM_PORT} available? ${AEM_AVAILABLE}"

if ( $HUB_AVAILABLE -And $AEM_AVAILABLE )
{
  debug "Selenium Hub and AEM are both up and available!" "info"

  if ( -Not( $SILENT ) )
  {
    $START = Read-Host -Prompt "Do you want to start testing with these settings? (y/n)"

    if ($START -ne "y")
    {
      Write-Output "Quiting..."
      Exit
    }
  }

  printSectionBanner "Starting Tests on $(LocalIP)" "info"

  runTests ${TEST_DRIVER_NAME}

  if ( -Not( ${TEST_USING_MAVEN} ) )
  {
    monitorTests ${TEST_DRIVER_NAME}
  }

} else {
  debug "Either Selenium Hub or AEM is not currently available!" "error"
  exit 1
}

