#!/bin/sh

mvn clean install
docker build . -t emailservice:1.0.0-SNAPSHOT
