#!/bin/bash

DOCKER=$(/usr/bin/which docker 2>/dev/null)

if [[ "$OS" == "windowsbash" ]]; then
	DOCKER="docker.exe"
fi