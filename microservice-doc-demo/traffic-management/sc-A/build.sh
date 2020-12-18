#!/bin/sh

mvn clean package
docker build . -t sc-a:0.0.1-SNAPSHOT