FROM golang:1.19-alpine3.16 as build

RUN sed -i 's/dl-cdn.alpinelinux.org/mirrors.tuna.tsinghua.edu.cn/g' /etc/apk/repositories

RUN apk --update add gcc libc-dev

ADD . /workspace

WORKDIR /workspace

RUN --mount=type=cache,target=/go \
  env GOPROXY=https://goproxy.cn,direct \
  go build -buildmode=pie -ldflags "-linkmode external -extldflags -static -w" -o /workspace

# The RUN line below is an alternative that also results in a static binary
# that can be run FROM scratch but results in a slightly-larger executable
# without ASLR.
#
# RUN CGO_ENABLED=0 go build -a -o arms-pilot

FROM alpine:3.16

RUN sed -i 's/dl-cdn.alpinelinux.org/mirrors.tuna.tsinghua.edu.cn/g' /etc/apk/repositories

COPY --from=build /workspace/c-server /c-server

EXPOSE 50051

CMD ["/c-server"]
