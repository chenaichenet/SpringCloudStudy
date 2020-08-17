/**
 * FileName: PaymentSentielMain9006
 * Author:   嘉平十七
 * Date:     2020/8/17 15:02
 * Description:
 * notes：
 */
package com.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class PaymentSentielMain9006 {
    public static void main(String[] args) {
        SpringApplication.run(PaymentSentielMain9006.class,args);
    }
}