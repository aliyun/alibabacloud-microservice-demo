#!/bin/sh

mvn clean package
docker build . -t provider:1.0.0