/**
 * FileName: PaymentSentinelService
 * Author:   嘉平十七
 * Date:     2020/8/17 15:45
 * Description:
 * notes：
 */
package com.springcloud.service;

import com.springcloud.domain.CommonResult;
import com.springcloud.domain.Payment;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "cloud-provider-sentinel-service",fallback = PaymentSentinelService.class)
public interface PaymentSentinelService {
    @GetMapping("/paymentSQL/{id}")
    CommonResult<Payment> paymentSQL(@PathVariable("id") Long id);
}