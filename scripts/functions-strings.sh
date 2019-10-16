#!/bin/bash

function isStringMatchesRegex() {
    local STRING=${1?Need string}
    local REGEX=${2?Need regex}
   echo "${STRING}" | $GREP -Eq "$REGEX" && echo "true" || echo "false"
}
