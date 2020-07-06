#!/bin/sh

mvn clean install
docker build . -t checkoutservice:1.0.0-SNAPSHOT
