/**
 * FileName: GateWayConfig
 * Author:   嘉平十七
 * Date:     2020/8/15 21:25
 * Description: 使用Bean配置网关路由
 * notes：
 */
package com.springcloud.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GateWayConfig {
    /**
     * 配置一个id为route-name的路由规则
     * 当访问地址为 http://localhost:9527/guonei 时会自动转发到地址： http://news.baidu.com/guonei
     * @param routeLocatorBuilder
     * @return
     */
    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder routeLocatorBuilder) {
        RouteLocatorBuilder.Builder routes = routeLocatorBuilder.routes();  //对应yml中的routes
        routes.route("test_route", r -> r.path("/guonei").uri("http://news.baidu.com/guonei")).build();
        return routes.build();
    }
}