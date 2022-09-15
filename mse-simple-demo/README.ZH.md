# mse-simple-demo

mse-simple-demo是一个简单的微服务demo，架构如下：

![demo架构](./image/arch.png)

# 如何部署

```sh
helm3 upgrade mse-simple-demo1 \
  helm/mse-simple-demo \
  --install \
  --set registry=registry.cn-shanghai.aliyuncs.com/yizhan/ \
  --set mse.namePrefix=mse-test- \
  --set nacos.host=mse-xxxxx-p.nacos-ans.mse.aliyuncs.com \
  --set nacos.namespace=public
```

* `registry`: 容器镜像地址前缀
* `mse.namePrefix`: 接入mse的应用名前缀，可以用来区分环境
* `nacos.host`: 应用要注册到的nacos地址
* `nacos.namespace`: nacos命名空间
