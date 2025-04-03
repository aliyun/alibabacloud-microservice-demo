#!/bin/sh
# Select the correct warehouse prefix
export REGISTRY=${REGISTRY}
export VERSION="3.1.0-heterogeneous"

set -e

cd "$(dirname "$0")"

./SpringCloudGateway/build.sh
./SpringCloudA/build.sh
./SpringBootB/build.sh
./GinC/build.sh
./SpringCloudD/build.sh
./nacos-server/build.sh
