#!/bin/bash

VLT="$(realpath $PARENT_PROJECT_PATH/tools/vault-cli/bin/vlt)"
REPO="$(realpath $PARENT_PROJECT_PATH/tools/vault-cli/bin/lib)"

function vltCheckout() {
    local CRX_CREDENTIALS=${1?Please specify CREDENTIALS}
    local CRX_HOST=${2?Please specify HOST}
    local CRX_JCR_ROOT=${3?Please specify JCR ROOT PATH}
    local CRX_FILTER=${4?Please specify FLTER PATH}

    $VLT --credentials $CRX_CREDENTIALS checkout --force --filter $CRX_FILTER $CRX_HOST $CRX_JCR_ROOT
}

function vltUpdate() {
    local CRX_CREDENTIALS=${1?Please specify CREDENTIALS}
    local CRX_HOST=${2?Please specify HOST}
    local CRX_JCR_ROOT=${3?Please specify JCR ROOT PATH}
    local CRX_FILTER=${4?Please specify FLTER PATH}

    $VLT --credentials $CRX_CREDENTIALS update --force --filter $CRX_FILTER $CRX_HOST $CRX_JCR_ROOT
}

function vltCheckin() {
    local CRX_CREDENTIALS=${1?Please specify CREDENTIALS}
    local CRX_HOST=${2?Please specify HOST}
    local CRX_JCR_ROOT=${3?Please specify JCR ROOT PATH}

    $VLT --credentials "$CRX_CREDENTIALS" commit --force ${@:4}
}

function vltSyncRegister() {
    local CRX_CREDENTIALS=${1?Please specify CREDENTIALS}
    local CRX_HOST=${2?Please specify HOST}
    local CRX_JCR_ROOT=${3?Please specify JCR ROOT PATH}

    #This will sync your CRX to Disk and Vice Versa
    #Install Syc Service
    $VLT --credentials $CRX_CREDENTIALS sync install --uri $CRX_HOST
    #Register Dir for Sync
    $VLT --credentials $CRX_CREDENTIALS sync register --uri $CRX_HOST $CRX_JCR_ROOT
}

function vltSyncUnRegister() {
    local CRX_CREDENTIALS=${1?Please specify CREDENTIALS}
    local CRX_HOST=${2?Please specify HOST}
    local CRX_JCR_ROOT=${3?Please specify JCR ROOT PATH}

    #UnRegister Dir for Sync
    $VLT --credentials $CRX_CREDENTIALS sync unregister --uri $CRX_HOST $CRX_JCR_ROOT
}


function vltSyncStatus() {
    local CRX_CREDENTIALS=${1?Please specify CREDENTIALS}
    local CRX_HOST=${2?Please specify HOST}

    #UnRegister Dir for Sync
    echo $($VLT --credentials $CRX_CREDENTIALS sync status --uri $CRX_HOST)
}