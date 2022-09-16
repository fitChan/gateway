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
public class GlobalFilter extends AbstractGatewayFilterFactory<GlobalFilter.Config> {

    public GlobalFilter() {
        super(Config.class);
    }


    @Override
    public GatewayFilter apply(Config config) {
        return (e, c) -> {
            ServerHttpRequest request = e.getRequest();
            ServerHttpResponse response = e.getResponse();

            log.info("Global filter baseMessage : request id == {}", config.getBaseMessage());
            if(config.isPreLogger()){
                log.info("Global filter start : request id == {}",request.getId());
            }

            //Custom Post Filter
            return c.filter(e).then(Mono.fromRunnable(() -> {
                if(config.isPostLogger()) {
                    log.info("Global filter End : response code == {}", response.getStatusCode());
                }
            }));
        };
    }

    @Data
    public static class Config {
        private boolean preLogger;
        private boolean postLogger;
        private String baseMessage;
    }

    /*pre필터에서 유저의 정보를 제대로 가지고있는지 feat.JWT 채크 가능하다.
    로그인 정보를 추가시켜보자 */

}
