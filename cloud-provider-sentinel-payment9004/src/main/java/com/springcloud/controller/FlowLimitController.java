/**
 * FileName: FlowLimitController
 * Author:   嘉平十七
 * Date:     2020/8/17 10:37
 * Description:
 * notes：
 */
package com.springcloud.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FlowLimitController {
    @GetMapping("/testA")
    public String testA() {
        return "-----testA-----";
    }

    @GetMapping("/testB")
    public String testB() {
        return "-----testB-----";
    }
}