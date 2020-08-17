/**
 * FileName: PaymentSentinelController
 * Author:   嘉平十七
 * Date:     2020/8/17 15:49
 * Description:
 * notes：
 */
package com.springcloud.controller;

import com.springcloud.domain.CommonResult;
import com.springcloud.domain.Payment;
import com.springcloud.service.PaymentSentinelService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class PaymentSentinelController {
    // OpenFeign
    @Resource
    private PaymentSentinelService paymentSentinelService;

    @GetMapping(value = "/consumer/paymentSQL/{id}")
    public CommonResult<Payment> paymentSQL(@PathVariable("id") Long id) {
        return paymentSentinelService.paymentSQL(id);
    }

}