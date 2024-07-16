package main

import (
	"github.com/gin-gonic/gin"
	"log"
	"net/http"
)

type GreetReq struct {
	Name string `json:"name" form:"name" query:"name"`
}

type GreetResp struct {
	Code    int           `json:"code" form:"code" query:"code"`
	Message string        `json:"message" form:"message" query:"message"`
	Data    GreetRespData `json:"data" form:"data" query:"data"`
}

type GreetRespData struct {
	Greeting  string `json:"greeting" form:"greeting" query:"greeting"`
	CallChain string `json:"callChain" form:"callChain" query:"callChain"`
}

func GetGreet(c *gin.Context) {
	log.Printf("[GetGreet] called, header: %+v, context: %v", c.Request.Header, c.Request.Context())

	var req GreetReq
	if err := c.ShouldBindQuery(&req); err != nil {
		c.JSON(200, gin.H{
			"code":    -1,
			"message": err.Error(),
		})
		return
	}
	log.Printf("[GetGreet] req: %+v", req)

	resp, err := greet(c, req.Name, http.MethodGet)
	if err != nil {
		log.Printf("[GetGreet] call greet err: %v", err)
		c.JSON(200, gin.H{
			"code":    -2,
			"message": err.Error(),
		})
		return
	}
	log.Printf("[GetGreet] resp: %+v", resp)

	c.JSON(200, gin.H{
		"code":    0,
		"message": "success",
		"data":    resp,
	})
}

func PostGreet(c *gin.Context) {
	log.Printf("[PostGreet] called, header: %+v, context: %v", c.Request.Header, c.Request.Context())

	var req GreetReq
	if err := c.ShouldBindJSON(&req); err != nil {
		c.JSON(200, gin.H{
			"code":    -1,
			"message": err.Error(),
		})
		return
	}
	log.Printf("[PostGreet] req: %+v", req)

	resp, err := greet(c, req.Name, http.MethodPost)
	if err != nil {
		log.Printf("[PostGreet] call greet err: %v", err)
		c.JSON(200, gin.H{
			"code":    -2,
			"message": err.Error(),
		})
		return
	}
	log.Printf("[PostGreet] resp: %+v", resp)

	c.JSON(200, gin.H{
		"code":    0,
		"message": "success",
		"data":    resp,
	})
}
