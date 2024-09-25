package logic

import (
	"context"
	"fmt"
	"github.com/aliyun/alibabacloud-microservice-demo/mse-go-demo/multiframe/C/http/d_api"
	"google.golang.org/grpc"
)

const (
	address = "go-grpc-demo-d:50053"
)

var dClient d_api.CServiceClient

func GreetD(ctx context.Context, name string) (string, error) {
	if dClient == nil { // lazy init
		conn, err := grpc.DialContext(
			ctx,
			address,
			grpc.WithInsecure(),
		)
		if err != nil {
			fmt.Printf("[GreetD] dail grpc server d error: %v\n", err)
			return "", err
		}

		dClient = d_api.NewCServiceClient(conn)
	}

	dReply, err := dClient.CMethod(ctx, &d_api.CRequest{Name: name})
	if err != nil {
		fmt.Printf("[GreetD] call d error: %v\n", err)
		return "", err
	}

	return dReply.GetMessage(), nil
}
