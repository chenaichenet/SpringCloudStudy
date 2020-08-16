/**
 * FileName: OrderNacosController
 * Author:   嘉平十七
 * Date:     2020/8/16 17:06
 * Description:
 * notes：
 */
package com.springcloud.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

@RestController
@Slf4j
public class OrderNacosController {
    //public static final String SERVER_URL="http://cloud-provider-nacos-service\n";
    //常规写法，但是现在在yml中配置了server-url，所以可以使用下面的方法

    @Resource
    private RestTemplate restTemplate;

    @Value("${service-url.nacos-user-service}")
    private String serverURL;

    @GetMapping(value = "/consumer/payment/nacos/{id}")
    public String paymentInfo(@PathVariable("id") Integer id) {
        return restTemplate.getForObject(serverURL+"/payment/nacos/"+id,String.class);
    }
}