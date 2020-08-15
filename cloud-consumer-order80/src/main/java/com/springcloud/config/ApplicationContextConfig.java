/**
 * FileName: ApplicationContextConfig
 * Author:   嘉平十七
 * Date:     2020/8/13 9:06
 * Description: 配置类
 * notes：
 */
package com.springcloud.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class ApplicationContextConfig {
    @Bean
    //@LoadBalanced   //使用@LoadBalanced注解赋予RestTemplate负载均衡的能力

    public RestTemplate getRestTemplate(){
        //直接返回new一个对象就可以了
        return new RestTemplate();
    }
}