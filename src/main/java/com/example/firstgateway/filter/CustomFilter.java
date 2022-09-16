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
public class CustomFilter extends AbstractGatewayFilterFactory<CustomFilter.Config> {

    public CustomFilter(){
        super(Config.class);
    }



    @Override
    public GatewayFilter apply(Config config) {
        return (e, c) -> {
            ServerHttpRequest request = e.getRequest();
            ServerHttpResponse response = e.getResponse();
            log.info("Custom PRE filter : request id == {}", request.getId());

            //Custom Post Filter
            return c.filter(e).then(Mono.fromRunnable(() -> {
                log.info("Custom POST filter : response code == {}", response.getStatusCode());
            }));
        };
    }


    public static class Config {
    }

    /*pre필터에서 유저의 정보를 제대로 가지고있는지 feat.JWT 채크 가능하다.
    로그인 정보를 추가시켜보자 */

}
