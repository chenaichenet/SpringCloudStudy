/**
 * FileName: PaymentService
 * Author:   嘉平十七
 * Date:     2020/8/12 9:37
 * Description:
 * notes：
 */
package com.springcloud.service;

import com.springcloud.domain.Payment;
import org.apache.ibatis.annotations.Param;

public interface PaymentService {

    int create(Payment payment); //写

    Payment getPaymentById(@Param("id") Long id);  //读取
}

