#!/bin/sh

mvn clean package install -Dmaven.test.skip=true
docker build . -t frontend:1.0.0-SNAPSHOT
