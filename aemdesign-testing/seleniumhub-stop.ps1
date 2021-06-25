Param(
    [string]$DOCKER_SELENIUMHUB_NODE_NAME = "selenium-hub-node-chrome",
    [string]$DOCKER_SELENIUMHUB_NAME = "selenium-hub",
    [string]$DOCKER_NETWORK_NAME = "selenium-grid",
    [string]$LOG_PATH = "..\logs",
    [string]$LOG_PEFIX = "seleniumhub-stop",
    [string]$LOG_SUFFIX = ".log",
    [string]$DOCKER_LOGS_FOLDER = "..\logs\docker",
    [string]$FUNCTIONS_URI = "https://github.com/aem-design/aemdesign-docker/releases/latest/download/functions.ps1"
)

$SKIP_CONFIG = $true
$PARENT_PROJECT_PATH = ".."

. ([Scriptblock]::Create((([System.Text.Encoding]::ASCII).getString((Invoke-WebRequest -Uri "${FUNCTIONS_URI}").Content))))

$script:LOG_PATH = $LOG_PATH

$LOG_FILENAME_DATE = "$(DateStamp)"
$LOG_FILENAME = "${LOG_PEFIX}-${DOCKER_NETWORK_NAME}-${LOG_FILENAME_DATE}${LOG_SUFFIX}"

printSectionBanner "Stopping Selenium Hub containers"

printSectionLine "Stop Selenium Chrome Node..."
Invoke-Expression -Command "docker stop ${DOCKER_SELENIUMHUB_NODE_NAME}" | Tee-Object -Append -FilePath "${LOG_FILE}"

printSectionLine "Stop Selenium Hub..."
Invoke-Expression -Command "docker stop ${DOCKER_SELENIUMHUB_NAME}" | Tee-Object -Append -FilePath "${LOG_FILE}"

printSectionLine "Remove Selenium Network..."
Invoke-Expression -Command "docker network rm ${DOCKER_NETWORK_NAME}" | Tee-Object -Append -FilePath "${LOG_FILE}"

