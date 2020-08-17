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
    同样的模式，唯一不同的就是使用了RestTemplate把请求转发到另一个端口。

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
pom文件中添加依赖：
    <dependency>
        <groupId>org.springframework.cloud</groupId>
        <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
    </dependency>

yml中要将自己注册进Eureka。

8001和80的主启动类都要添加注解@EnableEurekaClient，启用Eureka。
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
修改host文件（下面的Config中也是修改host文件，但是同样可以不修改，不修改的话就直接使用端口访问），添加：
    127.0.0.1  eureka7001.com
    127.0.0.1  eureka7002.com

集群Eureka需要互相注册，互相守望，即7001指向7002，7002指向7001，具体查看application.yml文件。

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
    7001端使用eureka.server.enable-self-preservation = false可以禁用自我保护模式。
    8001端使用eureka.instance.lease-renewal-interval-in-seconds，eureka.instance.lease-expiration-duration-in-seconds定义发送心跳时间间隔和等待时间上限。
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
ApplicationContextBean去掉@LoadBalanced。

定义接口：
    public interface LoadBalancer {
        //收集服务器总共有多少台能够提供服务的机器，并放到list里面
        ServiceInstance instances(List<ServiceInstance> serviceInstances);
    }
通过继承该接口，实现自定义的本地负载均衡。
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

Feign提供了习惯的面向接口编程。

超时控制：
    OpenFeign默认等待一秒钟，超过后报错，OpenFeign默认支持Ribbon。
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
        添加新注解@EnableCircuitBreaker，启用熔断器。
    88业务类：
        同上
    88主启动类：
        添加注解@EnableHystrix，启用Hystrix。

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
    主启动中需要配置Bean，让后访问http://localhost:9001/hystrix。
    服务监控只能监控熔断，不能监控降级。
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
配置路由有两种方式，一种是通过yml配置文件，一种是通过代码中注入RouteLocator的Bean。

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
    继承两个接口：GlobalFilter ,Ordered，实现其方法，一个定义拦截规则，一个定义过滤器加载顺序。
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
    这里有一个坑，不知原因，IDEA中使用SSH的话会拉取不到文件，但是使用HTTP的话就可以。

    主启动类使用注解：@EnableConfigServer。

    读取配置规则：
        1、/{label}/{application}-{profile}.yml（推荐使用这种方式）：
            http://localhost:3344/master/config-dev.yml
        2、/{application}-{profile}.yml：
            http://localhost:3344/config-dev.yml
        3、/{application}-{profile}[/{label}]：
            http://localhost:3344/config/dev/master

cloud-config-client3355:
    新增了一个bootstap.yml文件：
        application.yml 是用户级的资源配置项。
        bootstrap.yml 是系统级的，优先级更高。
    要将Client模块下的application.yml文件改为bootstrap.yml，这是很关键的。
```
## Bus消息总线
#### 对应模块
```text
cloud-config-center3344
cloud-config-client3355
cloud-config-client3366
```
#### 笔记
```text
3344配置中心端添加消息总线支持，在pom和bootstrap中添加对应配置。
3355、3366同样添加对应的消息总线支持。

通过以上配置，可以实现config中提到的广播通知，可以实现全局广播刷新。

使用Bus还可以实现定点通知：
    只通知3355：curl -X POST "http://localhost:3344/actuator/bus-refresh/config-client:3355"
    对比全局通知：对比全局通知：curl -X POST "http://localhost:3344/actuator/bus-refresh"
```

## Stream消息驱动
#### 对应模块
```text
cloud-provider-stream-payment8801：作为生产者进行发消息模块
cloud-consumer-stream-order8802：作为消息接受模块
cloud-consumer-stream-order8803：作为消息接受模块
```
#### 笔记
```text
cloud-provider-stream-payment8801：
    在没有引入Stream消息驱动之前，标准的MQ：
        生产者/消费者之间靠消息（Message）媒介传递信息内容。
        消息必须走特定的通道（MessageChannel）。
        消息通道里的消息由其子接口SubscribableChannel，由MessageHandler消息处理器订阅。
    
    使用消息驱动，可以解决不同的消息中间件通信的问题。CloudStream使用Binder作为中间层，实现应用程序与消息中间件细节之间的隔离。
    
    Stream中的消息通信方式遵循了发布-订阅模式，在RabbitMQ就是Exchange；在kafka中就是Topic。
    标准执行流程：
        Binder：连接中间件，屏蔽差异。
        Channel：通道，是队列Queue的一种抽象，在消息通讯系统中就是实现存储和转发的媒介，通过对Channel对队列进行配置。
        Source和Sink：简单的可理解为参照对象是Spring Cloud Stream自身，从Stream发布消息就是输出，接受消息就是输入。
    
    补充说明：
        application中 binder: defaultRabbit 报错，但是没有错，是正确的。
        此外，端口port: 5672，是正确的，不是15672。
