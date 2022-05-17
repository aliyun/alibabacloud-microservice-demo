#!/bin/sh

mvn clean package
docker build . -t spring-cloud-c:1.0.0