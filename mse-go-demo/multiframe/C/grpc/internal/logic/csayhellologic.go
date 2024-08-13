package logic

import (
	"context"
	"fmt"
	"os"

	"github.com/aliyun/alibabacloud-microservice-demo/mse-go-demo/multiframe/C/grpc/internal/svc"
	"github.com/aliyun/alibabacloud-microservice-demo/mse-go-demo/multiframe/C/grpc/proto"

	"github.com/zeromicro/go-zero/core/logx"
)

type CSayHelloLogic struct {
	ctx    context.Context
	svcCtx *svc.ServiceContext
	logx.Logger
}

func NewCSayHelloLogic(ctx context.Context, svcCtx *svc.ServiceContext) *CSayHelloLogic {
	return &CSayHelloLogic{
		ctx:    ctx,
		svcCtx: svcCtx,
		Logger: logx.WithContext(ctx),
	}
}

// 定义一个 SayHello 一元 rpc 方法，请求体和响应体必填。
func (l *CSayHelloLogic) CSayHello(in *__.CSayHelloReq) (*__.CSayHelloResp, error) {
	fmt.Printf("[CSayHelloLogic.CSayHello] receive request: %v\n", in)

	resp := &__.CSayHelloResp{
		Message: "hello " + in.Name,
	}

	tag := os.Getenv("MSE_ALICLOUD_SERVICE_TAG")
	if tag == "" {
		tag = "base"
	}
	ip := os.Getenv("KUBERNETES_POD_IP")
	resp.CallChain = fmt.Sprintf("C:%s:%s", tag, ip)
	return resp, nil
}
