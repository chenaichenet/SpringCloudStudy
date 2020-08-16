/**
 * FileName: OrderMain88
 * Author:   嘉平十七
 * Date:     2020/8/15 16:49
 * Description:
 * notes：
 */
package com.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients //服务接口调用
@EnableHystrix  //激活服务降级
public class OrderHystrixMain88 {
    public static void main(String[] args) {
        SpringApplication.run(OrderHystrixMain88.class,args);
    }
}