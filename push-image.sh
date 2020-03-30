#!/bin/zsh
#docker login --username=dingyue_cloudnative registry.cn-beijing.aliyuncs.com
imageName=$1
version=$2
repo_namespace=microservice-demo
mcp
docker build -t $imageName:$version ./
ImageId=`docker build -t $imageName:v1 ./ |grep "Successfully built"|awk -F" " '{print $3}'`
docker tag $ImageId registry.cn-beijing.aliyuncs.com/$repo_namespace/$imageName:$version
docker push registry.cn-beijing.aliyuncs.com/$repo_namespace/$imageName:$version

date
