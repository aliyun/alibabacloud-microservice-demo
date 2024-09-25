// Package main implements a server for Greeter service.
package main

import (
	"context"
	"fmt"
	"google.golang.org/grpc"
	"google.golang.org/grpc/reflection"
	"log"
	"net"
	"os"

	pb "mse-go-integration-test-demo/grpc/D/proto"
)

const (
	port = ":50053"
)

var tag, ip string

// server is used to implement helloworld.GreeterServer.
type server struct {
	pb.UnimplementedCServiceServer
}

func init() {
	tag = os.Getenv("MSE_ALICLOUD_SERVICE_TAG")
	if tag == "" {
		tag = "base"
	}
	ip = os.Getenv("KUBERNETES_POD_IP")
}

func (s *server) CMethod(context.Context, *pb.CRequest) (*pb.CReply, error) {
	content := fmt.Sprintf("D:%s:%s", tag, ip)

	reply := &pb.CReply{
		Message: content,
	}
	return reply, nil
}

func main() {
	lis, err := net.Listen("tcp", port)
	if err != nil {
		log.Fatalf("failed to listen: %v", err)
	}
	s := grpc.NewServer()
	pb.RegisterCServiceServer(s, &server{})
	log.Printf("server listening at %v", lis.Addr())
	// Register reflection service on gRPC server.
	reflection.Register(s)
	if err := s.Serve(lis); err != nil {
		log.Fatalf("failed to serve: %v", err)
	}
}
