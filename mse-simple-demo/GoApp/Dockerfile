FROM registry-cn-hangzhou.ack.aliyuncs.com/dev/alpine:3.18-update

RUN apk add --no-cache bash

WORKDIR /
COPY GoApp /GoApp
ENTRYPOINT ["/GoApp"]