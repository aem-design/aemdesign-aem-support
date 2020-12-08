Param(
  [string]$DOCKER_SELENIUMHUB_NODE_NAME = "selenium-hub-node-chrome",
  [string]$DOCKER_SELENIUMHUB_NAME = "selenium-hub",
  [string]$DOCKER_NETWORK_NAME = "selenium-grid",
  [string]$LOG_PATH = "${PWD}\logs\testspec",
  [string]$LOG_PEFIX = "${LOG_PATH}\test-spec",
  [string]$LOG_SUFFIX = ".log",
  [string]$DOCKER_LOGS_FOLDER = "${PWD}\logs\docker",
  [string]$DRIVER_FOLDER = "${PWD}\drivers",
  [switch]$LOGIN = $true,
  [switch]$REPORT = $true,
  [switch]$DEBUG = $false,
  [string]$TEST_SELENIUMHUB_SCHEME = $SELENIUMHUB_SCHEME,
  [string]$TEST_SELENIUMHUB_PORT = $SELENIUMHUB_PORT,
  [string]$TEST_SELENIUMHUB_SERVICE = $SELENIUMHUB_SERVICE,
  [string]$TEST_SELENIUM_URL = "${TEST_SELENIUMHUB_SCHEME}://${LOCAL_IP}:${TEST_SELENIUMHUB_PORT}${TEST_SELENIUMHUB_SERVICE}",
  [string]$TEST_IMAGE = "aemdesign/centos-java-buildpack",
  [switch]$TEST_OPEN_REPORT = $false,
  [string]$TEST_REPORT_PATH = "generated-docs/summary.html",
  [string]$TEST_DRIVER_NAME = "remote-seleniumhub-chrome",
  [string]$TEST_SCHEME = "$AEM_SCHEME",
  [string]$TEST_HOST = "$AEM_HOST",
  [string]$TEST_PORT = "$AEM_PORT",
  [string]$TEST_USERNAME = "$AEM_USER",
  [string]$TEST_PASSWORD = "$AEM_PASS",
  [string]$TEST_SPECS = "$( (Get-Content ".\test-list") -join ",")",
  [string]$TEST_WORKSPACE = "",
  [switch]$TEST_LOGIN = $false,
  [string]$TEST_MAVEN_CONFIG = "",
  [switch]$TEST_DISPATCHER = $false,
  [switch]$TEST_SKIP_CONVERT = $false,
  [switch]$TEST_USING_MAVEN = $false

)

. "..\scripts\functions.ps1"


Function Get-MavenCommand
{
  [Cmdletbinding()]
  [Alias("getMavenCommand")]
  param
  (
    [Parameter(ValueFromPipeline)]
    [string]$DISPATCHER = $args[0],
    [string]$DRIVER = $args[1],
    [string]$TEST_HOST = $args[2],
    [string]$LOGIN = $args[3],
    [string]$MAVEN_CONFIG = $args[4],
    [string]$PASSWORD = $args[5],
    [string]$PORT = $args[6],
    [string]$SCHEME = $args[7],
    [string]$SPECS = $args[8],
    [string]$SELENIUM_URL = $args[9],
    [string]$USERNAME = $args[10]
  )

  if ( -Not( $TEST_SKIP_CONVERT ) ) {
    $MAVEN_EXTRAS = "-P compile-reports-ruby"
  }

  return "mvn clean test ${MAVEN_EXTRAS} -D""geb.env=${DRIVER}"" -D""project.buildDirectory=${DRIVER}"" -D""crx.scheme=${SCHEME}"" -D""crx.host=${TEST_HOST}"" -D""crx.port=${PORT}"" -D""crx.username=${USERNAME}"" -D""crx.password=${PASSWORD}"" -D""test=${SPECS}"" -D""selenium.huburl=${SELENIUM_URL}"" -D""login.req=${LOGIN}"" -D""test.dispatcher=${DISPATCHER}"" ${MAVEN_CONFIG}"

}

