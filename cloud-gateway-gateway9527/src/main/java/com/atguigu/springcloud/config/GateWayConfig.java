package com.atguigu.springcloud.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GateWayConfig {
    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder routeLocatorBuilder){
        RouteLocatorBuilder.Builder routes = routeLocatorBuilder.routes();
        //访问http://localhost:9527/guonei将会转发到http://news.baidu.com/guonei，做了一个新的路由规则
        routes.route("path_route_atguigu",
                r -> r.path("/guonei").
                        uri("http://news.baidu.com/guonei")).build();
        return routes.build();
    }
}
