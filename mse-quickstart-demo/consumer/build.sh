#!/bin/sh

mvn clean package
docker build . -t consumer:1.0.0