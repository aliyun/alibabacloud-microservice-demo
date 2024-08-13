package main

import (
	"github.com/aliyun/alibabacloud-microservice-demo/mse-go-demo/multiframe/C/grpc"
	"github.com/aliyun/alibabacloud-microservice-demo/mse-go-demo/multiframe/C/http"
)

func main() {
	go grpc.RunGrpcServer()
	http.RunHttpServer()
}
