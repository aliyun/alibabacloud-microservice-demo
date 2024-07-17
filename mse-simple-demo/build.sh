#!/bin/sh
# Select the correct warehouse prefix
export REGISTRY=${REGISTRY}
export VERSION="${VERSION:-3.0.8-sca-2023}"

set -e

cd "$(dirname "$0")"

./A/build.sh
./B/build.sh
./C/build.sh
./SpringCloudGateway/build.sh
./mysql/build.sh
./nacos-server/build.sh
