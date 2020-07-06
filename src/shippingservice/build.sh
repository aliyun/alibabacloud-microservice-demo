#!/bin/sh

mvn clean install
docker build . -t shippingservice:1.0.0-SNAPSHOT
