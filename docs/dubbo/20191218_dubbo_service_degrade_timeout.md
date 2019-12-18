## Dubbo怎么进行服务降级，超时重试和失败重试的？

#### 服务降级

dubbo服务consumer调用provider时，由于provider服务不可用导致consumer重试几次之后服务还是无法调通，这时consumer会直接走降级逻辑。

dubbo通过consumer配置mock的方式进行服务降级。mock配置有两种配置方式:

- 第一种是mock="return null"，接口调用不可用是直接返回为null，配置方式如下。

  ```xml
  <dubbo:reference id="testService" interface="com.test.service.TestService" timeout="3000" check="false" mock="return null"/>
  ```

- 第二种mock的值配置成boolean值，默认情况下是false。当配置为true时，则使用mock类名，结构：类名+mock后缀形式，当接口调用失败会调用mock实现，mock实现需要保证有无参构造方法。

  ```xml
<dubbo:reference id="testService" interface="com.test.service.TestService" timeout="3000" check="false" mock="true"/>
  ```
  
  在com.test.service.TestService接口相同路径下定义一个TestServiceMock类并实现TestService接口，然后进行降级逻辑处理。
  

mock调用过程：

- 查看参数mock的值，如果未配置或false，则直接调用invoker；
- 如mock已force开头，则强制走mock；否则先调用provider服务，调用失败或超时则走Mock；
- 调用mock是没有发现mock服务，new一个MockInvoker，调用MockInvoker的服务。



#### 超时重试或失败重试

dubbo调用接口超时或失败重试配置方式，通过consumer的<dubbo:reference />中参数retries="3"表示失败或超时重试3次，timeout=“1000”表示多长时间超时；

```xml
<dubbo:reference id="xxxx" interface="xx" check="true" async="false" retries="3" timeout="1000"/>
```

