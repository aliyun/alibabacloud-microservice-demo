package main

import "github.com/gin-gonic/gin"

func main() {
	r := gin.Default()

	gin.New()

	registerEngine(r)
	if err := r.Run(); err != nil {
		panic(err)
	}
}

func registerEngine(r *gin.Engine) {
	g := r.Group("/GoApp")

	g.GET("/toA", toAHandler)
	g.GET("/toABC", toABCHandler)
	g.GET("/flow", flowHandler)
	g.GET("/param/:hot", paramHotHandler)
	g.GET("/isolate", isolateHandler)
}
