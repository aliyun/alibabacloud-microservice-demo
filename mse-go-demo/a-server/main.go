// Package main implements a server for Greeter service.
package main

import (
	"context"
	"fmt"
	"io/ioutil"
	"log"
	"net/http"
	"os"
	"regexp"
	"time"

	b_api_pb "github.com/aliyun/alibabacloud-microservice-demo/mse-go-demo/a-server/proto/b_api"
	"google.golang.org/grpc"
)

const (
	port = ":8080"

	address = "go-b-service:50051"
)

func handler(w http.ResponseWriter, r *http.Request) {
	fmt.Printf("requesting: %v\n", r.URL)
	ctx := context.Background()
	ctx, cancel := context.WithTimeout(ctx, 5*time.Second)
	defer cancel()

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
	bClient := b_api_pb.NewBServiceClient(conn)

	bReply, err := bClient.BMethod(ctx, &b_api_pb.BRequest{})
	if err != nil {
		fmt.Printf("call b-service failed! %v\n", err)
		log.Printf("call b-service failed! %v", err)
		w.WriteHeader(500)
		fmt.Fprint(w, err.Error())
		return
	}
	content := generateMessage("A") + "->" + bReply.GetMessage()
	fmt.Fprintln(w, content)
}

func main() {
	fmt.Println("starting")
	http.HandleFunc("/", handler)

	fmt.Println("listening")
	log.Fatal(http.ListenAndServe(port, nil))
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
