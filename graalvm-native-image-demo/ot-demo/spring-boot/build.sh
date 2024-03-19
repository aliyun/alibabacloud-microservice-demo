#!/bin/bash
NATIVE_LIBS_DIR="ot-native-support-libs"
INST_JAR="instrument.jar"
# NATIVE_LIBS_DIR and ot agent jar should be created when running 'mvn package' previously
pushd $NATIVE_LIBS_DIR
if [ ! -f $INST_JAR ]; then
  jar xf opentelemetry-javaagent-1.32.0.jar inst/* -d inst

  pushd inst
  find . -type f -name "*.classdata" | while read -r file; do
    mv "$file" "${file%.classdata}.class"
  done
  jar cf ../$INST_JAR *
  popd

  mvn install:install-file -Dfile=$INST_JAR -DgroupId=io.opentelemetry.javaagent -DartifactId=opentelemetry-javaagent-inst-native-support -Dversion=1.32.0 -Dpackaging=jar
fi
popd

pushd ../../opentelemetry-agent-native
mvn install
popd

mvn -Pnative package
