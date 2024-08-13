REGISTRY ?=
IMAGE_NAME ?= c-server
IMAGE_TAG ?= 1.0.0
IMG ?= ${REGISTRY}${IMAGE_NAME}:${IMAGE_TAG}

docker-build:
	docker build -t ${IMG} . --platform=linux/amd64

docker-push: docker-build
	docker push $(IMG)

all:
	protoc --go_out=. --go_opt=paths=source_relative \
    --go-grpc_out=. --go-grpc_opt=paths=source_relative \
    proto/*.proto
