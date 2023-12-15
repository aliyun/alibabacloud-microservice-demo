#!/bin/bash

shellName="app.sh"

PRG="$0"

while [ -h "$PRG" ]; do
  ls=`ls -ld "$PRG"`
  link=`expr "$ls" : '.*-> \(.*\)$'`
  if expr "$link" : '/.*' > /dev/null; then
    PRG="$link"
  else
    PRG=`dirname "$PRG"`/"$link"
  fi
done

PRGDIR=`dirname "$PRG"`

BASEDIR=`cd "$PRGDIR/.." >/dev/null; pwd`

PIDFILE=${BASEDIR}/pids/instance.pid

## customize zone begin

APP_NAME="sanmu-aiops-demo-client"

jarFile="${APP_NAME}.jar"

## customize zone end

JAVA_OPTS=" -Xms256m -Xmx1024m -XX:+UseConcMarkSweepGC -XX:CMSInitiatingOccupancyFraction=75 -XX:MaxDirectMemorySize=256m"\
" -XX:+UseCMSInitiatingOccupancyOnly -XX:SurvivorRatio=8 -XX:+ExplicitGCInvokesConcurrent -XX:MetaspaceSize=128m"\
" -XX:MaxMetaspaceSize=256m -XX:-OmitStackTraceInFastThrow -XX:+HeapDumpOnOutOfMemoryError"\
" -XX:HeapDumpPath=${BASEDIR}/logs -Djava.io.tmpdir=${BASEDIR}/logs -XX:+PrintGCDetails -XX:+PrintGCDateStamps"\
" -Xloggc:${BASEDIR}/logs/gc-%t.log -XX:+UseGCLogFileRotation -XX:NumberOfGCLogFiles=5 -XX:GCLogFileSize=10m "\

case "$1" in
start)

    if [ -f "$PIDFILE" ]; then
        # kill 0 has a special meaning
        if kill -0 `cat "$PIDFILE"` > /dev/null 2>&1; then
         echo $command already running as process `cat "$PIDFILE"`.
         exit 0
        fi
    fi

    /bin/echo -n $! > "$PIDFILE"

    exec ${JAVA_HOME}/bin/java ${JAVA_OPTS} -XX:OnOutOfMemoryError="kill -9 %p" -jar ${BASEDIR}/lib/${jarFile} \
    --spring.config.location=file:${BASEDIR}/conf/application.properties --logging.config=file:${BASEDIR}/conf/logback.xml

    ;;
stop)

    echo -n "Stopping ${APP_NAME} ... "
        if [ ! -f "${PIDFILE}" ]
        then
          echo "no ${APP_NAME} to stop (could not find file ${PIDFILE})"
        else
          kill -9 $(cat "$PIDFILE") && rm "$PIDFILE"
          echo STOPPED
        fi
        exit 0
    ;;
restart)
    "$0" stop ${@}
    sleep 3
    "$0" start ${@}
    ;;
status)
    pid=$(cat "$PIDFILE")
    found=`ps -ef | awk '{print $2}' | grep -v grep | grep ${pid}`
    if [ x${found} != x ]; then
        echo "${jarFile} is running!"
    else
        echo "${jarFile} not running"
    fi
    ;;
*)
   echo "Usage: $0 {start|stop|status|restart}"
esac

exit 0

