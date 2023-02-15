#!/bin/sh
set -e

./A/build.sh
./B/build.sh
./C/build.sh
./gateway/build.sh
