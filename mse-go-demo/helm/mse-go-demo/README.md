## build

At directory `A`/`B`/`C`/`gateway`:

* execute `./build.sh` to build image
* `docker tag` to tag image
* `docker push` to push image

## install

```shell
helm3 upgrade mse-simple-demo1 helm/mse-simple-demo \
  --namespace default --create-namespace \
  --install \
  --values helm/mse-simple-demo/values.example.yaml
```
