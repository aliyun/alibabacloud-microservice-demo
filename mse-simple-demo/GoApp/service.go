package main

import (
	"context"
	"errors"
	"fmt"
	"io"
	"net"
	"net/http"
	"os"
	"sync"
	"time"
)

const localhost = "127.0.0.1"

var httpClient *http.Client
var tag string
var ip string

func init() {
	httpClient = &http.Client{
		Timeout:   time.Second * 3,
		Transport: http.DefaultTransport,
	}
	tag = os.Getenv("MSE_ALICLOUD_SERVICE_TAG")
	ip = getIp()
}

func getResponseBody(resp *http.Response) ([]byte, error) {
	if resp == nil || resp.StatusCode != http.StatusOK {
		return nil, errors.New(fmt.Sprintf("http request status code not 200: %d", resp.StatusCode))
	}
	defer func() {
		if resp.Body != nil {
			resp.Body.Close()
		}
	}()

	b, err := io.ReadAll(resp.Body)
	if err != nil {
		return nil, err
	}

	return b, nil
}

func getIp() string {
	addrs, err := net.InterfaceAddrs()
	if err != nil {
		return localhost
	}

	for _, addr := range addrs {
		// 检查网络地址是否是IP地址（而非IP网络）
		if ipNet, ok := addr.(*net.IPNet); ok && !ipNet.IP.IsLoopback() {
			// 仅保留IPv4地址
			if ipNet.IP.To4() != nil {
				return ipNet.IP.String()
			}
		}
	}
	return localhost
}

func toApp(ctx context.Context, host string, path string) (string, error) {
	req, err := http.NewRequestWithContext(ctx, http.MethodGet, fmt.Sprintf("http://%s%s", host, path), nil)
	if err != nil {
		fmt.Printf("[toA] new http request with context error: %v\n", err)
		return "", err
	}

	resp, err := httpClient.Do(req)
	if err != nil {
		fmt.Printf("[toA] http request error: %v\n", err)
		return "", err
	}

	b, err := getResponseBody(resp)
	if err != nil {
		fmt.Printf("[toA] get response body error: %v\n", err)
		return "", err
	}

	return fmt.Sprintf("GoApp[tag=%s][%s][config=%s]", tag, ip, "") + " -> " + string(b), nil
}

func toA(ctx context.Context) (string, error) {
	return toApp(ctx, "spring-cloud-a:20001", "/a")
}

func toABC(ctx context.Context) (string, error) {
	w := sync.WaitGroup{}
	w.Add(3)
	var aResp, bResp, cResp string
	var aErr, bErr, cErr error
	go func() {
		aResp, aErr = toApp(ctx, "spring-cloud-a:20001", "/a")
		if aErr != nil {
			fmt.Printf("[toABC] to a error: %v\n", aErr)
			aResp = aErr.Error()
		}
		w.Done()
	}()
	go func() {
		bResp, bErr = toApp(ctx, "spring-cloud-b:20002", "/b")
		if bErr != nil {
			fmt.Printf("[toABC] to b error: %v\n", bErr)
			bResp = bErr.Error()
		}
		w.Done()
	}()
	go func() {
		cResp, cErr = toApp(ctx, "spring-cloud-c:20003", "/c")
		if cErr != nil {
			fmt.Printf("[toABC] to c error: %v\n", cErr)
			cResp = cErr.Error()
		}
		w.Done()
	}()
	w.Wait()

	return aResp + "\n" + bResp + "\n" + cResp, nil
}

func flow(ctx context.Context) (string, error) {
	return "", nil
}

func paramHot(ctx context.Context) (string, error) {
	return "", nil
}

func isolate(ctx context.Context) (string, error) {
	return "", nil
}
