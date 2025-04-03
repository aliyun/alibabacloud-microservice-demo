package main

import (
	"context"
	"fmt"
	"github.com/gin-gonic/gin"
	"io"
	"net"
	"net/http"
	"os"
	"time"
)

var client *http.Client
var tag string
var ip string

func init() {
	tag = os.Getenv("MSE_ALICLOUD_SERVICE_TAG")
	if tag == "" {
		tag = "base"
	}
	ip = getLocalIp()
	client = &http.Client{Timeout: 5 * time.Second}
}

func getLocalIp() string {
	addrs, err := net.InterfaceAddrs()
	if err != nil {
		return ""
	}
	for _, address := range addrs {
		if ipnet, ok := address.(*net.IPNet); ok && !ipnet.IP.IsLoopback() {
			if ipnet.IP.To4() != nil {
				return ipnet.IP.String()
			}
		}
	}
	return ""
}

func greet(c *gin.Context) {
	fmt.Printf("[GetGreet] called, header: %+v, context: %v\n", c.Request.Header, c.Request.Context())
	resp, err := doGreet(c)
	if err != nil {
		fmt.Printf("[GetGreet] doGreet failed, err: %v\n", err)
		c.String(http.StatusInternalServerError, err.Error())
		return
	}
	c.String(http.StatusOK, resp)
}

func doGreet(ctx context.Context) (string, error) {
	req, err := http.NewRequestWithContext(ctx, http.MethodGet, fmt.Sprintf("http://spring-cloud-d:20004/D/d"), nil)
	if err != nil {
		return "", err
	}

	resp, err := client.Do(req)
	fmt.Printf("[getGreet] resp: %v\n", resp)
	if err != nil {
		return "", err
	}
	if resp.StatusCode != http.StatusOK {
		return "", fmt.Errorf("get greet failed, status code: %d", resp.StatusCode)
	}
	defer resp.Body.Close()

	b, err := io.ReadAll(resp.Body)
	if err != nil {
		fmt.Printf("[getGreet] read resp body failed, err: %v\n", err)
		return "", err
	}
	respData := string(b)
	respData = fmt.Sprintf("C:%s:%s", ip, tag) + " - " + respData
	fmt.Printf("[getGreet] respData: %v\n", respData)
	return respData, nil
}
