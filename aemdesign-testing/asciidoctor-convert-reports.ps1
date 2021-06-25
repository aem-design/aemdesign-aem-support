Param(
    [string]$LOG_PATH = "..\logs",
    [string]$LOG_PEFIX = "asciidoctor",
    [string]$LOG_SUFFIX = ".log",
    [string]$DOCKER_LOGS_FOLDER = "..\logs\docker",
    [switch]$SILENT = $false,
    [string]$TEST_IMAGE = "aemdesign/centos-java-buildpack",
    [switch]$TEST_OPEN_REPORT = $false,
    [string]$TEST_REPORT_PATH = "generated-docs/summary.html",
    [string]$TEST_DRIVER_NAME = "remote-seleniumhub-chrome",
    [string]$DOCKER_SCRIPT = "./asciidoctor-convert-reports -images ${TEST_DRIVER_NAME}/generated-docs/pdf -root . -source coderay -base ./ -input ${TEST_DRIVER_NAME}/spock-reports -output ${TEST_DRIVER_NAME}/generated-docs -filter *.ad -html",
    [string]$FUNCTIONS_URI = "https://github.com/aem-design/aemdesign-docker/releases/latest/download/functions.ps1"
)

$SKIP_CONFIG = $true
$PARENT_PROJECT_PATH = ".."

. ([Scriptblock]::Create((([System.Text.Encoding]::ASCII).getString((Invoke-WebRequest -Uri "${FUNCTIONS_URI}").Content))))

$script:LOG_PATH = $LOG_PATH

$LOG_FILENAME_DATE = "$(DateStamp)"
$LOG_FILENAME = "${LOG_PEFIX}-${DOCKER_NETWORK_NAME}-${LOG_FILENAME_DATE}${LOG_SUFFIX}"

printSectionBanner "Converting report to HTML"

$CURRENT_PATH = ${PWD}
$PROJECT_ROOT_DIR="$( Resolve-Path ${PARENT_PROJECT_PATH} )"
$MAVEN_DIR="$(Resolve-Path ~/.m2)"
$PARENT_PROJECT_WITH_GIT = $(getGitDir)
$PARENT_PROJECT_WITH_GIT = ( $PARENT_PROJECT_WITH_GIT -split "\.git" )[0]
$PARENT_PROJECT_WITH_GIT_NAME = $( Resolve-Path "${PARENT_PROJECT_WITH_GIT}" | Split-Path -Leaf )
$PARENT_PROJECT_LOCATION = $( Resolve-Path "${PWD}\.." )
$PARENT_PROJECT_NAME = $( Resolve-Path "$PARENT_PROJECT_LOCATION" | Split-Path -Leaf )
$PROJECT_NAME = $( Resolve-Path "${PWD}" | Split-Path -Leaf )

$REPORT_ROOT="/build/${PARENT_PROJECT_WITH_GIT_NAME}/${PROJECT_NAME}"


$DOCKER_COMMAND="docker run --rm --net=host --name ${TEST_DRIVER_NAME} -v ${PARENT_PROJECT_WITH_GIT}:/build/${PARENT_PROJECT_WITH_GIT_NAME} -v ${MAVEN_DIR}:/build/.m2 -w ""/build/${PARENT_PROJECT_WITH_GIT_NAME}/${PROJECT_NAME}"" ${TEST_IMAGE} bash -l -c '${DOCKER_SCRIPT} -Dmaven.repo.local=/build/.m2/repository' "


printSectionLine "Project Root: ${PROJECT_ROOT_DIR}"
printSectionLine "Maven Plugins Directory: ${MAVEN_DIR}"
printSectionLine "Parent Project with GIT Directory: ${PARENT_PROJECT_WITH_GIT}"
printSectionLine "Parent Project with GIT Directory Name: ${PARENT_PROJECT_WITH_GIT_NAME}"
printSectionLine "Parent Project Name: ${PARENT_PROJECT_NAME}"
printSectionLine "Current Project Name: ${PROJECT_NAME}"
printSectionLine "Testing Directory: ${CURRENT_PATH}"
printSectionLine "Testing Sub Directory: ${PARENT_PROJECT_LOCATION}"
printSectionLine "Docker Script:"
printSectionLine " ${DOCKER_SCRIPT}"
printSectionLine "Docker Command:"
printSectionLine " ${DOCKER_COMMAND}"

if ( -Not( $SILENT ) )
{
  $START = Read-Host -Prompt "Do you want to start converting reports with these settings? (y/n)"

  if ($START -ne "y")
  {
    Write-Output "Quiting..."
    Exit
  }
}

printSectionLine "Fixing report asset paths:"
printSectionLine " - ${REPORT_ROOT}/${TEST_DRIVER_NAME}/test-screenshots/"
printSectionLine " - ${REPORT_ROOT}/${TEST_DRIVER_NAME}/test-reports/"
printSectionLine " - ${REPORT_ROOT}/src/test/screenshots/${TEST_DRIVER_NAME}/RemoteWebDriver/"

$SPOCK_REPORTS = Get-ChildItem "${TEST_DRIVER_NAME}" *.ad -rec
foreach ($REPORT in $SPOCK_REPORTS)
{
    (Get-Content $REPORT.PSPath) |
    Foreach-Object { $_ -replace "(?<=(link|image)\:).*\/${TEST_DRIVER_NAME}\/test-screenshots\/", "${REPORT_ROOT}/${TEST_DRIVER_NAME}/test-screenshots/" } |
    Foreach-Object { $_ -replace "(?<=(link|image)\:).*\/${TEST_DRIVER_NAME}\/test-reports\/", "${REPORT_ROOT}/${TEST_DRIVER_NAME}/test-reports/" } |
    Foreach-Object { $_ -replace "(?<=(link|image)\:).*\/${TEST_DRIVER_NAME}\/RemoteWebDriver\/", "${REPORT_ROOT}/src/test/screenshots/${TEST_DRIVER_NAME}/RemoteWebDriver/" } |
    Set-Content $REPORT.PSPath
}


debug "${DOCKER_COMMAND}"

printSubSectionStart "Docker Command Execute"

Invoke-Expression "${DOCKER_COMMAND}"



