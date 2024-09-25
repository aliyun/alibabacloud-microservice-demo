cd A
#CGO_ENABLED=0 GOOS=linux GOARCH=amd64 ../aliyun-go-agent -mse
#CGO_ENABLED=0 GOOS=linux GOARCH=amd64 ../instgo build --mse --licenseKey=hkhon1po62@e68942904df39f9
docker build -t gin-server-a:1.0-multiframe .
docker tag gin-server-a:1.0-multiframe registry.cn-hangzhou.aliyuncs.com/mse-governance-demo/gin-server-a:1.0-multiframe
docker push registry.cn-hangzhou.aliyuncs.com/mse-governance-demo/gin-server-a:1.0-multiframe
cd ..

cd B
#CGO_ENABLED=0 GOOS=linux GOARCH=amd64 ../aliyun-go-agent -mse
docker build -t gin-server-b:1.0-multiframe .
docker tag gin-server-b:1.0-multiframe registry.cn-hangzhou.aliyuncs.com/mse-governance-demo/go-kratos-demo-b:1.0-multiframe
docker push registry.cn-hangzhou.aliyuncs.com/mse-governance-demo/go-kratos-demo-b:1.0-multiframe
cd ..

cd C
#CGO_ENABLED=0 GOOS=linux GOARCH=amd64 ../aliyun-go-agent -mse
docker build -t gin-server-c:1.0-multiframe .
docker tag gin-server-c:1.0-multiframe registry.cn-hangzhou.aliyuncs.com/mse-governance-demo/go-zero-demo-c:1.0-multiframe
docker push registry.cn-hangzhou.aliyuncs.com/mse-governance-demo/go-zero-demo-c:1.0-multiframe
cd ..

cd D
#CGO_ENABLED=0 GOOS=linux GOARCH=amd64 ../aliyun-go-agent -mse
docker build -t go-grpc-demo-d:1.0-multiframe .
docker tag go-grpc-demo-d:1.0-multiframe registry.cn-hangzhou.aliyuncs.com/mse-governance-demo/go-grpc-demo-d:1.0-multiframe
docker push registry.cn-hangzhou.aliyuncs.com/mse-governance-demo/go-grpc-demo-d:1.0-multiframe
cd ..

kubectl create namespace multiframe
kubectl apply -f deployment.yaml -n multiframe