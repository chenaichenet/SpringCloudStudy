/**
 * FileName: IMessageProviderImpl
 * Author:   嘉平十七
 * Date:     2020/8/17 9:18
 * Description:
 * notes：
 */
package com.springcloud.service.impl;

import com.springcloud.service.IMessageProvider;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.MessageChannel;

import javax.annotation.Resource;
import java.util.UUID;

@EnableBinding(Source.class)    //定义消息的推送管道，Source是前面Spring Cloud Stream标准流程套路中提到的
public class IMessageProviderImpl implements IMessageProvider {
    @Resource
    private MessageChannel output;  //消息发送管道

    @Override
    public String send() {
        String serial = UUID.randomUUID().toString();
        output.send(MessageBuilder.withPayload(serial).build());
        System.out.println("serial: "+serial);
        return serial;
    }
}