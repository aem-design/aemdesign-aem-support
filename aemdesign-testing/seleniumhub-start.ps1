Param(
    [string]$DOCKER_SELENIUMHUB_NODE_NAME = "selenium-hub-node-chrome",
    [string]$DOCKER_SELENIUMHUB_NODE_IMAGE = "selenium/node-chrome",
    [string]$DOCKER_SELENIUMHUB_NODE_VERSION = "3.141.59",
    [string]$DOCKER_SELENIUMHUB_NAME = "selenium-hub",
    [string]$DOCKER_SELENIUMHUB_IMAGE = "selenium/hub",
    [string]$DOCKER_SELENIUMHUB_VERSION = "3.141.59",
    [string]$DOCKER_SELENIUMHUB_PORT = "32768",
    [string]$DOCKER_NETWORK_NAME = "selenium-grid",
    [string]$LOG_PEFIX = ".\seleniumhub",
    [string]$LOG_SUFFIX = ".log"
)

. "..\scripts\functions.ps1"

$LOG_FILENAME_DATE = "$(DateStamp)"
$LOG_FILENAME = "${LOG_PEFIX}-${DOCKER_NETWORK_NAME}-${LOG_FILENAME_DATE}${LOG_SUFFIX}"

Heading "Starting Selenium Hub containers"

Heading "Create Selenium Network..."
Invoke-Expression -Command "docker network create ${DOCKER_NETWORK_NAME}" | Tee-Object -Append -FilePath "${LOG_FILENAME}"

Heading "Create Selenium HUB..."
Invoke-Expression -Command "docker run --rm -d -p ${DOCKER_SELENIUMHUB_PORT}:4444 --net ${DOCKER_NETWORK_NAME} --name ${DOCKER_SELENIUMHUB_NAME} -e GRID_TIMEOUT=10 ${DOCKER_SELENIUMHUB_IMAGE}:${DOCKER_SELENIUMHUB_VERSION}" | Tee-Object -Append -FilePath "${LOG_FILENAME}"

Heading "Start Selenium HUB..."
Invoke-Expression -Command "docker start ${DOCKER_SELENIUMHUB_NAME}" | Tee-Object -Append -FilePath "${LOG_FILENAME}"

Heading "Create Selenium Chrome Node..."
Invoke-Expression -Command "docker run --rm -d  --net ${DOCKER_NETWORK_NAME} --shm-size=1g -e HUB_HOST=${DOCKER_SELENIUMHUB_NAME} --name ${DOCKER_SELENIUMHUB_NODE_NAME} ${DOCKER_SELENIUMHUB_NODE_IMAGE}:${DOCKER_SELENIUMHUB_NODE_VERSION}" | Tee-Object -Append -FilePath "${LOG_FILENAME}"

Heading "Start Selenium Chrome Node..."
Invoke-Expression -Command "docker start ${DOCKER_SELENIUMHUB_NODE_NAME}" | Tee-Object -Append -FilePath "${LOG_FILENAME}"
