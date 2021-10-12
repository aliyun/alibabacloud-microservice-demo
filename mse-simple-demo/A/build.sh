#!/bin/sh

mvn clean package
docker build . -t spring-cloud-a:1.0.0