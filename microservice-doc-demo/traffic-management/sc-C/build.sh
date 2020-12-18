#!/bin/sh

mvn clean package
docker build . -t sc-c:0.0.1-SNAPSHOT