package grpc

import (
	"fmt"

	"github.com/aliyun/alibabacloud-microservice-demo/mse-go-demo/multiframe/C/grpc/internal/config"
	"github.com/aliyun/alibabacloud-microservice-demo/mse-go-demo/multiframe/C/grpc/internal/server"
	"github.com/aliyun/alibabacloud-microservice-demo/mse-go-demo/multiframe/C/grpc/internal/svc"
	"github.com/aliyun/alibabacloud-microservice-demo/mse-go-demo/multiframe/C/grpc/proto"
	"github.com/zeromicro/go-zero/core/conf"
	"github.com/zeromicro/go-zero/core/service"
	"github.com/zeromicro/go-zero/zrpc"
	"google.golang.org/grpc"
	"google.golang.org/grpc/reflection"
)

func RunGrpcServer() {
	var c config.Config
	conf.MustLoad("grpc/etc/demo.yaml", &c)
	ctx := svc.NewServiceContext(c)

	s := zrpc.MustNewServer(c.RpcServerConf, func(grpcServer *grpc.Server) {
		__.RegisterCGreeterServer(grpcServer, server.NewCGreeterServer(ctx))

		if c.Mode == service.DevMode || c.Mode == service.TestMode {
			reflection.Register(grpcServer)
		}
	})
	defer s.Stop()

	fmt.Printf("Starting rpc server at %s...\n", c.ListenOn)
	s.Start()
}
