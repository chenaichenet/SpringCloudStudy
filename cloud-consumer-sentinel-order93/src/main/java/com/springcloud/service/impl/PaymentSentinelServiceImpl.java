/**
 * FileName: PaymentSentinelServiceImpl
 * Author:   嘉平十七
 * Date:     2020/8/17 15:47
 * Description:
 * notes：
 */
package com.springcloud.service.impl;

import com.springcloud.domain.CommonResult;
import com.springcloud.domain.Payment;
import com.springcloud.service.PaymentSentinelService;
import org.springframework.stereotype.Component;

@Component
public class PaymentSentinelServiceImpl implements PaymentSentinelService {
    @Override
    public CommonResult<Payment> paymentSQL(Long id) {
        return new CommonResult<>(44444,"服务降级返回：PaymentSentinelServiceImpl",new Payment(id,"errorSerial"));
    }
}