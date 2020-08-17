/**
 * FileName: PaymentSentinelMain9004
 * Author:   嘉平十七
 * Date:     2020/8/17 10:36
 * Description:
 * notes：
 */
package com.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class PaymentSentinelMain9004 {
    public static void main(String[] args) {
        SpringApplication.run(PaymentSentinelMain9004.class,args);
    }
}