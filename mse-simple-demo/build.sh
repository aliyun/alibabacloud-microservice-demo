#!/bin/sh
# Select the correct warehouse prefix
export REGISTRY=${REGISTRY}
export VERSION="${VERSION:-3.0.8-sb3-jdk21}"

set -e

cd "$(dirname "$0")"

./A/build.sh
./B/build.sh
./C/build.sh
./ZuulGateway/build.sh
./SpringCloudGateway/build.sh
./mysql/build.sh
./nacos-server/build.sh
