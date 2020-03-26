

### Introcution

This is a demo project to showcase running microservices on Alibaba Cloud.

This demo is powered by the following projects and products:

* [Apache Dubbo](https://github.com/apache/dubbo) for Remote Procedure Call
* [Spring Cloud Alibaba](https://github.com/alibaba/spring-cloud-alibaba) for Service to Service Call
* [Nacos](https://github.com/alibaba/nacos) for Service Discovery and Configuration management
* Alibaba Cloud [EDAS](https://www.aliyun.com/product/edas) for deployment and hosting
* Alibaba Cloud [ARMS](https://www.aliyun.com/product/arms) for monitoring
* Alibaba Cloud [SAE](https://www.aliyun.com/product/sae) for deployment and hosting without being aware of the underlying infrastrcuture.

### Live Demo

You can visit http://123.56.245.71:8080 for an live demo, which is hosted on Alibaba Cloud [SAE](https://www.aliyun.com/product/sae).

### Architecture

This project contains the following applications (more applications are ong the way):

* frontend: A Java application with SpringMVC and thymeleaf as template engine.
* cartservices: A Java application that  provides basic operations to add products to shopping carts, which is powered by [Apache Dubbo](https://github.com/apache/dubbo).
* productservice: A Java application that provies basic operations to list all the products and query product by ID, which is powered by [Spring Cloud Alibaba](https://github.com/alibaba/spring-cloud-alibaba).

### Build

#### Build docker image

You need to go to the `src` directory, for each sub module, there is a `build.sh`  file, just run it to build the docker image for each module.

```sh
./build.sh
```

### Deploy 

#### Deploy with docker-compose

This project can be deployed to docker with the following command

```sh
docker-compose -f docker-compose.yaml up
```

If you want to undeploy, use the following command

```sh
docker-compose -f docker-compose.yaml down
```

#### Deploy to Kubernetes cluster

This project can be deployed to Kubernetes cluster with the following command:

```sh
cd kubernetes-manifests/
for i in *.yaml; do kubectl apply -f $i; done
```

If you want to delete the deployment, please use the following command:

```sh
for i in *.yaml; do kubectl delete -f $i; done
```

#### Deploy with helm

This project can be deployed to Kubernetes cluster with helm chart:

```sh
helm install ./helm-chart  --name  microservice-demo
```

If you want to delete the deployment with helm, use the following command:

```sh
helm delete microservice-demo
```

### Credit

This project is originiated from [GoogleCloudPlatform/microservice-demo](https://github.com/GoogleCloudPlatform/microservices-demo)
