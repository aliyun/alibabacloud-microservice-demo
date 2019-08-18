#!/bin/sh

mvn clean package
docker build . -t frontend:1.0.0-SNAPSHOT
