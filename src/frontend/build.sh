#!/bin/sh
set -e

mvn clean install -Dmaven.test.skip=true
docker build . -t ${IMAGE_PREFIX}frontend:1.0.0-SNAPSHOT

if [ -n "${IMAGE_PREFIX}" ]; then
  docker push ${IMAGE_PREFIX}frontend:1.0.0-SNAPSHOT
fi
