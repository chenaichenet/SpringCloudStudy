# Spring Cloud学习笔记
#### README说明
````text
对应模块：该章学习中需要新建的模块，以及测试需要用到的模块。
笔记：着重一些脑图中没有提到的，或者修正的，以及一些重要的知识点。
````
#### 学习视频中的源码及脑图文件
https://github.com/chenaichenet/AtguiguSpringCloud      


## 微服务框架编码构建
#### 对应模块
```text
cloud-api-commons：提出的公共包（两个实体类）
cloud-provider-payment8001：作为服务提供者
cloud-consumer-order80：作为服务消费者
```
#### 笔记
```text
说说踩到的坑
cloud-provider-payment8001：
1、MySQL不同于之前的SQL server，它的连接参数有讲究，驱动有版本升级的区别，具体看yml文件。
2、模块中使用了不同于之前学习的mapper文件放到dao包中，所有除了添加@Mapper注解外，在resource中定义的mapper文件，也要在配置文件中声明。具体看yml文件。
3、Lombok使用1.16.18报错，修改程1.18.4版本完美解决。
4、还有教学视频中在.idea中的通过修改idea的workpace.xml的方式来快速打开Run DashBoard窗口的添加代码的方法在IDEA2019中无效。
    IDEA2019开启Run DashBoard参考这个笔记：https://blog.csdn.net/qq_38289863/article/details/101515838?utm_medium=distribute.pc_relevant.none-task-blog-BlogCommendFromMachineLearnPai2-1.channel_param&depth_1-utm_source=distribute.pc_relevant.none-task-blog-BlogCommendFromMachineLearnPai2-1.channel_param

除此之外，还是一贯的domain——dao——service——controller结构。

cloud-consumer-order80：
同样的模式，唯一不同的就是使用了RestTemplate把请求转发到另一个端口

@GetMapping("/consumer/payment/get/{id}")
    public CommonResult<Payment> getPayment(@PathVariable("id") Long id) {
        return restTemplate.getForObject(PAYMENT_URL + "/payment/get/" + id, CommonResult.class);
    }
```

## Eureka服务注册与发现
### 单机Eureka构建
#### 对应模块
```text
cloud-eureka-server7001：作为服务端注册中心
cloud-provider-payment8001：注册进eureka作为服务提供者
cloud-consumer-order80：注册进eureka作为服务消费者
```
#### 笔记
```text
pom文件中添加依赖
    <dependency>
        <groupId>org.springframework.cloud</groupId>
        <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
    </dependency>

yml中要将自己注册进Eureka

8001和80的主启动类都要添加注解@EnableEurekaClient，启用Eureka
```
### 集群Eureka构建
#### 对应模块
```text
同上，新增
cloud-eureka-server7002：作为服务端注册中心
cloud-provider-payment8002：作为服务消费者
```
#### 笔记
```text
需要修改host文件，添加
    127.0.0.1  eureka7001.com
    127.0.0.1  eureka7002.com

集群Eureka需要互相注册，互相守望，即7001指向7002，7002指向7001，具体查看application.yml文件

负载均衡：
    使用@LoadBalanced注解赋予RestTemplate负载均衡的能力（默认轮询）。
        @Bean
        @LoadBalanced //使用@LoadBalanced注解赋予RestTemplate负载均衡的能力
        public RestTemplate getRestTemplate(){
            return new RestTemplate();
        }

服务信息完善：
    instance.instance-id= payment8001 定义服务名称
    instance.prefer-ip-address= true 显示IP

服务发现：
    主启动类使用@EnableDiscoveryClient注解，通过List<String> services=discoveryClient.getServices();得到微服务的所有具体实例。

自我保护：
    7001端使用eureka.server.enable-self-preservation = false可以禁用自我保护模式
    8001端使用eureka.instance.lease-renewal-interval-in-seconds，eureka.instance.lease-expiration-duration-in-seconds定义发送心跳时间间隔和等待时间上限
```

