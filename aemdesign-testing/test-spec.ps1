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
  [switch]$DODEBUG = $false,
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
  [string]$AEM_HOST = "author",     # selenium node has direct access to author network
  [string]$AEM_PORT = "8080",     # selenium node has direct access to author network
  [string]$AEM_USERNAME = "admin",
  [string]$AEM_PASSWORD = "admin",
  [string]$TEST_WORKSPACE = "",
  [switch]$TEST_LOGIN = $false,
  [string]$TEST_MAVEN_CONFIG = "",
  [switch]$TEST_DISPATCHER = $false,
  [switch]$TEST_SKIP_CONVERT = $false,
  [switch]$TEST_USING_MAVEN = $false,
  [string]$FUNCTIONS_URI = "https://github.com/aem-design/aemdesign-docker/releases/latest/download/functions.ps1",
  [string]$TEST_VIEWPORTS = "$( (Get-Content ".\test-viewports") -join ",")",
  [string]$DOCKER_COMPOSE_COMMAND = "cd ..; docker-compose --profile=dotest up testing; cd -",
  [Parameter(Position=0)]
  [string]$TEST_SPECS = "$( (Get-Content ".\test-list") -join ",")"

)

$SKIP_CONFIG = $true
$PARENT_PROJECT_PATH = ".."

. ([Scriptblock]::Create((([System.Text.Encoding]::ASCII).getString((Invoke-WebRequest -Uri "${FUNCTIONS_URI}").Content))))

Function CompileMavenCommand
{
  [Cmdletbinding()]
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

Function RunTests
{
  [Cmdletbinding()]
  param(
    [string[]]$TEST_DRIVER_NAME = $args[0]
  )

  $script:DRIVERS_EXEC = $TEST_DRIVER_NAME
  $DRIVER_LENGTH = $TEST_DRIVER_NAME.Length

  $COUNT = 1

  Foreach ($DRIVER IN $TEST_DRIVER_NAME) {
    printSectionLine "Starting test: [$COUNT/$DRIVER_LENGTH] $DRIVER"
    RunTest "$DRIVER"
    $COUNT = $COUNT + 1
  }
}


Function RunTest
{
  [Cmdletbinding()]
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

    printSectionLine "MAVEN_COMMAND: ${MAVEN_COMMAND}"
    printSectionLine "PARENT_PROJECT_WITH_GIT: ${PARENT_PROJECT_WITH_GIT}"
    printSectionLine "PARENT_PROJECT_WITH_GIT_NAME: ${PARENT_PROJECT_WITH_GIT_NAME}"
    printSectionLine "CURRENT_PROJECT_LOCATION: ${CURRENT_PROJECT_LOCATION}"
    printSectionLine "MAVEN_DIR: ${MAVEN_DIR}"

    # if testing using docker update vars
    if ( -Not( ${TEST_USING_MAVEN} ) )
    {
      $TEST_SELENIUM_URL = "${TEST_SELENIUMHUB_SCHEME}://seleniumhub:4444${TEST_SELENIUMHUB_SERVICE}"
      $PROJECT_ROOT_DIR = "/build/${PARENT_PROJECT_WITH_GIT_NAME}"
    }

    $MAVEN_COMMAND=$(CompileMavenCommand "${TEST_DISPATCHER}" "${DRIVER}" "${AEM_HOST}" "${TEST_LOGIN}" "${TEST_MAVEN_CONFIG}" "${AEM_PASSWORD}" "${AEM_PORT}" "${AEM_SCHEME}" "${TEST_SPECS}" "${TEST_SELENIUM_URL}" "${AEM_USERNAME}" "$([system.String]::Join(" ", $TEST_VIEWPORTS))" "${PROJECT_ROOT_DIR}" "${PARENT_PROJECT_NAME}" "${CURRENT_PROJECT_LOCATION}")

    if ( -Not( ${TEST_USING_MAVEN} ) )
    {


      printSubSectionStart "Docker Compose Execute"
      printSectionLine "MAVEN_COMMAND: ${MAVEN_COMMAND}"

      $Env:MAVEN_COMMAND = "${MAVEN_COMMAND}"
      $Env:PARENT_PROJECT_WITH_GIT = "${PARENT_PROJECT_WITH_GIT}"
      $Env:PARENT_PROJECT_WITH_GIT_NAME = "${PARENT_PROJECT_WITH_GIT_NAME}"
      $Env:CURRENT_PROJECT_LOCATION = "${CURRENT_PROJECT_LOCATION}"
      $Env:MAVEN_DIR = "${MAVEN_DIR}"


      # run docker container as current use
      # set work directory to be current project
      # mount parent project directory into build
      # mount .m2 into /build/.m2 as we are running as user
      # pass maven command location for .m2 dir
      # run bash with login to allow usage of RVM
      # auto-remove container after its done

      Invoke-Expression "${DOCKER_COMPOSE_COMMAND}"

      printSubSectionEnd "Docker Compose Execute"
    } else {

      printSubSectionStart "Direct Maven Command Execute"
      debug "${MAVEN_COMMAND}" "warn"
      Invoke-Expression "${MAVEN_COMMAND}"
      printSubSectionEnd "Direct Maven Command Execute"

    }

    if ( -Not( ${TEST_SKIP_CONVERT} ) )
    {
      printSubSectionStart "Convert Reports"
      Invoke-Expression ".\asciidoctor-convert-reports -SILENT"
      printSubSectionEnd "Convert Reports"
    }


  } else {
    printSectionLine "Container is already running: $DRIVER"
  }

}

Function OpenReports
{
  [Cmdletbinding()]
  param(
    [string]$DRIVER = $args[0]
  )

  printSubSectionStart "Open Reports"

  printSectionLine "REPORTS: $DRIVER"

  $REPORT_PATH = ".\${DRIVER}\generated-docs\html\summary.html"
  printSectionLine "CHECK REPORT: ${REPORT_PATH}"

  if (Test-Path "${REPORT_PATH}" -PathType leaf)
  {
    Invoke-Item "${REPORT_PATH}"
  }

  $REPORT_PATH = ".\${DRIVER}\generated-docs\pdf\summary.pdf"
  printSectionLine "CHECK REPORT: ${REPORT_PATH}"
  if (Test-Path "${REPORT_PATH}" -PathType leaf)
  {
    Invoke-Item "${REPORT_PATH}"
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

if ( $AEM_HOST -eq "author" ) {
  $script:AEM_AVAILABLE=$(testServer "${AEM_SCHEME}://localhost:4502")
} else {
  $script:AEM_AVAILABLE=$(testServer "${AEM_SCHEME}://${AEM_HOST}:${AEM_PORT}")
}

$script:HUB_AVAILABLE=$(testServer ("${TEST_SELENIUM_URL}").Replace("/wd/hub","") )

printSectionLine "Is Selenium Hub at ${TEST_SELENIUM_URL} available? ${HUB_AVAILABLE}"
printSectionLine "Is AEM at ${AEM_SCHEME}://${AEM_HOST}:${AEM_PORT} available? ${AEM_AVAILABLE}"


if ( $HUB_AVAILABLE )
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

  RunTests ${TEST_DRIVER_NAME}

  if ( ${TEST_OPEN_REPORT} )
  {
    OpenReports "${TEST_DRIVER_NAME}"
  }

} else {
  debug "Either Selenium Hub or AEM is not currently available!" "error"
  exit 1
}

