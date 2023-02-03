#!/bin/sh
set -e

cd "$(dirname "$0")"

docker build . -t ${REGISTRY}spring-cloud-c:1.2.0-ons-client

if [ -n "${REGISTRY}" ]; then
    docker push ${REGISTRY}spring-cloud-c:1.2.0-ons-client
fi
