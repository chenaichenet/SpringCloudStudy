/**
 * FileName: PaymentDao
 * Author:   嘉平十七
 * Date:     2020/8/12 9:36
 * Description:
 * notes：
 */
package com.springcloud.dao;

import com.springcloud.domain.Payment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface PaymentDao{
    int create(Payment payment);
    Payment getPaymentById(@Param("id") Long id);
}

