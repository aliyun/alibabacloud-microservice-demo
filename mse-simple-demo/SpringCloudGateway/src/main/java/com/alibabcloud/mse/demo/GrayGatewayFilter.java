package com.alibabcloud.mse.demo;

import io.opentelemetry.api.baggage.Baggage;
import io.opentelemetry.context.Context;
import io.opentelemetry.context.Scope;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class GrayGatewayFilter implements GlobalFilter, Ordered {
    private static final Logger log = LoggerFactory.getLogger(GrayGatewayFilter.class);

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        MultiValueMap<String, String> params = exchange.getRequest().getQueryParams();
        Scope baggageScope = null;
        try {
            if ("12345".equals(params.getFirst("userId"))) {
                String tag = "gray";
                // 标记流量标签
                // key固定为__microservice_tag__
                // value为合法json，同时tag为流量的标签结果
                Baggage baggage = Baggage.builder()
                        .put("__microservice_tag__", "[{\"name\":\"" + tag + "\"}]")
                        .build();
                log.info("request with userId: {}, tagged {}", params.getFirst("userId"), tag);
                baggageScope = baggage.storeInContext(Context.current()).makeCurrent();
            }
            return chain.filter(exchange);
        } finally {
            if (baggageScope != null) {
                baggageScope.close();
            }
        }
    }

    /**
     * 由于正常的业务请求处理也是一个Filter，所以这儿要确保顺序在业务Filter之前
     * @return
     */
    @Override
    public int getOrder() {
        return -1;
    }
}

