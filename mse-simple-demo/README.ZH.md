# mse-simple-demo

mse-simple-demo是一个简单的微服务demo，架构如下：

![demo架构](./image/arch.png)

# 如何部署

```sh
helm upgrade mse-simple-demo1 \
  helm/mse-simple-demo \
  --install \
  --namespace default --create-namespace \
  --values ./helm/mse-simple-demo/values.example.yaml
```

* `namespace`: 要安装到的k8s命名空间
* `registry`: 容器镜像地址前缀（可选）
* `mse.namespace`: 接入MSE微服务治理的微服务命名空间，可以用来区分环境
