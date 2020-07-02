#!/bin/sh

mvn clean install
docker build . -t recomendationservice:1.0.0-SNAPSHOT
