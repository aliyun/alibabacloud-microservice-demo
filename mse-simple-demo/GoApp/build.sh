CGO_ENABLED=0 GOOS=linux GOARCH=amd64 ./aliyun-go-agent -mse
docker build -t gin-server-a:3.0.8 .
docker tag gin-server-a:3.0.8 registry.cn-hangzhou.aliyuncs.com/mse-governance-demo/gin-server-a:3.0.8
docker push registry.cn-hangzhou.aliyuncs.com/mse-governance-demo/gin-server-a:3.0.8