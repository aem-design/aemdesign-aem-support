Param(
  [string]$LOG_PATH = ".\logs",
  [string]$LOG_PEFIX = "start",
  [string]$LOG_SUFFIX = ".log",
  [string]$FUNCTIONS_URI = "https://github.com/aem-design/aemdesign-docker/releases/latest/download/functions.ps1",
  [string]$SERVICES = "$( (Get-Content ".\start-services.conf" | Where-Object {$_ -notmatch '^#.*'} | Where-Object {-not([String]::IsNullOrWhiteSpace($_))} ) -join " -f ")",
  [string]$PORT = "5080"
)

$SKIP_CONFIG = $true
$PARENT_PROJECT_PATH = "."

. ([Scriptblock]::Create((([System.Text.Encoding]::ASCII).getString((Invoke-WebRequest -Uri "${FUNCTIONS_URI}").Content))))

$DOCKER_COMMAND="docker-compose --env-file .env -f docker-compose.yml -f ${SERVICES} down --remove-orphans"

printSubSectionStart "Docker Compose Execute"

printSectionLine "${DOCKER_COMMAND}"

Invoke-Expression "${DOCKER_COMMAND}"
