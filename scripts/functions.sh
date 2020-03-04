#!/bin/bash

CURRENT_PATH="$(pwd)"
PARENT_PROJECT_PATH="${PARENT_PROJECT_PATH:-$CURRENT_PATH}"
SCRIPTS_PARENT="${SCRIPTS_PARENT:-$PARENT_PROJECT_PATH}/scripts"

source "$SCRIPTS_PARENT/functions-common.sh"
source "$SCRIPTS_PARENT/functions-debug.sh"
source "$SCRIPTS_PARENT/functions-maven.sh"
source "$SCRIPTS_PARENT/functions-curl.sh"
source "$SCRIPTS_PARENT/functions-vlt.sh"
source "$SCRIPTS_PARENT/functions-fswatch.sh"
source "$SCRIPTS_PARENT/functions-docker.sh"
source "$SCRIPTS_PARENT/functions-getopt.sh"
source "$SCRIPTS_PARENT/functions-strings.sh"

if [[ $INTELLIJ == "true" ]]; then source "./functions-common.sh"; fi
if [[ $INTELLIJ == "true" ]]; then source "./functions-debug.sh"; fi
if [[ $INTELLIJ == "true" ]]; then source "./functions-maven.sh"; fi
if [[ $INTELLIJ == "true" ]]; then source "./functions-curl.sh"; fi
if [[ $INTELLIJ == "true" ]]; then source "./functions-vlt.sh"; fi
if [[ $INTELLIJ == "true" ]]; then source "./functions-fswatch.sh"; fi
if [[ $INTELLIJ == "true" ]]; then source "./functions-docker.sh"; fi
if [[ $INTELLIJ == "true" ]]; then source "./functions-getopt.sh"; fi
if [[ $INTELLIJ == "true" ]]; then source "./functions-strings.sh"; fi