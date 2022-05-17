#!/bin/sh

mvn clean package
docker build . -t spring-cloud-b:1.0.0