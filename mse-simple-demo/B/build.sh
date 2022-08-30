#!/bin/sh

cd "$(dirname "$0")"

mvn clean package
docker build . -t spring-cloud-b:1.1.0
