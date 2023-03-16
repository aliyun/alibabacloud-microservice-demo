#!/bin/sh
set -e

cd "$(dirname "$0")"

docker build --platform linux/amd64 . -t ${REGISTRY}demo-mysql:2.0.2

if [ -n "${REGISTRY}" ]; then
    docker push ${REGISTRY}demo-mysql:2.0.2
fi