#!/bin/sh
set -e

cd "$(dirname "$0")"

./A/build.sh
./B/build.sh
./C/build.sh
./gateway/build.sh
./mysql/build.sh
