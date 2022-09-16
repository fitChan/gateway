package com.example.firstgateway.config;


import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//@Configuration
public class FilterConfig {


//    @Bean
    public RouteLocator gatewayRoutes(RouteLocatorBuilder builder) {
        int a = (int) (Math.random() * 10);
        String firstRequestValue = a + "= first-request-value";
        int b = (int) (Math.random() * 10);
        String firstResponseValue = b + "= first-response-value";
        int c = (int) (Math.random() * 10);
        String secondRequestValue = c + "= second-request-value";
        int d = (int) (Math.random() * 10);
        String secondResponseValue = d + "= second-response-value";
        return builder.routes()
                .route(r -> r.path("/first/**")
                                .filters(f -> f.addRequestHeader("first-request", firstRequestValue)
                                        .addResponseHeader("first-response", firstResponseValue))
                                .uri("http://localhost:8081/")
                        /* r이라는 매게변수가 있을때 r에다가
                        filter 설정을 하고 filter를 적용후에
                        uri 값으로 이동하겠습니다. */
                )
                .route(r -> r.path("/second/**")
                                .filters(f -> f.addRequestHeader("second-request", secondRequestValue)
                                        .addResponseHeader("second-response", secondResponseValue))
                                .uri("http://localhost:8082/")
                )
                .build();
    }
}
