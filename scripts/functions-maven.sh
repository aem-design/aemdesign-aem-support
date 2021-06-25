#!/bin/bash

DEFAULT_POM_FILE="${PARENT_PROJECT_PATH}/pom.xml"
POM_FILE="${POM_FILE:-$DEFAULT_POM_FILE}"


function getDefaultFromPom() {
    local PARAM_NAME=${1:-}
    local POM_FILE=${2:-$DEFAULT_POM_FILE}
    echo $(cat $POM_FILE | grep "<${PARAM_NAME}>" | sed -e 's/.*>\(.*\)<.*/\1/')

}

function getParamOrDefault() {
    local PARAMS="${1:-}"
    local PARAM_NAME="${2:-}"
    local POM_FILE="${3:-$DEFAULT_POM_FILE}"
    local DEFAULT_VALUE=$(getDefaultFromPom "${PARAM_NAME}" "${POM_FILE}")

    if [[ "" == "$DEFAULT_VALUE" ]]; then
        echo "DEFAULT MISSING IN POM"
    else
        PARAMS_CHECK=$(echo ${PARAMS} | grep "${PARAM_NAME}=" | sed "s/.*-D$PARAM_NAME=\([^,[:space:]]*\).*/\1/")
        if [[ "" == "$PARAMS_CHECK" ]]; then
            echo $DEFAULT_VALUE
        else
            echo $(echo ${PARAMS_CHECK} | sed -e "s/.*${PARAM_NAME}=\(.*\).*/\1/")
        fi
    fi
}

function evalMaven() {
    local PARAM=${1:-}
    echo $(mvn help:evaluate -q -DforceStdout -D"expression=$PARAM")

}



AEM_USER=$(getParamOrDefault "$SCRIPT_PARAMS" "aem.password" "$POM_FILE")
AEM_PASS=$(getParamOrDefault "$SCRIPT_PARAMS" "aem.username" "$POM_FILE")
AEM_SCHEME=$(getParamOrDefault "$SCRIPT_PARAMS" "aem.scheme" "$POM_FILE")
AEM_HOST=$(getParamOrDefault "$SCRIPT_PARAMS" "aem.host" "$POM_FILE")
AEM_PORT=$(getParamOrDefault "$SCRIPT_PARAMS" "aem.port" "$POM_FILE")
AEM_SCHEMA=$(getParamOrDefault "$SCRIPT_PARAMS" "package.uploadProtocol" "$POM_FILE")
SELENIUMHUB_HOST=$(getParamOrDefault "$SCRIPT_PARAMS" "seleniumhubhost.host" "$POM_FILE")
SELENIUMHUB_PORT=$(getParamOrDefault "$SCRIPT_PARAMS" "seleniumhubhost.port" "$POM_FILE")
SELENIUMHUB_SCHEME=$(getParamOrDefault "$SCRIPT_PARAMS" "seleniumhubhost.scheme" "$POM_FILE")
SELENIUMHUB_SERVICE=$(getParamOrDefault "$SCRIPT_PARAMS" "seleniumhubhost.service" "$POM_FILE")


if [ -z "$SKIP_PRINT_CONFIG" ]; then
    echo "Params:     $SCRIPT_PARAMS"
    echo " - POM_FILE:   ${POM_FILE:-$DEFAULT_POM_FILE}"
    echo " - AEM_USER:   $AEM_USER"
    echo " - AEM_PASS:   $(sed "s/\w/*/g" <<< ${AEM_PASS})"
    echo " - AEM_SCHEME: $AEM_SCHEME"
    echo " - AEM_HOST:   $AEM_HOST"
    echo " - AEM_PORT:   $AEM_PORT"
    echo " - AEM_SCHEMA: $AEM_SCHEMA"
    echo " - SELENIUMHUB_HOST: $SELENIUMHUB_HOST"
    echo " - SELENIUMHUB_PORT: $SELENIUMHUB_PORT"
    echo " - SELENIUMHUB_SCHEME: $SELENIUMHUB_SCHEME"
    echo " - SELENIUMHUB_SERVICE: $SELENIUMHUB_SERVICE"

    unset SKIP_PRINT_CONFIG
fi
