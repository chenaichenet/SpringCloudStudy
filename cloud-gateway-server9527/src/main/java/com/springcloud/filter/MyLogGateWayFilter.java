/**
 * FileName: MyLogGateWayFilter
 * Author:   嘉平十七
 * Date:     2020/8/16 8:57
 * Description:
 * notes：
 */
package com.springcloud.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Date;

@Component
@Slf4j
public class MyLogGateWayFilter implements GlobalFilter, Ordered {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        log.info("Come in MyLogGateWayFilter: "+new Date());
        String uname = exchange.getRequest().getQueryParams().getFirst("test"); //获取名为uname的请求参数
        if(uname==null){
            log.info("用户名为Null，非法用户");
            exchange.getResponse().setStatusCode(HttpStatus.NOT_ACCEPTABLE);    //设置状态码，不被接受
            return exchange.getResponse().setComplete();
        }
        return chain.filter(exchange);  //放行
    }

    @Override
    public int getOrder() {
        return 0;   //过滤器的加载顺序，0，优先级最高
    }
}