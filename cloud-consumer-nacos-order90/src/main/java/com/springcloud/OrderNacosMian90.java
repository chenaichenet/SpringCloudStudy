/**
 * FileName: OrderNacosMian90
 * Author:   嘉平十七
 * Date:     2020/8/16 17:04
 * Description:
 * notes：
 */
package com.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class OrderNacosMian90 {
    public static void main(String[] args) {
        SpringApplication.run(OrderNacosMian90.class,args);
    }
}