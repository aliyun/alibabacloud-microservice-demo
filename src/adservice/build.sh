#!/bin/sh

mvn clean install
docker build . -t adservice:1.0.0-SNAPSHOT
