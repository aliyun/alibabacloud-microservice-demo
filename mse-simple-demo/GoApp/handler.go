package main

import "github.com/gin-gonic/gin"

func toAHandler(c *gin.Context) {
	resp, err := toA(c)
	if err != nil {
		c.String(500, err.Error())
		return
	}

	c.String(200, resp)
}

func toABCHandler(c *gin.Context) {
	resp, err := toABC(c)
	if err != nil {
		c.String(500, err.Error())
		return
	}

	c.String(200, resp)
}

func flowHandler(c *gin.Context) {
	c.String(200, "flow")
}

func paramHotHandler(c *gin.Context) {
	c.String(200, "paramHot")
}

func isolateHandler(c *gin.Context) {
	c.String(200, "isolate")
}
