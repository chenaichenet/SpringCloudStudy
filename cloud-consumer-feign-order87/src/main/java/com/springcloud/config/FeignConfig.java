/**
 * FileName: FeignConfig
 * Author:   嘉平十七
 * Date:     2020/8/15 15:54
 * Description: 配置日志
 * notes：
 */
package com.springcloud.config;

import feign.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignConfig {
    @Bean
    Logger.Level feginLoggerLevel(){
        return Logger.Level.FULL;   //详细日志
    }
}