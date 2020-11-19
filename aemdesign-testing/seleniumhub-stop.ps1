Param(
    [string]$DOCKER_SELENIUMHUB_NODE_NAME = "selenium-hub-node-chrome",
    [string]$DOCKER_SELENIUMHUB_NAME = "selenium-hub",
    [string]$DOCKER_NETWORK_NAME = "selenium-grid",
    [string]$LOG_PEFIX = ".\seleniumhub",
    [string]$LOG_SUFFIX = ".log"
)

. "..\scripts\functions.ps1"

$LOG_FILENAME_DATE = "$(DateStamp)"
$LOG_FILENAME = "${LOG_PEFIX}-${DOCKER_NETWORK_NAME}-${LOG_FILENAME_DATE}${LOG_SUFFIX}"

Heading "Stopping Selenium Hub containers"

Heading "Stop Selenium Chrome Node..."

Invoke-Expression -Command "docker stop ${DOCKER_SELENIUMHUB_NODE_NAME}" | Tee-Object -Append -FilePath "${LOG_FILENAME}"

Heading "Stop Selenium Hub..."
Invoke-Expression -Command "docker stop ${DOCKER_SELENIUMHUB_NAME}" | Tee-Object -Append -FilePath "${LOG_FILENAME}"

Heading "Remove Selenium Network..."

Invoke-Expression -Command "docker network rm ${DOCKER_NETWORK_NAME}" | Tee-Object -Append -FilePath "${LOG_FILENAME}"

