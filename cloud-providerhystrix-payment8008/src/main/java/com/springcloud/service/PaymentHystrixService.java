/**
 * FileName: PaymentService
 * Author:   嘉平十七
 * Date:     2020/8/15 16:31
 * Description:
 * notes：
 */
package com.springcloud.service;

import cn.hutool.core.util.IdUtil;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.concurrent.TimeUnit;

@Service
public class PaymentHystrixService {
    //正常访问，成功的方法
    public String paymentInfo_OK(Integer id){
        return "线程池："+Thread.currentThread().getName()+", \t 显示信息：paymentInfo_OK    ,   id："+id+"\t";
    }

    //手动定义延迟，服务降级
    @HystrixCommand(fallbackMethod = "paymentInfo_TimeOutHandler",commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds",value = "3000")  //3秒钟以内就是正常的业务逻辑
    })  //前一个注解表示出了问题找方法paymentInfo_TimeOutHandler，后一个注解为timeoutInMilliseconds这个线程超时时间，表示3秒钟以内是正常业务逻辑。
    public String paymentInfo_TimeOut(Integer id){
        //int timeNumber = 3;     //此时是正常的
        int timeNumber = 5;     //此时是超时错误
        //int age = 10/0;         //此时是运行错误

        try {
            TimeUnit.SECONDS.sleep(timeNumber);
        }catch (Exception e) {
            e.printStackTrace();
        }
        return "线程池："+Thread.currentThread().getName()+", \t 显示信息：paymentInfo_TimeOut    ,  id："+id+" \t ,耗时(秒): "+timeNumber;
    }

    //服务降级的兜底方法
    public String paymentInfo_TimeOutHandler(Integer id){
        return "线程池："+Thread.currentThread().getName()+"\t 显示信息：系统繁忙, 请稍候再试    ,    id："+id+"\t";
    }

    //服务熔断
    @HystrixCommand(fallbackMethod = "paymentCircuitBreaker_fallback",commandProperties = {
            @HystrixProperty(name = "circuitBreaker.enabled",value = "true"),  //是否开启断路器
            @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold",value = "10"),   //请求次数
            @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds",value = "10000"),  //时间范围
            @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage",value = "60"), //失败率达到多少后跳闸
    })  //上面表示，在10秒内，请求次数达到10次，且错误率为60%时，开启服务熔断
    public String paymentCircuitBreaker(@PathVariable("id") Integer id){
        if (id < 0){
            throw new RuntimeException("id不能负数");
        }
        //hutool工具类，地址：https://www.hutool.cn/docs/#/
    /*<dependency>
        <groupId>cn.hutool</groupId>
        <artifactId>hutool-all</artifactId>
        <version>5.3.10</version>
    </dependency>*/
        String serialNumber = IdUtil.simpleUUID();
        return Thread.currentThread().getName()+"\t"+"调用成功,流水号："+serialNumber;
    }

    public String paymentCircuitBreaker_fallback(@PathVariable("id") Integer id){
        return "id 不能负数，请稍候再试    ,  id: " +id;
    }

}