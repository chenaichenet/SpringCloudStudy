/**
 * FileName: PaymentController
 * Author:   嘉平十七
 * Date:     2020/8/12 9:39
 * Description:
 * notes：
 */
package com.springcloud.controller;

import com.springcloud.domain.CommonResult;
import com.springcloud.domain.Payment;
import com.springcloud.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController
@Slf4j
public class PaymentController
{
    @Resource
    private PaymentService paymentService;

    @Value("${server.port}")
    private String serverPort;

    @Resource
    private DiscoveryClient discoveryClient;

    @PostMapping(value = "/payment/create")
    public CommonResult create(@RequestBody Payment payment) {
        int result = paymentService.create(payment);
        log.info("插入结果："+result);

        if(result > 0) {
            return new CommonResult(200,"插入数据库成功,serverPort: "+serverPort,result);
        }else{
            return new CommonResult(444,"插入数据库失败",null);
        }
    }

    @GetMapping(value = "/payment/get/{id}")
    public CommonResult<Payment> getPaymentById(@PathVariable("id") Long id) {
        Payment payment = paymentService.getPaymentById(id);
        if(payment != null)
        {
            return new CommonResult(200,"查询成功,serverPort:  "+serverPort,payment);
        }else{
            return new CommonResult(444,"没有对应记录,查询ID: "+id,null);
        }
    }

    //服务发现
    @GetMapping(value = "/payment/discovery")
    public Object discovery(){
        List<String> services=discoveryClient.getServices();
        for(String element:services){
            log.info("Element:"+element);
        }

        //微服务下的全部具体实例
        List<ServiceInstance> instances=discoveryClient.getInstances("CLOUD-PAYMENT-SERVICE");
        for(ServiceInstance instance:instances){
            log.info(instance.getServiceId()+"\t"+instance.getHost()+"\t"+instance.getPort()+"\t"+instance.getUri());
        }
        return this.discoveryClient;
    }

    //手写本地负载均衡器测试
    @GetMapping("/payment/lb")
    public String getPaymentLB(){
        return serverPort;
    }


    //OpenFeign超时控制，手动超时
    @GetMapping(value = "/payment/feign/timeout")
    public String paymentFeignTimeout(){
        try {
            TimeUnit.SECONDS.sleep(3);  //默认是1秒
        }catch (Exception e){
            e.printStackTrace();
        }
        return serverPort;
    }

    //分布式链路追踪
    @GetMapping("/payment/zipkin")
    public String paymentZipkin() {
        return "分布式服务追踪";
    }

}