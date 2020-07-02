#!/bin/sh

mvn clean install
docker build . -t currencyservice:1.0.0-SNAPSHOT
