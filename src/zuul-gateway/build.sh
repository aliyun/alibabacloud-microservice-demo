#!/bin/sh
set -e

mvn clean package -DskipTests
docker build . -t ${IMAGE_PREFIX}zuul-gateway:1.0.0-SNAPSHOT

if [ -n "${IMAGE_PREFIX}" ]; then
  docker push ${IMAGE_PREFIX}zuul-gateway:1.0.0-SNAPSHOT
fi
