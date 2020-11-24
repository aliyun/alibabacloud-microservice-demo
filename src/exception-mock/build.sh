#!/bin/sh

mvn clean package install -Dmaven.test.skip=true

docker build . -t exception-mock:1.0.0-SNAPSHOT
