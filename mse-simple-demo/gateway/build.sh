#!/bin/sh
set -e

cd "$(dirname "$0")"

mvn clean package
docker build . -t spring-cloud-zuul:1.1.0
