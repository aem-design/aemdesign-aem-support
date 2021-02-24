Param(
  [string]$LOG_PATH = "${PWD}\logs",
  [string]$LOG_PEFIX = "${LOG_PATH}\deploy",
  [string]$LOG_SUFFIX = ".log",
  [string]$DOCKER_LOGS_FOLDER = "${PWD}\logs\docker",
  [string]$AEM_SCHEME = "http",
  [string]$AEM_HOST = "localhost",
  [string]$AEM_PORT = "4502",
  [string]$AEM_USERNAME = "admin",
  [string]$AEM_PASSWORD = "admin",
  [string]$MVN_COMMAND = "mvn -D""vault.useProxy=false"" -DskipTests -e -U -P installdeploymentpackage clean install -D""aem.port=$AEM_PORT"" -D""aem.host=$AEM_HOST"" -D""aem.username=$AEM_USERNAME"" -D""aem.password=$AEM_PASSWORD"" -D""aem.scheme=$AEM_SCHEME"" "

)

. ".\scripts\functions.ps1"

printSectionBanner "Deploying Monolith Package:" "warn"
printSectionLine "mvn -Dvault.useProxy=false -DskipTests -e -U -P installdeploymentpackage clean install"


#update host
if ( $TEST_HOST -eq "localhost" )
{
  debug "Test host is set as localhost, updating to use local ip" "info"
  $script:TEST_HOST="${LOCAL_IP}"
}

$script:ADDRESS="${AEM_SCHEMA}://${AEM_HOST}:${AEM_PORT}"
$script:AEM_AVAILABLE=$(testServer "${ADDRESS}")

printSectionLine "Is AEM at ${AEM_SCHEME}://${AEM_HOST}:${AEM_PORT} available? ${AEM_AVAILABLE}"

if ( $AEM_AVAILABLE )
{
  printSectionLine "AEM host is available!" "info"

  printSectionBanner "Disable Workflows" "warn"
  doSlingPost -Method Post -Referer $ADDRESS -UserAgent "curl" -Body $WORKFLOW_ASSET_DISABLE_UPDATE -Url "${ADDRESS}${WORKFLOW_ASSET_MODIFY}" -BasicAuthCreds ${AEM_USERNAME}:${AEM_PASSWORD} -Timeout $TIMEOUT
  doSlingPost -Method Post -Referer $ADDRESS -UserAgent "curl" -Body $WORKFLOW_ASSET_DISABLE_CREATE -Url "${ADDRESS}${WORKFLOW_ASSET_CREATE}" -BasicAuthCreds ${AEM_USERNAME}:${AEM_PASSWORD} -Timeout $TIMEOUT

#   printSectionBanner "Disable aem mailer bundle" "warn"
#   doSlingPost -Method Post -Referer $ADDRESS -UserAgent "curl" -Body $BODY_SERVICE_TO_DISABLE -Url "${ADDRESS}${SERVICE_TO_DISABLE}" -BasicAuthCreds ${AEM_USERNAME}:${AEM_PASSWORD} -Timeout $TIMEOUT

  printSectionLine "Deploying!" "info"
  Invoke-Expression -Command "$MVN_COMMAND" | Tee-Object -Append -FilePath "${LOG_FILENAME}"


  printSectionBanner "Enable Workflows" "warn"
  doSlingPost -Method Post -Referer $ADDRESS -UserAgent "curl" -Body $WORKFLOW_ASSET_ENABLE_UPDATE -Url "${ADDRESS}${WORKFLOW_ASSET_MODIFY}" -BasicAuthCreds ${AEM_USERNAME}:${AEM_PASSWORD} -Timeout $TIMEOUT
  doSlingPost -Method Post -Referer $ADDRESS -UserAgent "curl" -Body $WORKFLOW_ASSET_ENABLE_CREATE -Url "${ADDRESS}${WORKFLOW_ASSET_CREATE}" -BasicAuthCreds ${AEM_USERNAME}:${AEM_PASSWORD} -Timeout $TIMEOUT

#   printSectionBanner "Enable aem mailer bundle" "info"
#   doSlingPost -Method Post -Referer $ADDRESS -UserAgent "curl" -Body $BODY_SERVICE_TO_DISABLE_ENABLE -Url "${ADDRESS}${SERVICE_TO_DISABLE}" -BasicAuthCreds ${AEM_USERNAME}:${AEM_PASSWORD} -Timeout $TIMEOUT


} else {
  printSectionLine "AEM is not currently available!" "error"
  exit 1
}

