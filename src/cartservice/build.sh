#!/bin/sh

mvn clean install
docker build . -t cartservice:1.0.0-SNAPSHOT
