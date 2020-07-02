#!/bin/sh

mvn clean install
docker build . -t paymentservice:1.0.0-SNAPSHOT
