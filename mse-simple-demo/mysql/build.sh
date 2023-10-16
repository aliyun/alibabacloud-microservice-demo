#!/bin/sh
export REGISTRY=${REGISTRY}

export appName=demo-mysql
export version=3.0.3

set -e

cd "$(dirname "$0")"

docker build --platform linux/amd64 . -t ${REGISTRY}${appName}:${version}

if [ -n "${REGISTRY}" ]; then
    docker push ${REGISTRY}${appName}:${version}
fi