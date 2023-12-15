FROM reg.docker.alibaba-inc.com/fuyi_docker/arms-baseline:1.0

COPY ./server/target/arms-prometheus-gateway  /home/admin/arms-prometheus-gateway

RUN chown -R admin:admin /home/admin/arms-prometheus-gateway

WORKDIR /home/admin/arms-prometheus-gateway

USER admin:admin

# official exp
ENTRYPOINT ["./bin/app.sh", "start"]



ADD admin /home/admin
ADD alidata /alidata
