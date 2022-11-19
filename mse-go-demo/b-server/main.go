// Package main implements a server for Greeter service.
package main

import (
	"context"
	"log"
	"net"

	"google.golang.org/grpc"
	"google.golang.org/grpc/reflection"

	b_api_pb "github.com/aliyun/alibabacloud-microservice-demo/mse-go-demo/b-server/proto/b_api"
	c_api_pb "github.com/aliyun/alibabacloud-microservice-demo/mse-go-demo/b-server/proto/c_api"
)

const (
	port    = ":50051"
	address = "go-c-service:50051"
)

// server is used to implement helloworld.GreeterServer.
type server struct {
	b_api_pb.UnimplementedBServiceServer

	cClient c_api_pb.CServiceClient
}

func (s *server) BMethod(ctx context.Context, req *b_api_pb.BRequest) (*b_api_pb.BReply, error) {
	cReply, err := s.cClient.CMethod(ctx, &c_api_pb.CRequest{})
	if err != nil {
		return nil, err
	}
	reply := &b_api_pb.BReply{
		Message: cReply.GetMessage() + "-> B",
	}
	return reply, nil
}

func main() {
	conn, err := grpc.Dial(
		address,
		grpc.WithInsecure(),
		grpc.WithBlock(),
	)
	if err != nil {
		log.Fatalf("did not connect: %v", err)
	}
	defer conn.Close()
	cClient := c_api_pb.NewCServiceClient(conn)

	lis, err := net.Listen("tcp", port)
	if err != nil {
		log.Fatalf("failed to listen: %v", err)
	}
	s := grpc.NewServer()
	b_api_pb.RegisterBServiceServer(s, &server{cClient: cClient})
	log.Printf("server listening at %v", lis.Addr())
	// Register reflection service on gRPC server.
	reflection.Register(s)
	if err := s.Serve(lis); err != nil {
		log.Fatalf("failed to serve: %v", err)
	}
}
