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

	pb "github.com/aliyun/alibabacloud-microservice-demo/mse-go-demo/grpc/c-server/proto"
)

const (
	port = ":50053"
)

// server is used to implement helloworld.GreeterServer.
type server struct {
	pb.UnimplementedCServiceServer
}

func (s *server) CMethod(context.Context, *pb.CRequest) (*pb.CReply, error) {
	reply := &pb.CReply{
		Message: generateMessage("C"),
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