## Zookeeper服务注册与发现
#### 对应模块
```text
cloud-provider-zookeeper-payment8004
cloud-consumer-zookeeper-order84
```
#### 笔记
```text
@EnableDiscoveryClient  //该注解用于向使用consul或者zookeeper作为注册中心时注册服务
```

## Consul服务注册与发现
#### 对应模块
```text
cloud-provider-consul-payment8006
cloud-consumer-consul-order86
```

## 三个注册中心的区别
```text
CAP
C：Consistency，强一致性
A：Availability，可用性
P：Partition tolerance，分区容错

AP：Eureka
CP：Zookeeper，Consul
```

## Ribbon负载均衡服务调用
#### 对应模块
```text
cloud-provider-payment8001
cloud-provider-payment8002
cloud-consumer-order80
```
#### 笔记
```text
ApplicationContextBean去掉@LoadBalanced

定义接口：
    public interface LoadBalancer {
        //收集服务器总共有多少台能够提供服务的机器，并放到list里面
        ServiceInstance instances(List<ServiceInstance> serviceInstances);
    }
通过继承该接口，实现自定义的本地负载均衡
```
## OpenFeign服务接口调用
#### 对应模块
```text
cloud-provider-payment8001
cloud-consumer-feign-order87
```
#### 笔记
```text
主启动类添加@EnableFeignClients，开启服务接口调用

业务逻辑接口+@FeignClient配置调用provider服务
    @Component
    @FeignClient(value = "CLOUD-PAYMENT-SERVICE")
    public interface PaymentFeignService {
    
        @GetMapping(value = "/payment/get/{id}")  //测试查询  
        public CommonResult getPaymentById(@PathVariable("id") Long id);
    }

Feign提供了习惯的面向接口编程

超时控制：
    OpenFeign默认等待一秒钟，超过后报错，OpenFeign默认支持Ribbon
```

## Hystrix断路器
#### 对应模块
```text
cloud-eureka-server7001：作为单机测试服务注册中心
cloud-provider-hystrix-payment8008
cloud-consumer-hystrix-order88
cloud-consumer-hystrix-dashboard9001：服务监控仪表盘
```
#### 笔记
```text
服务降级：
    8008业务类：
        //定义兜底方法和错误要求
        @HystrixCommand(fallbackMethod = "paymentInfo_TimeOutHandler",commandProperties = {
                    @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds",value = "3000")  //3秒钟以内就是正常的业务逻辑
            })  //前一个注解表示出了问题找方法paymentInfo_TimeOutHandler，后一个注解为timeoutInMilliseconds这个线程超时时间，表示3秒钟以内是正常业务逻辑。
        public String paymentInfo_TimeOut(Integer id){}
        //兜底方法
        public String paymentTimeOutFallbackMethod(@PathVariable("id") Integer id){
            return "我是消费者80，对付支付系统繁忙请10秒钟后再试或者自己运行出错请检查自己,(┬＿┬)";
        }
    8008主启动类：
        添加新注解@EnableCircuitBreaker，启用熔断器
    88业务类：
        同上
    88主启动类：
        添加注解@EnableHystrix，启用Hystrix

服务熔断：
    8008业务类：
        //服务熔断
        @HystrixCommand(fallbackMethod = "paymentCircuitBreaker_fallback",commandProperties = {
                @HystrixProperty(name = "circuitBreaker.enabled",value = "true"),  //是否开启断路器
                @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold",value = "10"),   //请求次数
                @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds",value = "10000"),  //时间范围
                @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage",value = "60"), //失败率达到多少后跳闸
        })  //上面表示，在10秒内，请求次数达到10次，且错误率为60%时，开启服务熔断
        public String paymentCircuitBreaker(@PathVariable("id") Integer id){}
        public String paymentCircuitBreaker_fallback(@PathVariable("id") Integer id){}

服务监控：
    主启动中需要配置Bean，让后访问http://localhost:9001/hystrix
    服务监控只能监控熔断，不能监控降级
```

