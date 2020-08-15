/**
 * FileName: PaymentMain8004
 * Author:   嘉平十七
 * Date:     2020/8/14 9:53
 * Description: 主启动
 * notes：
 */
package com.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient  //该注解用于向使用consul或者zookeeper作为注册中心时注册服务
public class PaymentZKMain8004 {
    public static void main(String[] args) {
        SpringApplication.run(PaymentZKMain8004.class,args);
    }
}