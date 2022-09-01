#!/bin/sh
set -e

cd "$(dirname "$0")"

mvn clean package
docker build . -t registry.cn-zhangjiakou.aliyuncs.com/luyanbo-msc/spring-cloud-c:1.1.0-jdk17
