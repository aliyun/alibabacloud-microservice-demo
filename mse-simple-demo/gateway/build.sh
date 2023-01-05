#!/bin/sh
set -e

cd "$(dirname "$0")"

docker build . -t ${REGISTRY}spring-cloud-zuul:1.1.0-eureka

if [ -n "${REGISTRY}" ]; then
    docker push ${REGISTRY}spring-cloud-zuul:1.1.0-eureka
fi
