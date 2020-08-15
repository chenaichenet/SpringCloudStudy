/**
 * FileName: MyLB
 * Author:   嘉平十七
 * Date:     2020/8/15 7:19
 * Description: 自定义负载均衡器
 * notes：
 */
package com.springcloud.lb;

import org.springframework.cloud.client.ServiceInstance;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class MyLB implements LoadBalancer {
    private AtomicInteger atomicInteger = new AtomicInteger(0);

    //坐标
    private final int getAndIncrement(){
        int current;
        int next;
        do {    //和源码中的for(;;)一样，自旋锁
            current = this.atomicInteger.get(); //当前值为0
            next = current >= 2147483647 ? 0 : current + 1; //溢出重新计算，否则加1
        }while (!this.atomicInteger.compareAndSet(current,next));    //第一个参数是期望值，第二个参数是修改值
        System.out.println("第几次访问，访问次数："+next);
        return next;
    }

    @Override
    public ServiceInstance instances(List<ServiceInstance> serviceInstances) { //得到机器的列表
        int index = getAndIncrement() % serviceInstances.size(); //得到服务器的下标
        return serviceInstances.get(index);
    }
}