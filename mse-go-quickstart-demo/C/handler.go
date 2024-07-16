package main

import (
	"fmt"
	"github.com/gin-gonic/gin"
	"log"
	"os"
)

type GreetReq struct {
	Name string `json:"name" form:"name" query:"name"`
	Age  int    `json:"age" form:"age" query:"age"`
	Sex  string `json:"sex" form:"sex" query:"sex"`
}

type GreetRespData struct {
	Greeting  string `json:"greeting" form:"greeting" query:"greeting"`
	CallChain string `json:"callChain" form:"callChain" query:"callChain"`
}

func GetGreet(c *gin.Context) {
	log.Printf("[GetGreet] called, headler: %+v, context: %v", c.Request.Header, c.Request.Context())

	var req GreetReq
	if err := c.ShouldBindQuery(&req); err != nil {
		c.JSON(200, gin.H{
			"code":    -1,
			"message": err.Error(),
		})
		return
	}
	log.Printf("[GetGreet] req: %+v", req)

	log.Printf("[GetGreet] mock handling get greet...")

	resp := GreetRespData{
		Greeting: "Hello " + req.Name,
	}
	tag := os.Getenv("MSE_ALICLOUD_SERVICE_TAG")
	if tag == "" {
		tag = "base"
	}
	ip := os.Getenv("KUBERNETES_POD_IP")
	resp.CallChain = fmt.Sprintf("C:%s:%s", tag, ip)
	log.Printf("[GetGreet] resp: %+v", resp)

	c.JSON(200, gin.H{
		"code":    0,
		"message": "success",
		"data":    resp,
	})
}

func PostGreet(c *gin.Context) {
	log.Printf("[PostGreet] called, headler: %+v, context: %v", c.Request.Header, c.Request.Context())

	var req GreetReq
	if err := c.ShouldBindJSON(&req); err != nil {
		c.JSON(200, gin.H{
			"code":    -1,
			"message": err.Error(),
		})
		return
	}
	log.Printf("[PostGreet] req: %+v", req)

	log.Printf("[GetGreet] mock handling post greet...")

	resp := GreetRespData{
		Greeting: "Hello " + req.Name,
	}
	tag := os.Getenv("MSE_ALICLOUD_SERVICE_TAG")
	if tag == "" {
		tag = "base"
	}
	ip := os.Getenv("KUBERNETES_POD_IP")
	resp.CallChain = fmt.Sprintf("C:%s:%s", tag, ip)
	log.Printf("[PostGreet] resp: %+v", resp)

	c.JSON(200, gin.H{
		"code":    0,
		"message": "success",
		"data":    resp,
	})
}
