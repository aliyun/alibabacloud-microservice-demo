#!/bin/sh
# Select the correct warehouse prefix
export REGISTRY=${REGISTRY}

set -e

cd "$(dirname "$0")"

./A/build.sh
./B/build.sh
./C/build.sh
./ZuulGateway/build.sh
./SpringCloudGateway/build.sh
./mysql/build.sh
