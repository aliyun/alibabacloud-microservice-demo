package svc

import "github.com/aliyun/alibabacloud-microservice-demo/mse-go-demo/multiframe/C/grpc/internal/config"

type ServiceContext struct {
	Config config.Config
}

func NewServiceContext(c config.Config) *ServiceContext {
	return &ServiceContext{
		Config: c,
	}
}
