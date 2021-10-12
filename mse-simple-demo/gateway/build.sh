#!/bin/sh

mvn clean package
docker build . -t spring-cloud-zuul:1.0.0