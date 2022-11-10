#!/bin/sh
set -e

mvn clean install -DskipTests
docker build . -t ${IMAGE_PREFIX}cartservice:1.0.0-SNAPSHOT

if [ -n "${IMAGE_PREFIX}" ]; then
  docker push ${IMAGE_PREFIX}cartservice:1.0.0-SNAPSHOT
fi
