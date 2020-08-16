/**
 * FileName: ConfigClientController
 * Author:   嘉平十七
 * Date:     2020/8/16 11:18
 * Description:
 * notes：
 */
package com.springcloud.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ConfigClientController {
    //Config可以将配置信息以Rest接口的形式暴露
    @Value("${config.info}")
    private String configInfo;

    @GetMapping("/config/configInfo")
    public String getConfigInfo(){
        return configInfo;
    }
}