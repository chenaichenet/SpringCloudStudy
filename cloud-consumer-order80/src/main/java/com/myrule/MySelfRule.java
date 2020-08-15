/**
 * FileName: MySelfRule
 * Author:   嘉平十七
 * Date:     2020/8/15 7:10
 * Description: Ribbon核心组件IRule的自定义负载算法
 * notes：
 */
package com.myrule;

import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.RandomRule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MySelfRule {
    @Bean
    public IRule myRule(){
        return new RandomRule();    //定义为随机
    }
}