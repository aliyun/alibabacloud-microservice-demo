package com.alibabcloud.mse.demo;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

@Component
public class ModifyPathGatewayFilterFactory extends AbstractGatewayFilterFactory<ModifyPathGatewayFilterFactory.Config> {

    public ModifyPathGatewayFilterFactory() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return ((exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();
            String path = request.getURI().getRawPath();
            if (path.startsWith(config.prefix)) {
                String newPath = config.newPrefix == null || config.newPrefix.isEmpty()
                        ? path.substring(config.prefix.length())
                        : config.newPrefix + path.substring(config.prefix.length());
                ServerHttpRequest newRequest = request.mutate().path(newPath).build();
                return chain.filter(exchange.mutate().request(newRequest).build());
            }
            return chain.filter(exchange);
        });
    }

    public static class Config {

        private String prefix;

        private String newPrefix;

        public String getPrefix() {
            return prefix;
        }

        public void setPrefix(String prefix) {
            this.prefix = prefix;
        }

        public String getNewPrefix() {
            return newPrefix;
        }

        public void setNewPrefix(String newPrefix) {
            this.newPrefix = newPrefix;
        }
    }
}
