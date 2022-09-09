Param(
  [string]$LOG_PATH = ".\logs",
  [string]$LOG_PEFIX = "start",
  [string]$LOG_SUFFIX = ".log",
  [string]$FUNCTIONS_URI = "https://github.com/aem-design/aemdesign-docker/releases/latest/download/functions.ps1",
  [string]$SERVICES = "$( (Get-Content ".\start-services.conf" | Where-Object {$_ -notmatch '^#.*'} | Where-Object {-not([String]::IsNullOrWhiteSpace($_))} ) -join " --file ")",
  [string]$PORT = "443",
  [Parameter(Position=0)]
  [string]$SERVICE_NAME = "" # services to start
)

$SKIP_CONFIG = $true
$PARENT_PROJECT_PATH = "."

. ([Scriptblock]::Create((([System.Text.Encoding]::ASCII).getString((Invoke-WebRequest -Uri "${FUNCTIONS_URI}").Content))))


# get env config
$CONFIG_ENV = (Get-Content ".env" -Raw | ConvertFrom-StringData)
$PORT=$CONFIG_ENV.TRAEFIK_HTTP_PORT

$DOCKER_COMMAND="docker-compose --env-file .env --file docker-compose.yml --file ${SERVICES} up ${SERVICE_NAME}"

if ( [string]::IsNullOrEmpty(${SERVICE_NAME}) ) {

  $OPEN_COMMAND="explorer https://localhost"

  printSubSectionStart "Open Console at https://localhost"

  Invoke-Expression "${OPEN_COMMAND}"

}

printSubSectionStart "Docker Compose Execute"

printSectionLine "${DOCKER_COMMAND}"

Invoke-Expression "${DOCKER_COMMAND}"