## GateWay网关
#### 对应模块
```text
cloud-gateway-server9527：网关服务
cloud-eureka-server7001
cloud-eureka-server7002
cloud-provider-payment8001
```
#### 笔记
```text
配置路由有两种方式，一种是通过yml配置文件，一种是通过代码中注入RouteLocator的Bean

yml方式：
    cloud:
        gateway:
          routes:
            - id: payment_routh #路由的ID，没有固定规则但要求唯一，建议配合服务名
              uri: http://localhost:8001   #匹配后提供服务的路由地址
              predicates:
                - Path=/payment/get/**   #断言,路径相匹配的进行路由
            - id: payment_routh2
              uri: http://localhost:8001
              predicates:
                - Path=/payment/lb/**   #断言,路径相匹配的进行路由

代码方式：
    @Bean
        public RouteLocator customRouteLocator(RouteLocatorBuilder routeLocatorBuilder) {
            RouteLocatorBuilder.Builder routes = routeLocatorBuilder.routes();  //对应yml中的routes
            routes.route("test_route", r -> r.path("/guonei").uri("http://news.baidu.com/guonei")).build();
            return routes.build();
        }

常用的Route Predicate：
    - Path=/payment/lb/**   #断言,路径相匹配的进行路由
    - After=2020-08-16T08:41:27.049083200+08:00[Asia/Shanghai]  #表示在这个时间以后，请求访问才有效果
    - Before=2020-08-17T08:41:27.049083200+08:00[Asia/Shanghai] #表示在这个时间之前，请求访问才有效果
    - Between=2020-08-16T08:41:27.049083200+08:00[Asia/Shanghai],2020-08-17T08:41:27.049083200+08:00[Asia/Shanghai] #在特定时间内
    - Cookie=username,test #cookie名为username，值为test（可以设置正则表达式）
    - Hander=X-Request-Id, \d+  #请求头要有 X-Request-Id 属性并且值为整数的正则表达式
    - Host=**.springcloud.com   #主机地址
    - Method=GET  #请求类型
    - Query=username, \d+   #要有参数名称并且是正整数才能路由

自定义全局GlobalFilter：
    继承两个接口：GlobalFilter ,Ordered，实现其方法，一个定义拦截规则，一个定义过滤器加载顺序
```
## Config分布式配置中心
#### 对应模块
```text
cloud-config-center3344：服务端
cloud-config-client3355：客户端
cloud-eureka-server7001：3344，3355注册进了eureka，而eureka中定义的是集群模式（可以改为单机）
cloud-eureka-server7002
```
#### 笔记
```text
cloud-center3344:
    yml中定义文件路径，详细见yml文件
    这里有一个坑，不知原因，IDEA中使用SSH的话会拉取不到文件，但是使用HTTP的话就可以

    主启动类使用注解：@EnableConfigServer

    读取配置规则：
        1、/{label}/{application}-{profile}.yml（推荐使用这种方式）：
            http://localhost:3344/master/config-dev.yml
        2、/{application}-{profile}.yml：
            http://localhost:3344/config-dev.yml
        3、/{application}-{profile}[/{label}]：
            http://localhost:3344/config/dev/master

cloud-config-client3355:
    新增了一个bootstap.yml文件：
        application.yml 是用户级的资源配置项
        bootstrap.yml 是系统级的，优先级更高
    要将Client模块下的application.yml文件改为bootstrap.yml，这是很关键的
```
## Bus消息总线
#### 对应模块
```text

```
## Stream消息驱动
#### 对应模块
```text

```
## Sleuth分布式请求链路追踪
#### 对应模块
```text

```
## Alibaba Nacos服务注册中心和配置中心
#### 对应模块
```text
服务注册中心：
    cloud-provider-nacos-payment9001
    cloud-provider-nacos-payment9002
    cloud-consumer-nacos-order90
服务配置中心：
    cloud-config-nacos-client3377
```
## Alibaba Sentinel高可用流量管理框架
#### 对应模块
```text

```
## Alibaba Seata分布式事务中间件
#### 对应模块
```text

```