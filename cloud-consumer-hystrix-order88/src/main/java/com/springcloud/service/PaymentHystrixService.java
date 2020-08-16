/**
 * FileName: PaymentHystrixService
 * Author:   嘉平十七
 * Date:     2020/8/15 16:51
 * Description:
 * notes：
 */
package com.springcloud.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Component
@FeignClient(value = "CLOUD-PROVIDER-HYSTRIX-SERVICE")  //调用名为CLOUD-PROVIDER-HYSTRIX-PAYMENT的service暴露的ok和timeout两个
public interface PaymentHystrixService {
    @GetMapping("/payment/hystrix/ok/{id}")
    String paymentInfo_OK(@PathVariable("id") Integer id);

    @GetMapping("/payment/hystrix/timeout/{id}")
    String paymentInfo_TimeOut(@PathVariable("id") Integer id);
}
