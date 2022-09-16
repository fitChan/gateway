package com.example.firstgateway.filter;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class CustomFilterExample extends AbstractGatewayFilterFactory<CustomFilterExample.Config> {

    public CustomFilterExample() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();
            ServerHttpResponse response = exchange.getResponse();
            log.info(config.getBaseMessage()+"**************************");

            log.info("Custom PRE filter : request id == {}", request.getId());

            return chain.filter(exchange).then(Mono.fromRunnable(() -> {
                log.info("Custom POST filter : response code == {}", response.getStatusCode());
            }));

        };
    }

    @Data
    public static class Config {
        private String baseMessage;
    }
}
