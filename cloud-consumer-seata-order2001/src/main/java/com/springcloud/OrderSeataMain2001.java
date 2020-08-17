/**
 * FileName: DataSourceProxyConfig
 * Author:   嘉平十七
 * Date:     2020/8/17 16:19
 * Description:
 * notes：
 */
package com.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableDiscoveryClient
@EnableFeignClients
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class) //取消数据源的自动创建
public class OrderSeataMain2001 {
    public static void main(String[] args) {
        SpringApplication.run(OrderSeataMain2001.class, args);
    }
}
