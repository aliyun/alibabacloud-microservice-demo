#!/bin/sh
set -e

cd "$(dirname "$0")"

docker build --platform linux/amd64 . -t ${REGISTRY}spring-cloud-a:1.2.0

if [ -n "${REGISTRY}" ]; then
    docker push ${REGISTRY}spring-cloud-a:1.2.0
fi
