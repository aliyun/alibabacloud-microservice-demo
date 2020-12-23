#!/bin/sh

java \
  -Dcom.sun.management.jmxremote.port=19991 \
  -Dcom.sun.management.jmxremote.authenticate=false \
  -Dcom.sun.management.jmxremote.ssl=false \
  -jar /app/frontend-1.0.0-SNAPSHOT.jar
