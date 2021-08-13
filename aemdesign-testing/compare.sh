#!/bin/bash

screenshotFilename=${1}
referenceFilename=${2}
differenceFilename=${3}

echo "screenshotFilename: ${screenshotFilename}"
echo "referenceFilename: ${referenceFilename}"
echo "differenceFilename: ${differenceFilename}"

compare -verbose -metric mae -fuzz 10% "${screenshotFilename}" "${referenceFilename}" "${differenceFilename}" 2>&1 || convert "${screenshotFilename}" "${referenceFilename}" -compose difference -composite +level-colors black,red "${differenceFilename}" 2>&1 | echo "all: 1.0 (1.0)"
