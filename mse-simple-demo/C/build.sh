#!/bin/sh
set -e

cd "$(dirname "$0")"

docker build --platform linux/amd64 . -t ${REGISTRY}spring-cloud-c:2.0.1

if [ -n "${REGISTRY}" ]; then
    docker push ${REGISTRY}spring-cloud-c:2.0.1
fi
