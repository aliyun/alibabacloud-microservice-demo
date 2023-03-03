## build
export REGISTRY=registry.cn-hangzhou.aliyuncs.com/mse-demo-hz/ #对应的registry前缀

At directory `A`/`B`/`C`/`gateway`/`mysql`:

* execute `./build.sh` to build and push image

## install

```shell
kubectl create namespace mse-demo

helm install mse-simple-demo mse-simple-demo \
--set images.version=2.0.1 \
--set mse.application.prefix=demo \
--set registry=registry.cn-hangzhou.aliyuncs.com/mse-demo-hz/ \
--namespace=mse-demo
```

其中可以修改的参数如下：
- `namespace`: 要安装到的k8s命名空间（可选，一般为default） 
- `registry`: 容器镜像地址前缀 
- `images.version`：镜像版本 
- `mse.namespace`: 接入MSE微服务治理的微服务命名空间，可以用来区分环境 
- `autoscaling`：是否开启自动伸缩。 
- `resources.enableRequests`：是否设置resources的requests配置
