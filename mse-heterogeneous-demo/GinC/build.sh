#!/bin/sh

export REGISTRY=${REGISTRY}

export appName=gin-c
export VERSION=${VERSION}

set -e

cd "$(dirname "$0")"

#CGO_ENABLED=0 GOOS=linux GOARCH=amd64 go build .
CGO_ENABLED=0 GOOS=linux GOARCH=amd64 ./aliyun-go-agent go build .
chmod +x ./gin-c

docker build --platform linux/amd64 . -t ${REGISTRY}${appName}:${VERSION}

if [ -n "${REGISTRY}" ]; then
    docker push ${REGISTRY}${appName}:${VERSION}
fi