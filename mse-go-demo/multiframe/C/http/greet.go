package http

import (
	"fmt"

	"github.com/aliyun/alibabacloud-microservice-demo/mse-go-demo/multiframe/C/http/internal/config"
	"github.com/aliyun/alibabacloud-microservice-demo/mse-go-demo/multiframe/C/http/internal/handler"
	"github.com/aliyun/alibabacloud-microservice-demo/mse-go-demo/multiframe/C/http/internal/svc"
	"github.com/zeromicro/go-zero/core/conf"
	"github.com/zeromicro/go-zero/rest"
)

func RunHttpServer() {
	var c config.Config
	conf.MustLoad("http/etc/greet.yaml", &c)

	server := rest.MustNewServer(c.RestConf)
	defer server.Stop()

	ctx := svc.NewServiceContext(c)
	handler.RegisterHandlers(server, ctx)

	fmt.Printf("Starting server at %s:%d...\n", c.Host, c.Port)
	server.Start()
}
