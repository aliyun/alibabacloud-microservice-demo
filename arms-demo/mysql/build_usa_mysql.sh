#!/bin/bash -e
if [ -z "$1" ]
then
  echo "tag is empty,use default one"
  TAG=helm-v2
else
  TAG=$1
fi
echo "TAG: $TAG"
# aliwaredemo账号
DOCKER_IMAGE=arms-demo-registry.us-east-1.cr.aliyuncs.com/arms-demo/demo-mysql
echo ${DOCKER_IMAGE}
docker build --platform linux/amd64 -t ${DOCKER_IMAGE}:${TAG} --file ./Dockerfile .
docker push ${DOCKER_IMAGE}:${TAG}