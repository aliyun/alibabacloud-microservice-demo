## build
export REGISTRY=registry.cn-hangzhou.aliyuncs.com/mse-demo-hz/ #对应的registry前缀

At directory `A`/`B`/`C`/`ZuulGateway`/`SpringCloudGateway`/`mysql`:

* execute `./build.sh` to build and push image

## install

```shell
helm upgrade mse-simple-demo helm/mse-simple-demo \
--namespace mse-demo --create-namespace \
--install \
--values helm/mse-simple-demo/values.yaml
```

其中可以修改的参数如下：
- `namespace`: 要安装到的k8s命名空间（可选，默认值为mse-demo） 
- `registry`: 容器镜像地址前缀 
- `images.version`：镜像版本 
- `mse.namespace`: 接入MSE微服务治理的微服务命名空间，可以用来区分环境（默认值为mse-demo）
- `autoscaling`：是否开启自动伸缩（默认值为false）
- `resources.enableRequests`：是否设置resources的requests配置（默认值为true）
- `gateway.springcloud`：是否开启springcloud网关