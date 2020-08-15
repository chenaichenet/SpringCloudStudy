/**
 * FileName: LoadBalancer
 * Author:   嘉平十七
 * Date:     2020/8/15 7:17
 * Description: 自定义负载均衡器
 * notes：
 */
package com.springcloud.lb;

import org.springframework.cloud.client.ServiceInstance;

import java.util.List;

public interface LoadBalancer {
    //收集服务器总共有多少台能够提供服务的机器，并放到list里面
    ServiceInstance instances(List<ServiceInstance> serviceInstances);
}