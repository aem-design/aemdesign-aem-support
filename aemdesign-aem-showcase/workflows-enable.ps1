Param(
    #equivalent of using localhost in docker container
    [string]$AEM_HOST = "localhost",
    # TCP port SOURCE_CQ listens on
    [string]$AEM_PORT = "4502",
    # AEM Admin user for AEM_HOST
    [string]$AEM_USER = "admin",
    # AEM Admin password for AEM_HOST
    [string]$AEM_PASSWORD = "admin",
    # Root folder name for placing content
    [string]$SOURCE_CONTENT_FOLDER = "localhost-author-export",
    # Server WebDav Path
    #$SOURCE_WEBDAV_PATH = "/crx/server/crx.default/jcr:root/"
    [string]$SOURCE_WEBDAV_PATH = "/crx",
    [string]$AEM_SCHEMA = "http",
    #to set additional flags if required
    [string]$VLT_FLAGS = "--insecure -Xmx2g",
    [string]$VLT_CMD = "./bin/vlt",
    [string]$ROOT_PATH = "/",
    [string]$CONTENT_SOURCE = "src\main\content\jcr_root",
    # connection timeout
    [string]$TIMEOUT = "5",
    # host address
    [string]$ADDRESS = "${AEM_SCHEMA}://${AEM_HOST}:${AEM_PORT}",
    # Workflow Assets Modify path
    [string]$WORKFLOW_ASSET_MODIFY = "/conf/global/settings/workflow/launcher/config/update_asset_mod",
    # Workflow Assets Create path
    [string]$WORKFLOW_ASSET_CREATE = "/conf/global/settings/workflow/launcher/config/update_asset_create",
    # Workflow Assets Create path
    [string]$SERVICE_TO_DISABLE = "/system/console/bundles/com.day.cq.cq-mailer",

    [HashTable]$BODY_SERVICE_TO_DISABLE = @{
        "action"="stop"
    },
    [HashTable]$BODY_SERVICE_TO_DISABLE_ENABLE = @{
        "action"="start"
    },
    [HashTable]$WORKFLOW_ASSET_ENABLE_UPDATE = @{
        "jcr:primaryType"= "cq:WorkflowLauncher"
        "description"= "Update Asset - Modification"
        "enabled"= "true"
        "conditions"= "jcr:content/jcr:mimeType!=video/.*"
        "glob"= "/content/dam(/((?!/subassets).)*/)renditions/original"
        "eventType"= "16"
        "workflow"= "/var/workflow/models/dam/update_asset"
        "runModes"= "author"
        "nodetype"= "nt:file"
        "excludeList"= "event-user-data:changedByWorkflowProcess"
        "enabled@TypeHint"="Boolean"
        "eventType@TypeHint"="Long"
        "conditions@TypeHint"="String[]"
    },

    [HashTable]$WORKFLOW_ASSET_DISABLE_UPDATE = @{
        "jcr:primaryType"= "cq:WorkflowLauncher"
        "description"= "Update Asset - Modification"
        "enabled"= "false"
        "conditions"= "jcr:content/jcr:mimeType!=video/.*"
        "glob"= "/content/dam(/((?!/subassets).)*/)renditions/original"
        "eventType"= "16"
        "workflow"= "/var/workflow/models/dam/update_asset"
        "runModes"= "author"
        "nodetype"= "nt:file"
        "excludeList"= "event-user-data:changedByWorkflowProcess"
        "enabled@TypeHint"="Boolean"
        "eventType@TypeHint"="Long"
        "conditions@TypeHint"="String[]"
    },

    [HashTable]$WORKFLOW_ASSET_ENABLE_CREATE = @{
        "jcr:primaryType"= "cq:WorkflowLauncher"
        "description"= "Update Asset - Create"
        "enabled"= "true"
        "conditions"= "jcr:content/jcr:mimeType!=video/.*"
        "glob"= "/content/dam(/((?!/subassets).)*/)renditions/original"
        "eventType"= "1"
        "workflow"= "/var/workflow/models/dam/update_asset"
        "runModes"= "author"
        "nodetype"= "nt:file"
        "excludeList"= "event-user-data:changedByWorkflowProcess"
        "enabled@TypeHint"="Boolean"
        "eventType@TypeHint"="Long"
        "conditions@TypeHint"="String[]"
    },

    [HashTable]$WORKFLOW_ASSET_DISABLE_CREATE = @{
        "jcr:primaryType"= "cq:WorkflowLauncher"
        "description"= "Update Asset - Create"
        "enabled"= "false"
        "conditions"= "jcr:content/jcr:mimeType!=video/.*"
        "glob"= "/content/dam(/((?!/subassets).)*/)renditions/original"
        "eventType"= "16"
        "workflow"= "/var/workflow/models/dam/update_asset"
        "runModes"= "author"
        "nodetype"= "nt:file"
        "excludeList"= "event-user-data:changedByWorkflowProcess"
        "enabled@TypeHint"="Boolean"
        "eventType@TypeHint"="Long"
        "conditions@TypeHint"="String[]"
    },
    [switch]$Silent = $false

)

