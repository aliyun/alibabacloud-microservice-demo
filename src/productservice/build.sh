#!/bin/sh

mvn clean install
docker build . -t productservice:1.0.0-SNAPSHOT
