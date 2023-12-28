# REPLACE it with your kube config
KUBECONFIG=/Users/allenli/kube/config

pod-list:
	kubectl get pod -n arms-apm-demo --kubeconfig=$(KUBECONFIG) -o wide	| grep mysql

pod-bash:
	kubectl exec -it --kubeconfig=/Users/allenli/kube/config -n arms-apm-demo "$(kubectl get pod -n arms-apm-demo --kubeconfig=config -o wide | grep mysql | awk '{ print $1 }')" bash

docker-build:
	sh build.sh && \
	docker tag mysql:v1 sy-demo-registry.cn-hangzhou.cr.aliyuncs.com/insights/demo-mysql:v1 && \
	docker push sy-demo-registry.cn-hangzhou.cr.aliyuncs.com/insights/demo-mysql:v1