Function Do-RunTests
{
  [Cmdletbinding()]
  [Alias("runTests")]
  param(
    [string[]]$TEST_DRIVER_NAME = $args[0]
  )

  $global:DRIVERS_EXEC = $TEST_DRIVER_NAME
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

  $MAVEN_COMMAND=$(getMavenCommand "${TEST_DISPATCHER}" "${DRIVER}" "${TEST_HOST}" "${TEST_LOGIN}" "${TEST_MAVEN_CONFIG}" "${TEST_PASSWORD}" "${TEST_PORT}" "${TEST_SCHEME}" "${TEST_SPECS}" "${TEST_SELENIUM_URL}" "${TEST_USERNAME}")

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
    $PROJECT_ROOT_DIR="$( realpath ${PARENT_PROJECT_PATH} )"
    $MAVEN_DIR="$(realpath ~/.m2)"

    printSectionLine "Project Root Directory: ${PROJECT_ROOT_DIR}"
    printSectionLine "Maven Directory: ${MAVEN_DIR}"

    if ( -Not( ${TEST_USING_MAVEN} ) )
    {
      $PARENT_PROJECT_WITH_GIT = $(getGitDir)
      $PARENT_PROJECT_WITH_GIT = ( $PARENT_PROJECT_WITH_GIT -split ".git" )[0]
      $PARENT_PROJECT_WITH_GIT_NAME = $( basename ${PARENT_PROJECT_WITH_GIT} )
      $PARENT_PROJECT_LOCATION = $( realpath "${PWD}\.." )
      $PARENT_PROJECT_NAME = $(basename "$PARENT_PROJECT_LOCATION")
      $PROJECT_NAME = $(basename "${PWD}")

      printSectionLine "Parent Project with GIT Directory: ${PARENT_PROJECT_WITH_GIT}"
      printSectionLine "Parent Project with GIT Directory Name: ${PARENT_PROJECT_WITH_GIT_NAME}"
      printSectionLine "Parent Project Name: ${PARENT_PROJECT_NAME}"
      printSectionLine "Curren Project Name: ${PROJECT_NAME}"
      printSectionLine "Testing Directory: ${CURRENT_PATH}"
      printSectionLine "Testing Sub Directory: ${PARENT_PROJECT_LOCATION}"

      # run docker container as current use
      # set work directory to be current project
      # mount parent project directory into build
      # mount .m2 into /build/.m2 as we are running as user
      # pass maven command location for .m2 dir
      # run bash with login to allow usage of RVM
      # auto-remove container after its done
      $DOCKER_COMMAND="docker run -d --user $(id -u):$(id -g) --rm --name ${DRIVER} -v ${PARENT_PROJECT_WITH_GIT}:/build/${PARENT_PROJECT_WITH_GIT_NAME} -v ${MAVEN_DIR}:/build/.m2 -w ""/build/${PARENT_PROJECT_WITH_GIT_NAME}/${PARENT_PROJECT_NAME}/${PROJECT_NAME}"" ${TEST_IMAGE} bash -l -c '${MAVEN_COMMAND} -Dmaven.repo.local=/build/.m2/repository' "

      debug "${DOCKER_COMMAND}"

      printSubSectionStart "Docker Command Execute"

      Invoke-Expression "${DOCKER_COMMAND}"

      printSubSectionEnd "Docker Command Execute"
    } else {
      printSubSectionStart "Direct Maven Command Execute"
      debug "${MAVEN_COMMAND}"
      Invoke-Expression "${MAVEN_COMMAND}"
      printSubSectionEnd "Direct Maven Command Execute"
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

  $global:OPEN_REPORTS = $TEST_DRIVER_NAME
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

  $global:OPEN_REPORTS = $TEST_DRIVER_NAME
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



printSectionBanner "Starting Tests $(LocalIP)"

# update selenium url if localhost and not passed
if ( $SELENIUMHUB_HOST -eq "localhost" -Or -not ([string]::IsNullOrEmpty(${TEST_SELENIUM_URL})) )
{
  debug "Selenium hub url not specified or set to localhost, updating to use local ip" "info"
  $global:TEST_SELENIUM_URL="${TEST_SELENIUMHUB_SCHEME}://${LOCAL_IP}:${TEST_SELENIUMHUB_PORT}${TEST_SELENIUMHUB_SERVICE}"
}

#update host
if ( $TEST_HOST -eq "localhost" )
{
  debug "Test host is set as localhost, updating to use local ip" "info"
  $global:TEST_HOST="${LOCAL_IP}"
}

printSectionBanner "Test Configuration:" "warn"
printSectionLine "Scheme:                 ${TEST_SCHEME}"
printSectionLine "Host:                   ${TEST_HOST}"
printSectionLine "Port:                   ${TEST_PORT}"
printSectionLine "Selenium URL:           ${TEST_SELENIUM_URL}"
printSectionLine "Test Specs:             ${TEST_SPECS}"
printSectionLine "Workspace:              ${TEST_WORKSPACE}"
printSectionLine "Authenticate?           ${TEST_LOGIN}"
printSectionLine "Custom Maven Config:    ${TEST_MAVEN_CONFIG}"
printSectionLine "Run on Dispatcher?      ${TEST_DISPATCHER}"
printSectionLine "Skip Report Conversion? ${TEST_SKIP_CONVERT}"
printSectionLine "Run plain Maven?        ${TEST_USING_MAVEN}"

$global:AEM_AVAILABLE=$(testServer "${TEST_SCHEME}://${TEST_HOST}:${TEST_PORT}")
$global:HUB_AVAILABLE=$(testServer ("${TEST_SELENIUM_URL}").Replace("/wd/hub","") )

printSectionLine "Is Selenium Hub at ${TEST_SELENIUM_URL} available? ${HUB_AVAILABLE}"
printSectionLine "Is AEM at ${TEST_SCHEME}://${TEST_HOST}:${TEST_PORT} available? ${AEM_AVAILABLE}"

if ( $HUB_AVAILABLE -And $AEM_AVAILABLE )
{
  debug "Selenium Hub and AEM are both up and available!" "info"

  runTests ${TEST_DRIVER_NAME}

  if ( -Not( ${TEST_USING_MAVEN} ) )
  {
    monitorTests ${TEST_DRIVER_NAME}
  }

} else {
  debug "Either Selenium Hub or AEM is not currently available!" "error"
  exit 1
}

