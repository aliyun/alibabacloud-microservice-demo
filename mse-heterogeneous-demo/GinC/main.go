package main

import (
	"github.com/gin-gonic/gin"
)

func main() {
	r := gin.Default()
	registerEngine(r)
	if err := r.Run(":20003"); err != nil { // listen and serve on 0.0.0.0:8080
		panic(err)
	}
}

func registerEngine(r *gin.Engine) {
	r.GET("/C/c", greet)
}
