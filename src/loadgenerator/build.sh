#!/bin/sh
set -e

docker build . -t ${IMAGE_PREFIX}loadgenerator:1.0.0-SNAPSHOT

if [ -n "${IMAGE_PREFIX}" ]; then
  docker push ${IMAGE_PREFIX}loadgenerator:1.0.0-SNAPSHOT
fi
