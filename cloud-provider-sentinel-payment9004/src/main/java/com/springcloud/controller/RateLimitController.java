/**
 * FileName: RateLimitController
 * Author:   嘉平十七
 * Date:     2020/8/17 14:43
 * Description: 限流
 * notes：
 */
package com.springcloud.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.springcloud.domain.CommonResult;
import com.springcloud.domain.Payment;
import com.springcloud.myhandler.CustomerBlockHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RateLimitController {

    //按资源名称限流——流控规则中添加资源名为byResource
    @GetMapping("/byResource")
    @SentinelResource(value = "byResource",blockHandler = "handleException")
    public CommonResult byResource() {
        return new CommonResult(200,"按资源名称限流测试",new Payment(2020L,"serial001"));
    }
    //兜底方法
    public CommonResult handleException(BlockException exception) {
        return new CommonResult(444,exception.getClass().getCanonicalName()+"\t 服务不可用");
    }

    //按照Url地址限流+后续处理——流控规则中的资源名为/rateLimit/byUrl
    @GetMapping("/rateLimit/byUrl")
    @SentinelResource(value = "byUrl")
    public CommonResult byUrl() {
        return new CommonResult(200,"按url限流测试",new Payment(2020L,"serial002"));
    }

    //使用自定义限流处理类
    @GetMapping("/rateLimit/customerBlockHandler")
    @SentinelResource(value = "customerBlockHandler",
            blockHandlerClass = CustomerBlockHandler.class,
            blockHandler = "handlerException2")
    public CommonResult customerBlockHandler() {
        return new CommonResult(200,"按客戶自定义",new Payment(2020L,"serial003"));
    }
}