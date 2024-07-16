package main

import (
	"fmt"
	"math/rand"
	"net/http"
	"os"
	"time"
)

func main() {
	go doHttpRequest()
	select {}
}

func doHttpRequest() {
	defer func() {
		if err := recover(); err != nil {
			fmt.Printf("[doHttpRequest] panic recover: %v\n", err)
			go doHttpRequest()
		}
	}()

	rand.Seed(time.Now().UnixNano())
	time.Sleep(5 * time.Second)

	client := http.Client{
		Timeout: 5 * time.Second,
	}
	for {
		targetDomain := "mse-go-demo-a"
		lbDomain := os.Getenv("MSE_GATEWAY_DOMAIN")
		if lbDomain != "" {
			targetDomain = lbDomain
		}

		req, err := http.NewRequest(http.MethodGet, fmt.Sprintf("http://%s/greet1a?name=coder", targetDomain), nil)
		if err != nil {
			fmt.Printf("[doHttpRequest] new request err: %v\n", err)
			continue
		}

		randN := rand.Intn(100)
		if randN > 50 {
			req.Header.Set("x-mse-tag", "gray")
		}

		resp, err := client.Do(req)
		if err != nil {
			fmt.Printf("[doHttpRequest] do request err: %v\n", err)
			continue
		}
		if resp != nil && resp.StatusCode != http.StatusOK {
			fmt.Printf("[doHttpRequest] do request status code err: %v\n", resp.StatusCode)
			continue
		}

		fmt.Printf("doHttpRequest do request success, resp is %v\n", resp)
		time.Sleep(time.Duration(randN) * time.Millisecond)
	}
}
