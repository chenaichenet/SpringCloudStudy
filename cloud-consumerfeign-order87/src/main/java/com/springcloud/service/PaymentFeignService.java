/**
 * FileName: PaymentFeignService
 * Author:   嘉平十七
 * Date:     2020/8/15 9:41
 * Description:
 * notes：
 */
package com.springcloud.service;

import com.springcloud.domain.CommonResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Component
@FeignClient(value = "CLOUD-PAYMENT-SERVICE")   //接口调用服务
public interface PaymentFeignService {
    @GetMapping(value = "/payment/get/{id}")  //测试查询
    CommonResult getPaymentById(@PathVariable("id") Long id);
    //手动定义超时
    String paymentFeignTimeout();
}