cloud-consumer-stream-order8802/8803：
    配置一个监听类ReceiveMessageListenerController，使用注解@EnableBinding(Sink.class)和@StreamListener(Sink.INPUT)监听消息通道的消息。

此时启动8802，8803会出现一个问题：消息的重复消费以及消息持久化的问题。
    使用group: A，添加分组。
    不同组之间，可以重复消费。而在同一组中属于竞争关系，每次只有一个能消费。
    使用分组后，可以自动持久化。
```

## Sleuth分布式请求链路追踪
#### 对应模块
```text
cloud-provider-payment8001
cloud-consumer-order80
```
#### 笔记
```text
cloud-provider-payment8001：
    pom中添加zipkin依赖，application中配置spring.zipkin和spring.sleuth。
    controller中配置方法输出。

cloud-consumer-order80：
    pom中同样添加zipkin依赖，application中配置spring.zipkin和spring.sleuth。
    controller中配置方法指向8001。
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
#### 笔记
```text
访问：http://localhost:8848/nacos或者http://localhost:8848/nacos/#/login
服务注册中心：
    四个注册中心对比
        AP：Eureka，Nacos
        CP：Zookeeper，Consul
    
    Nacos支持AP和CP模式切换，使用命令curl -X PUT '$NACOS_SERVER:8848/nacos/v1/ns/operator/switches?entry=serverMode&value=CP'
    
    何时选择何种模式？
    一般来说，
    如果不需要存储服务级别的信息，且服务实例是通过nacos-client注册，并且能保持心跳上报，那么就选择AP模式。
    当前主流的服务如Spring Cloud和Dubbo服务，都适合AP模式，AP模式为了服务的可能性而减弱了一致性，因此AP模式下只支持注册临时实例。
     
    如果需要在服务级别编辑或存储配置信息，那么CP是必须，K8S服务和DNS服务则适合CP模式。
    CP模式下则支持注册持久化实例，此时则是以Raft协议为集群运行模式，该模式下注册实例之前必须先注册服务，如果服务不存在，则会返回错误。

服务配置中心：

    主启动类中，通过SpringCloud原生注解@RefreshScope实现配置自动更新

    Nacos中的匹配规则：
        ${prefix}-${spring.profiles.active}.${spring.cloud.nacos.config.file-extension}
        prefix  默认为 spring.application.name 的值，也可以通过配置项 spring.cloud.nacos.config.prefix来配置。
        spring.profiles.active 即为当前环境对应的 profile，详情可以参考 Spring Boot文档。 注意：当 spring.profiles.active 为空时，对应的连接符 - 也将不存在，dataId 的拼接格式变成 ${prefix}.${file-extension}。
        file-exetension 为配置内容的数据格式，可以通过配置项 spring.cloud.nacos.config.file-extension 来配置。目前只支持 properties 和 yaml 类型。
    示例：
        Nacos控制台中添加配置列表，此时的Data ID：cloud-config-nacos-client-dev.yaml，配置格式yaml，信息如下：
            config:
                info: "config info for dev,from nacos config center,version = 1.0"
        访问：http://localhost:3377/nacos/config/info得到信息。

    Nacos还可以进行分类配置，有三种方案，DataID、Group、Namespace
        DataID：默认的方案，在application中配置 spring.profiles.active=dev来对应Nacos中的DataID。
        Group：使用分组，在Nacos控制台中可以定义分组，在bootstrap中配置   spring.cloud.nacos.config.group=DEFAULT_GROUP 来对应分组。
        Namespace：使用命名空间，在Nacos控制台中定义命名空间，在bootstrap中配置 spring.cloud.nacos.config.namespace=3711bcd7-0c56-445b-91e6-c6e4871561b0 来匹配命名空间。

Nacos集群和持久化配置
    Nacos默认使用嵌入式数据库derby实现数据存储，通过执行nacos\conf下的nacos-mysql.sql脚本和application.properties配置文件，可以进行数据库的切换。
```
## Alibaba Sentinel高可用流量管理框架
#### 对应模块
```text

```
## Alibaba Seata分布式事务中间件
#### 对应模块
```text

```