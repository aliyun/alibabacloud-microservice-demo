#!/bin/sh
set -e

cd "$(dirname "$0")"

docker build --platform linux/amd64 . -t ${REGISTRY}demo-mysql:2.0.3-jdk17

if [ -n "${REGISTRY}" ]; then
    docker push ${REGISTRY}demo-mysql:2.0.3-jdk17
fi
