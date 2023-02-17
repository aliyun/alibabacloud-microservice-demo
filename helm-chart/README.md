
## install

```shell
helm3 upgrade mse-demo1 helm-chart/ \
  --namespace default --create-namespace \
  --install \
  --values helm-chart/values.yaml
```
