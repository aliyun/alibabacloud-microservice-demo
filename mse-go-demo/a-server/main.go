// Package main implements a server for Greeter service.
package main

import (
	"context"
	"log"
	"net/http"

	"google.golang.org/grpc"

	b_api_pb "github.com/aliyun/alibabacloud-microservice-demo/mse-go-demo/a-server/proto/b_api"
)

const (
	port = ":8080"

	address = "go-b-service:50051"
)

var bClient b_api_pb.BServiceClient

func handler(w http.ResponseWriter, r *http.Request) {
	ctx := context.Background()
	bReply, err := bClient.BMethod(ctx, &b_api_pb.BRequest{})
	if err != nil {
		w.WriteHeader(500)
		return
	}
	content := "A->" + bReply.GetMessage()
	w.Write([]byte(content))
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
	bClient = b_api_pb.NewBServiceClient(conn)

	http.HandleFunc("/", handler)
	log.Fatal(http.ListenAndServe(port, nil))
}
