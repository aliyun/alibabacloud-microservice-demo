#!/bin/sh

mvn clean package
docker build . -t sc-b:0.0.1-SNAPSHOT