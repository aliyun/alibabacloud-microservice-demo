#!/bin/sh

cd "$(dirname "$0")"

docker build --platform linux/amd64 . -t ${REGISTRY}spring-cloud-b:1.2.0

if [ -n "${REGISTRY}" ]; then
    docker push ${REGISTRY}spring-cloud-b:1.2.0
fi
