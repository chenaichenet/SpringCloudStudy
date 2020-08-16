/**
 * FileName: PaymentNacosController
 * Author:   嘉平十七
 * Date:     2020/8/16 16:57
 * Description:
 * notes：
 */
package com.springcloud.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PaymentNacosController {
    @Value("${server.port}")
    private String serverPort;

    @GetMapping("/payment/nacos/{id}")
    public String getServerPort(@PathVariable("id") Integer id){
        return "Nacos, serverPort: "+ serverPort+"  ,id="+id;
    }
}