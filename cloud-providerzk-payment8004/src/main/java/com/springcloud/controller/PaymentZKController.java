/**
 * FileName: PaymentController
 * Author:   嘉平十七
 * Date:     2020/8/14 9:54
 * Description:
 * notes：
 */
package com.springcloud.controller;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@Slf4j
public class PaymentZKController {
    @Value("${server.port}")
    private String serverPort;

    @GetMapping(value = "/payment/zk")
    public String paymentzk(){
        return "SpringCloud with zookeeper:"+serverPort+"\t"+ UUID.randomUUID().toString();
    }

}