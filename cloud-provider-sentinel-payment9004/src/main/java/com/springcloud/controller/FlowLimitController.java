/**
 * FileName: FlowLimitController
 * Author:   嘉平十七
 * Date:     2020/8/17 10:37
 * Description:
 * notes：
 */
package com.springcloud.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

@RestController
@Slf4j
public class FlowLimitController {
    @GetMapping("/testA")
    public String testA() {
        return "-----testA-----";
    }

    @GetMapping("/testB")
    public String testB() {
        return "-----testB-----";
    }

    //服务降级规则：RT
    @GetMapping("/testC")
    public String testC() {
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.info("testC 测试RT");
        return "-----testC：服务降级——RT-----";
    }

    //服务降级规则：异常比例
    @GetMapping("/testD")
    public String testD(){
        log.info("testD 异常比例");
        int age = 10/0;
        return "-----testD：服务降级——异常比例-----";
    }

    //服务降级规则：异常数
    @GetMapping("/testE")
    public String testE() {
        log.info("testE 测试异常数");
        int age = 10/0;
        return "-----testE：服务降级——异常数-----";
    }

    //热点限流
    @GetMapping("/testHotKey")
    @SentinelResource(value = "testHotKey",blockHandler = "deal_testHotKey")    //第一个是热点规则资源名，第二个是兜底方法
    public String testHotKey(@RequestParam(value = "p1",required = false) String p1, @RequestParam(value = "p2",required = false) String p2) {
        //int age = 10/0;
        return "-----testHotKey：热点限流-----";
    }
    //兜底方法
    public String deal_testHotKey (String p1, String p2, BlockException exception) {
        return "------deal_testHotKey：兜底方法-----";  //sentinel系统默认的提示：Blocked by Sentinel (flow limiting)
    }
}