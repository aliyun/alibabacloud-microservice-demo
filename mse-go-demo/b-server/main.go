// Package main implements a server for Greeter service.
package main

import (
	"context"
	"io/ioutil"
	"log"
	"net"
	"os"
	"regexp"

	"google.golang.org/grpc"
	"google.golang.org/grpc/reflection"

	b_api_pb "github.com/aliyun/alibabacloud-microservice-demo/mse-go-demo/b-server/proto/b_api"
	c_api_pb "github.com/aliyun/alibabacloud-microservice-demo/mse-go-demo/b-server/proto/c_api"
)

const (
	port    = ":50052"
	address = "go-c-service:50053"
)

// server is used to implement helloworld.GreeterServer.
type server struct {
	b_api_pb.UnimplementedBServiceServer
}

func (s *server) BMethod(ctx context.Context, req *b_api_pb.BRequest) (*b_api_pb.BReply, error) {
	conn, err := grpc.DialContext(
		ctx,
		address,
		grpc.WithInsecure(),
		grpc.WithBlock(),
	)
	if err != nil {
		log.Fatalf("did not connect: %v", err)
	}
	defer conn.Close()
	cClient := c_api_pb.NewCServiceClient(conn)

	cReply, err := cClient.CMethod(ctx, &c_api_pb.CRequest{})
	if err != nil {
		return nil, err
	}
	reply := &b_api_pb.BReply{
		Message: generateMessage("B") + "->" + cReply.GetMessage(),
	}
	return reply, nil
}

func main() {
	lis, err := net.Listen("tcp", port)
	if err != nil {
		log.Fatalf("failed to listen: %v", err)
	}
	s := grpc.NewServer()
	b_api_pb.RegisterBServiceServer(s, &server{})
	log.Printf("server listening at %v", lis.Addr())
	// Register reflection service on gRPC server.
	reflection.Register(s)
	if err := s.Serve(lis); err != nil {
		log.Fatalf("failed to serve: %v", err)
	}
}

func generateMessage(message string) string {
	tag := parseTag()
	if tag != "" {
		message += "-" + tag
	}
	hostname, err := os.Hostname()
	if err == nil {
		message += "[" + hostname + "]"
	}
	return message
}

func parseTag() string {
	var re = regexp.MustCompile(`(?m)alicloud\.service\.tag="(?P<tag>.*)"`)
	bs, err := ioutil.ReadFile("/etc/podinfo/labels")
	if err != nil {
		return ""
	}
	content := string(bs)
	result := re.FindStringSubmatch(content)

	groupNames := re.SubexpNames()
	index := 0
	for i, name := range groupNames {
		if name == "tag" {
			index = i
			break
		}
	}
	if len(result) <= index {
		return ""
	}
	return result[index]
}
