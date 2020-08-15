/**
 * FileName: ApplicationContextConfig
 * Author:   嘉平十七
 * Date:     2020/8/14 11:00
 * Description: 配置bean
 * notes：
 */
package com.springcloud.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class ApplicationContextConfig {
    @Bean
    @LoadBalanced
    public RestTemplate getRestTemplate(){
        return new RestTemplate();
    }
}