FROM registry-cn-hangzhou.ack.aliyuncs.com/dev/alpine:3.18-update@sha256:f2f97bbc6f3173044292cbf8d306ca31b4356a5f2eb54701fc97f64065b412cf

RUN apk add --no-cache bash

WORKDIR /
COPY A /A
ENTRYPOINT ["/A"]