#!/bin/sh

mvn clean package
docker build . -t sc-zuul:1.0-SNAPSHOT