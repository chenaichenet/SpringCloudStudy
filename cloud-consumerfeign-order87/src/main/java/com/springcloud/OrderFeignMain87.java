/**
 * FileName: OrderFeignMain80
 * Author:   嘉平十七
 * Date:     2020/8/15 9:39
 * Description:
 * notes：
 */
package com.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients     //激活服务接口调用
public class OrderFeignMain87 {
    public static void main(String[] args) {
        SpringApplication.run(OrderFeignMain87.class,args);
    }
}