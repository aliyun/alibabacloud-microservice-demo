package main

import (
	"context"
	"fmt"
	"log"
	"os"
	"time"

	"github.com/aliyun/alibabacloud-microservice-demo/mse-go-demo/multiframe/B/proto/b_api"
	"github.com/aliyun/alibabacloud-microservice-demo/mse-go-demo/multiframe/B/proto/c_api"
	"github.com/go-kratos/kratos/v2"
	"github.com/go-kratos/kratos/v2/middleware/recovery"
	"github.com/go-kratos/kratos/v2/transport/grpc"
	"github.com/go-kratos/kratos/v2/transport/http"
)

var KratosServiceCHttpEndpoint = "go-zero-demo-c"
var KratosServiceCGrpcEndpoint = "go-zero-demo-c:8892"

type server struct {
	b_api.UnimplementedBGreeterServer
}

func (s *server) BSayHello(ctx context.Context, in *b_api.BHelloRequest) (*b_api.BHelloReply, error) {
	fmt.Printf("[BSayHello] service c receive say hello request : %v\n", in)
	var cReply *c_api.CHelloReply
	reply := &b_api.BHelloReply{}
	// grpc方式调用
	if in.CallType == "grpc" {
		conn, err := grpc.DialInsecure(
			context.Background(),
			grpc.WithEndpoint(KratosServiceCGrpcEndpoint),
			grpc.WithMiddleware(
				recovery.Recovery(),
			),
			grpc.WithTimeout(time.Second*2),
		)
		defer func() {
			if conn != nil {
				conn.Close()
			}
		}()
		if err != nil {
			fmt.Printf("[BSayHello] grpc: new client err: %v\n", err)
			return nil, err
		}

		client := c_api.NewCGreeterClient(conn)
		cReply, err = client.CSayHello(ctx, &c_api.CHelloRequest{Name: in.GetName(), CallType: in.GetCallType()})
		if err != nil {
			fmt.Printf("[BSayHello] grpc: say hello to service c err: %v\n", err)
			return nil, err
		}
		fmt.Printf("[BSayHello] grpc: say hello to service c successfully, reply is %v\n", cReply)
	} else {
		httpConn, err := http.NewClient(
			context.Background(),
			http.WithEndpoint(KratosServiceCHttpEndpoint),
			http.WithTimeout(time.Second*2),
		)
		defer func() {
			if httpConn != nil {
				httpConn.Close()
			}
		}()
		if err != nil {
			fmt.Printf("[BSayHello] http: new client err: %v\n", err)
			return nil, err
		}

		httpClient := c_api.NewCGreeterHTTPClient(httpConn)
		cReply, err = httpClient.CSayHello(ctx, &c_api.CHelloRequest{Name: in.GetName(), CallType: in.GetCallType()})
		if err != nil {
			fmt.Printf("[BSayHello] http: say hello to service c err: %v\n", err)
			return nil, err
		}
		fmt.Printf("[BSayHello] http: say hello to service c successfully, reply is %v\n", cReply)
	}

	reply.Message = cReply.GetMessage()
	tag := os.Getenv("MSE_ALICLOUD_SERVICE_TAG")
	if tag == "" {
		tag = "base"
	}
	ip := os.Getenv("KUBERNETES_POD_IP")
	callType := "http"
	if in.CallType == "grpc" {
		callType = "grpc"
	}
	reply.CallChain = fmt.Sprintf("B:%s:%s", tag, ip) + fmt.Sprintf(" -(%s)- ", callType) + cReply.GetCallChain()
	return reply, nil
}

func main() {
	httpSrv := http.NewServer(
		http.Address(":8001"),
	)
	grpcSrv := grpc.NewServer(
		grpc.Address(":9001"),
	)

	s := &server{}
	b_api.RegisterBGreeterServer(grpcSrv, s)
	b_api.RegisterBGreeterHTTPServer(httpSrv, s)
	app := kratos.New(
		kratos.Name("go-kratos-demo-b"),
		kratos.Server(
			httpSrv,
			grpcSrv,
		),
	)
	if err := app.Run(); err != nil {
		log.Fatal(err)
	}
}
