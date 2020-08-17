/**
 * FileName: SendMessageController
 * Author:   嘉平十七
 * Date:     2020/8/17 9:22
 * Description:
 * notes：
 */
package com.springcloud.controller;

import com.springcloud.service.IMessageProvider;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class SendMessageController {
    @Resource
    private IMessageProvider messageProvider;

    @GetMapping("/sendMessage")
    public String sendMessage(){
        return messageProvider.send();
    }
}