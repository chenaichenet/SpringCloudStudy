/**
 * FileName: OrderController
 * Author:   嘉平十七
 * Date:     2020/8/13 9:07
 * Description:
 * notes：
 */
package com.springcloud.controller;

import com.springcloud.domain.CommonResult;
import com.springcloud.domain.Payment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

@RestController
public class OrderController {

//    public static final String PAYMENT_URL = "http://localhost:8001";   //定义服务提供者地址
    public static final String PAYMENT_URL = "http://CLOUD-PAYMENT-SERVICE";    //Eureka集群中不用写死

    @Resource
    private RestTemplate restTemplate;

    @GetMapping("/consumer/payment/create")
    public CommonResult<Payment> create(Payment payment) {
        //使用restTemplate，其中的三个参数是（REST请求地址，请求参数，HTTP响应转换被转换成的对象类型）
        return restTemplate.postForObject(PAYMENT_URL + "/payment/create", payment, CommonResult.class);  //写操作
    }

    @GetMapping("/consumer/payment/get/{id}")
    public CommonResult<Payment> getPayment(@PathVariable("id") Long id) {
        return restTemplate.getForObject(PAYMENT_URL + "/payment/get/" + id, CommonResult.class);
    }

    //分布式链路追踪
    @GetMapping("/consumer/payment/zipkin")
    public String paymentZipkin() {
        String result = restTemplate.getForObject("http://localhost:8001"+"/payment/zipkin/", String.class);
        return result;
    }
}