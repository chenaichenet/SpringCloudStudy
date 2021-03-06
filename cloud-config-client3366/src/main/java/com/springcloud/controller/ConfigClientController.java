/**
 * FileName: ConfigClientController
 * Author:   嘉平十七
 * Date:     2020/8/17 8:40
 * Description:
 * notes：
 */
package com.springcloud.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ConfigClientController {
    @Value("${server.prot}")
    private String serverPort;

    //Config可以将配置信息以Rest接口的形式暴露
    @Value("${config.info}")
    private String configInfo;

    @GetMapping("/config/configInfo")
    public String getConfigInfo(){
        return "ServerPort: "+serverPort+"\t\nConfigInfo: "+configInfo;
    }
}