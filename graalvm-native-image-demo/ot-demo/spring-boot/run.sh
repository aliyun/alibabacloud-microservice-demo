#!/bin/bash
JAVA="$GRAALVM_HOME"
j=0
NUM=$#

while [ $j -lt $NUM ]; do
  case $1 in
  "--svm")
    VM_MODE="svm"
    shift 1
    j=$(($j + 1))
    ;;
  "--jvm")
    VM_MODE="jvm"
    shift 1
    j=$(($j + 1))
    ;;
  "--collect")
    COLLECT="true"
    shift 1
    j=$(($j + 1))
    ;;
  "--arms")
    LOG_MODE="arms"
    shift 1
    j=$(($j + 1))
    ;;
  "--log")
    LOG_MODE="log"
    shift 1
    j=$(($j + 1))
    ;;
  *)
    echo "option $1 is not supported"
    printUsage
    exit 1
    ;;
  esac
done

if [ x"$LOG_MODE" == "xlog" ]; then
  export OTEL_TRACES_EXPORTER=logging
  export OTEL_METRICS_EXPORTER=logging
  export OTEL_LOGS_EXPORTER=logging
elif [ x"$LOG_MODE" == "xarms" ]; then
  LOG_PROPERTIES="-Dotel.resource.attributes=service.name=native-agent-demo \
-Dotel.exporter.otlp.headers=Authentication=$ARMS_AUTH \
-Dotel.exporter.otlp.endpoint=$ARMS_ENDPOINT \
-Dotel.metrics.exporter=none"
else
  echo "Must specify logging mode. It can be --arms, or --log."
  exit 1
fi

export PORT="2327"

function curlService(){
  while true; do
    result=$(netstat -tln | grep ":${PORT} ")
    if [ -n "$result" ]; then
        curl localhost:${PORT}
        pid=`lsof -i :${PORT} | grep LISTEN | awk '{print $2}'`
        kill -2 $pid
        break
    fi
    sleep 1
  done
}

if [ x"$VM_MODE" == "xjvm" ]; then
  if [ x"$COLLECT" == "xtrue" ]; then
    NATIVE_IMAGE_AGENT="-agentpath:$JAVA/lib/libnative-image-agent.so=access-filter-file=access-filter-file.json,no-builtin-heuristic-filter,config-output-dir=native-configs,experimental-instrument,experimental-class-define-support,predefine-class-rules=io/opentelemetry/javaagent/bootstrap/field/VirtualFieldImpl"
    curlService&
  fi
  $JAVA/bin/java $NATIVE_IMAGE_AGENT $LOG_PROPERTIES -javaagent:ot-native-support-libs/opentelemetry-javaagent-1.32.0.jar -jar target/spring-ot-demo-1.0.0-SNAPSHOT.jar
elif [ x"$VM_MODE" == "xsvm" ]; then
  ./target/spring-ot-demo $LOG_PROPERTIES
else
  echo "Unrecognized VM mode, Only support --jvm and --svm"
  exit 1
fi
