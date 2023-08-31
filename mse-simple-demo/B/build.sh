#!/bin/sh

export REGISTRY=${REGISTRY}

export appName=spring-cloud-b
export version=3.0.2

set -e

cd "$(dirname "$0")"

mvn clean package
docker build --platform linux/amd64 . -t ${REGISTRY}${appName}:${version}

if [ -n "${REGISTRY}" ]; then
    docker push ${REGISTRY}${appName}:${version}
fi