function doSlingPost {
    [CmdletBinding()]
    Param (

        [Parameter(Mandatory=$true)]
        [string]$Url="http://localhost:4502",

        [Parameter(Mandatory)]
        [ValidateNotNullOrEmpty()]
        [ValidateSet('Post','Delete')]
        [string]$Method,

        [Parameter(Mandatory=$false)]
        [HashTable]$Body,

        [Parameter(Mandatory=$false,
                HelpMessage="Provide Basic Auth Credentials in following format: <user>:<pass>")]
        [string]$BasicAuthCreds="",

        [Parameter(Mandatory=$false)]
        [string]$UserAgent="",

        [Parameter(Mandatory=$false)]
        [string]$Referer="",

        [Parameter(Mandatory=$false)]
        [string]$Timeout="5"

    )



    $HEADERS = @{
    }

    if (-not([string]::IsNullOrEmpty($UserAgent))) {
        $HEADERS.add("User-Agent",$UserAgent)
    }

    if (-not([string]::IsNullOrEmpty($Referer))) {
        $HEADERS.add("Referer",$Referer)
    }


    if (-not([string]::IsNullOrEmpty($BasicAuthCreds))) {
        $BASICAUTH = [System.Convert]::ToBase64String([System.Text.Encoding]::ASCII.GetBytes($BasicAuthCreds))
        $HEADERS.add("Authorization","Basic $BASICAUTH")
    }


    Write-Output "Performing action $Method on $Url."

    $Response = Invoke-WebRequest -Method Post -Headers $HEADERS -TimeoutSec $Timeout -Uri "$Url" -Form $Body -ContentType "application/x-www-form-urlencoded"

}

Write-Output "------- CONFIG ----------"
Write-Output "AEM_SCHEMA: $AEM_SCHEMA"
Write-Output "AEM_HOST: $AEM_HOST"
Write-Output "AEM_PORT: $AEM_PORT"
Write-Output "AEM_USER: $AEM_USER"
Write-Output "CONTENT_SOURCE: $CONTENT_SOURCE"
Write-Output "ROOT_PATH: $ROOT_PATH"
Write-Output "Silent: $Silent"
Write-Output "VLT_FLAGS: $VLT_FLAGS"
Write-Output "VLT_CMD: $VLT_CMD"

if (-not($Silent))
{
    $START = Read-Host -Prompt 'Do you want to start import with these settings? (y/n)'

    if ($START -ne "y")
    {
        Write-Output "Quiting..."
        Exit
    }
}


Write-Output "------- Enable Workflows ----------"

doSlingPost -Method Post -Referer $ADDRESS -UserAgent "curl" -Body $WORKFLOW_ASSET_ENABLE_UPDATE -Url "${ADDRESS}${WORKFLOW_ASSET_MODIFY}" -BasicAuthCreds ${AEM_USER}:${AEM_PASSWORD} -Timeout $TIMEOUT
doSlingPost -Method Post -Referer $ADDRESS -UserAgent "curl" -Body $WORKFLOW_ASSET_ENABLE_CREATE -Url "${ADDRESS}${WORKFLOW_ASSET_CREATE}" -BasicAuthCreds ${AEM_USER}:${AEM_PASSWORD} -Timeout $TIMEOUT

Write-Output "------- Done ----------"
