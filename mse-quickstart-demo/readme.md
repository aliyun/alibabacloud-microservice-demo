## Readme

#### 部署

通过 consumer 下的 consumer-deployment.yaml 和 consumer-gray-deployment.yaml 这两个文件部署 consumer 的 base 版本和 gray 版本


通过 provider 下的 provider-deployment.yaml 和 provider-gray-deployment.yaml 这两个文件部署 provider 的 base 版本和 gray 版本


可以通过修改 env 里的  dubbo.registry.address、spring.cloud.nacos.discovery.server-addr、 spring.cloud.nacos.config.server-addr 来修改对应 nacos 的地址

#### 调用

可以通过云原生网关来暴露服务，其中 consumer 提供了这几个URL

- /a， 通过 Spring Cloud 去调用 Provider，并分别返回 Consumer 和 Provider 的地址

- /dubbo， 通过 Dubbo 去调用 Provider，并分别返回 Consumer 和 Provider 的地址

#### 配置

Consumer 会不断地输出日志，打印内存里面 name  的值。 name 的值可以通过修改 Nacos Config 来修改

其中 dataId 为 consumer.properties， content 为 `name=helloworld`