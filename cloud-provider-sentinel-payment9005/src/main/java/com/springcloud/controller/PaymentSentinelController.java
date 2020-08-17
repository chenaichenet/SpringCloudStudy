/**
 * FileName: PaymentSentinelController
 * Author:   嘉平十七
 * Date:     2020/8/17 15:04
 * Description:
 * notes：
 */
package com.springcloud.controller;

import com.springcloud.domain.CommonResult;
import com.springcloud.domain.Payment;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
public class PaymentSentinelController {
    @Value("${server.port}")
    private String serverPort;

    //模拟dao层，不再访问数据库了
    public static HashMap<Long, Payment> hashMap = new HashMap<>();
    static{
        hashMap.put(1L,new Payment(1L,"28a8c1e3bc2742d8848569891fb42181"));
        hashMap.put(2L,new Payment(2L,"bba8c1e3bc2742d8848569891ac32182"));
        hashMap.put(3L,new Payment(3L,"6ua8c1e3bc2742d8848569891xt92183"));
    }

    @GetMapping(value = "/paymentSQL/{id}")
    public CommonResult<Payment> paymentSQL(@PathVariable("id") Long id){
        Payment payment = hashMap.get(id);
        CommonResult<Payment> result = new CommonResult(200,"From MySQL,serverPort："+serverPort,payment);
        return result;
    }
}