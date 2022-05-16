

### install

```shell
helm3 upgrade \
  --install mse-simple-demo1 helm/mse-simple-demo \
  --namespace default --create-namespace \
  --values helm/mse-simple-demo/values.example.yaml
```