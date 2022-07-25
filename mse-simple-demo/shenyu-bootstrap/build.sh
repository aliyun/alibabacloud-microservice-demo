#!/bin/sh
set -e

cd "$(dirname "$0")"

mvn clean package
docker build . -t shenyu-bootstrap:1.0.0
