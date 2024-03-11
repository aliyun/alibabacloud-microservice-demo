#!/bin/sh
export REGISTRY=${REGISTRY}

export appName=nacos-server
export VERSION="v2.1.2"

set -e

cd "$(dirname "$0")"

docker pull nacos/nacos-server:${VERSION}
docker tag nacos/nacos-server:${VERSION} ${REGISTRY}${appName}:${VERSION}

if [ -n "${REGISTRY}" ]; then
    docker push ${REGISTRY}${appName}:${VERSION}
fi

