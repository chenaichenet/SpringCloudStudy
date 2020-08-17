/**
 * FileName: MyBatisConfig
 * Author:   嘉平十七
 * Date:     2020/8/17 16:23
 * Description:
 * notes：
 */
package com.springcloud.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan({"com.springcloud.dao"})
public class MyBatisConfig {
}