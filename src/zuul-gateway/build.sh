#!/bin/sh

mvn clean package
docker build . -t zuul-gateway:1.0-SNAPSHOT
