/**
 * FileName: CustomerBlockHandler
 * Author:   嘉平十七
 * Date:     2020/8/17 14:50
 * Description: 自定义限流处理类
 * notes：
 */
package com.springcloud.myhandler;

import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.springcloud.domain.CommonResult;

public class CustomerBlockHandler {
    public static CommonResult handleException(BlockException exception) {
        return new CommonResult(4444, "按客户自定义....CustomerBlockHandler---1");
    }
    public static CommonResult handleException2(BlockException exception) {
        return new CommonResult(4444, "按客户自定义，....CustomerBlockHandler---2");
    }
}