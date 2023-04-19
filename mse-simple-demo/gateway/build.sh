#!/bin/sh
set -e

cd "$(dirname "$0")"

docker build --platform linux/amd64 . -t ${REGISTRY}spring-cloud-zuul:2.0.3

if [ -n "${REGISTRY}" ]; then
    docker push ${REGISTRY}spring-cloud-zuul:2.0.3
fi
