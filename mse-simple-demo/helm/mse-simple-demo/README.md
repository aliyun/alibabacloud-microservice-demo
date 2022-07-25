## build

At directory `A`/`B`/`C`/`gateway`:

* execute `./build.sh` to build image
* `docker tag` to tag image
* `docker push` to push image

## install

```shell
helm3 upgrade helm/mse-simple-demo \
  --namespace default --create-namespace \
  --install mse-simple-demo1 \
  --values helm/mse-simple-demo/values.example.yaml
```
