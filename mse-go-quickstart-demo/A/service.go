package main

import (
	"bytes"
	"context"
	"encoding/json"
	"fmt"
	"net/http"
	"os"
	"time"
)

func greet(ctx context.Context, name, httpType string) (*GreetRespData, error) {
	if httpType == http.MethodGet {
		return getGreet(ctx, name, "mse-go-demo-b")
	}
	return postGreet(ctx, name, "mse-go-demo-b")
}

func getGreet(ctx context.Context, name, disService string) (*GreetRespData, error) {
	req, err := http.NewRequestWithContext(ctx, http.MethodGet, fmt.Sprintf("http://%v/greet1b?name=%v&age=%v&sex=%v", disService, name, 20, "male"), nil)
	if err != nil {
		return nil, err
	}

	client := &http.Client{Timeout: 5 * time.Second}
	resp, err := client.Do(req)
	if err != nil {
		return nil, err
	}
	fmt.Println("[getGreet] resp ", resp)
	if resp.StatusCode != http.StatusOK {
		return nil, fmt.Errorf("get greet failed, status code: %d", resp.StatusCode)
	}
	var respData GreetResp
	defer resp.Body.Close()
	if err := json.NewDecoder(resp.Body).Decode(&respData); err != nil {
		return nil, err
	}

	tag := os.Getenv("MSE_ALICLOUD_SERVICE_TAG")
	if tag == "" {
		tag = "base"
	}
	ip := os.Getenv("KUBERNETES_POD_IP")
	respData.Data.CallChain = fmt.Sprintf("A:%s:%s", tag, ip) + " - " + respData.Data.CallChain

	return &respData.Data, nil
}

func postGreet(ctx context.Context, name, disService string) (*GreetRespData, error) {
	data := map[string]interface{}{
		"name": name,
		"age":  20,
		"sex":  "male",
	}
	jsonData, err := json.Marshal(data)
	if err != nil {
		return nil, err
	}

	req, err := http.NewRequestWithContext(ctx, http.MethodPost, fmt.Sprintf("http://%v/greet2b", disService), bytes.NewBuffer(jsonData))
	if err != nil {
		return nil, err
	}

	client := &http.Client{Timeout: 5 * time.Second}
	resp, err := client.Do(req)
	if err != nil {
		return nil, err
	}
	if resp.StatusCode != http.StatusOK {
		return nil, fmt.Errorf("post greet failed, status code: %d", resp.StatusCode)
	}
	var respData GreetResp
	defer resp.Body.Close()
	if err := json.NewDecoder(resp.Body).Decode(&respData); err != nil {
		return nil, err
	}

	tag := os.Getenv("MSE_ALICLOUD_SERVICE_TAG")
	if tag == "" {
		tag = "base"
	}
	ip := os.Getenv("KUBERNETES_POD_IP")
	respData.Data.CallChain = fmt.Sprintf("A:%s:%s", tag, ip) + " - " + respData.Data.CallChain
	return &respData.Data, nil
}
