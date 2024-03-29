Param(
    [string]$LOG_PATH = "${PWD}\..\logs",
    [string]$LOG_PEFIX = "generate-content",
    [string]$LOG_SUFFIX = ".log",
    [string]$DOCKER_LOGS_FOLDER = "${PWD}\..\logs\docker",
    [switch]$DEBUG = $false,
    [string]$COMMAND = "node generator.js --no-color --no-clean --no-console --config=core.yml "
)

$script:SKIP_CONFIG = $true

. "..\..\scripts\functions.ps1"

if ($DEBUG) {
    $script:COMMAND = "$COMMAND --debug"
}

printSectionBanner "Generating Tags"

Invoke-Expression -Command "$COMMAND" | Tee-Object -Append -FilePath "${LOG_FILENAME}"

