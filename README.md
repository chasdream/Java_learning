# JAVA学习总结

## JAVA基础篇



## JAVA集合篇

- #### Java集合继承关系图

- #### ArrayList源码解析

- #### CopyOnWriteArrayList源码解析

- #### LinkedList源码解析

- #### HashMap源码解析

- #### ConcurrentHashMap源码解析



## IO/NIO篇

- #### BIO、NIO、AIO 各自有什么特点？



## 并发编程篇

- #### 线程生命周期，Java中线程的创建方式有哪几种？

- #### Java线程池的实现原理，Java提供了哪些线程池的创建方式？

- #### Java线程池的拒绝策略，线程池提供的队列有哪些？

- #### sychronized和ReentrantLock的工作原理及区别

- #### volatile保证了线程池的什么性质？为什么可以保证？

- #### Java中锁机制包括哪几种锁类型？

- #### 什么事CAS？CAS的过程是怎样的？Java API中有哪些类利用CAS原理？

- #### AQS原理



## 网络篇

- #### 什么事OSI七层模型？

- #### TCP/IP协议，什么事三次握手四次挥手？为什么需要四次挥手？

- #### HTTP协议的格式？常见的HTTP返回码及含义

- #### HTTP/HTTPS请求流程，两者有什么区别



## JVM篇

- #### JVM运行时数据区域，哪些情况会导致oom？

- #### Java中对象时如何创建、布局及访问定位的？Java中有哪些引用类型？

- #### [判断对象是否存活算法有哪些？JVM使用的是哪种？可达性分析算法是如何判断对象可以进行回收？哪些对象可以作为GC Roots?](.//docs/jvm/20191229_jvm_object_alive.md)

- #### 方法区(永久代)是否需要进行垃圾回收？为什么永久代垃圾回收效率较低？

- #### 如何判断JVM内存泄露？如何定位Full GC发生的原因？

- #### 什么情况下会发生minor GC和full GC？JVM中发生一次full GC的过程？

- #### 为什么通常情况下发生full GC会伴随着一次minor GC？什么情况下full GC不需要先进行minor GC？为什么full GC的效率要比minor GC低？

- #### JVM垃圾回收器有哪几类及其工作原理？

- #### JVM对象创建过程中内存分配策略

- #### JVM性能调优过程

- #### 类在什么时候会被加载？JVM类加载过程是怎么进行的？

- #### 类加载器的种类，什么是双亲委派模型？什么情况下会破坏双亲委派模型？



## Dubbo篇

- #### [Dubbo框架原理，consumer和provider之间是如何调用的？rpc请求流程是怎样的？注册中心如果挂了还可以继续通信吗？]()

- #### Dubbo支持哪些协议？推荐使用哪种？

- #### Dubbo内置了哪几种服务容器？是否需要web容器？

- #### Dubbo支持的注册中心有哪几类型？默认是哪种类型？

- #### [Dubbo是如何进行服务治理的？]()

- #### [Dubbo怎么进行服务降级，超时重试和失败重试的？](./docs/dubbo/20191218_dubbo_service_degrade_timeout.md)

- #### [Dubbo负载均衡策略、容错策略、动态代理策略]()

- #### [Dubbo RPC服务调用与http服务调用有什么区别？哪个效率(性能)更高？]()



## zookeeper篇

- #### [什么是zookeeper？zookeeper节点(znode)有几种类型？zookeeper架构原理](./docs/zookeeper/20191217_zookeeper_introduce.md)

- #### [zookeeper应用场景有哪些？]()


- #### [什么是ZAB协议？](./docs/zookeeper/20191218_zookeeper_zab_protocol.md)


- #### [zookeeper选举流程，投票选举leader为什么需要超过半数？zkserver机器部署成奇数台(2n+1)?](./docs/zookeeper/20191218_zookeeper_zab_vote.md)

- #### [fastLeaderElection选举算法实现原理](./docs/zookeeper/20191229_zookeeper_fastLeaderElection.md)

- #### [zookeeper如何保证一致性？]()



## 消息队列篇

- #### 使用消息队列(MQ)如何保证数据一致性？

- #### 使用消息队列(MQ)如何保证消息不丢失？如kafka是如何保证的？

- #### 使用消息队列(MQ)如何保证消息的顺序？

- #### 使用消息队列(MQ)如何解决消息重复消费的问题？

- #### 消息队列堆积严重该如何处理？消息队列满了又该如何处理？

- #### kafka集群原理

- #### kafka消息存储方式



## redis篇

- #### redis数据结构有哪几种？每种数据结构存储的最大容量是多少？

- #### redis字符串的存储结构

- #### redis持久化机制是什么样的？以及不同持久化方式的优缺点？

- #### redis常见的性能及解决方案

- #### redis过期键删除策略？redis回收策略(淘汰策略)?

- #### redis主从复制是如何进行的？redis同步机制的原理？

- #### 什么是哨兵模式？redis集群原理，什么事hash槽的概念？什么情况下会导致整个集群不可用？

- #### redis支持的Java客户端有哪些？jedis和redisson对比有哪些有缺点？

- #### 什么是pipeline？使用pipeline有什么优缺点？

- #### Redis缓存穿透、雪崩原因及解决方案？



## 框架篇

- #### Spring IOC原理、AOP原理

- #### spring Bean加载方式和加载过程

- #### 什么是springMVC？常用的springMVC注解有哪些？



## 数据库篇
- #### mysql索引原理，为什么使用B+树，而不是使用其他树数据结构？例如红黑树等

- #### mysql事务，事务的隔离级别有哪几种？mysql默认的隔离级别是哪一种？

- #### [mysql锁机制MVCC原理](./docs/database/mysql_mvcc.md)

- #### mysql锁算法

- #### mysql存储引擎

- #### 数据库表设计时应注意哪些事项？sql查询优化

- #### 如何进行分表分库？有哪些中间件？不同中间件的原理及优缺点？




## 运维篇(nginx/linux/tomcat/docker/jenkins)

- #### Nginx原理，服务入口Nginx是一台还是多台？

- #### Nginx负载均衡策略有哪些？如何配置？



## 分布式篇

- #### 如何保证服务的高可用性的？

- #### [什么是2pc、3pc、tcc协议？]()

- #### [A系统调用B系统，如何保证系统之间数据的一致性？如果不采用分布式事务又如何保证？（注：两个系统数据库不同）]()



## 设计模式篇

- #### 设计模式设计原则包括哪些？

- #### 代理设计模式、策略设计模式

- #### 责任链设计模式、迭代器设计模式

- #### 单列设计模式、工厂设计模式

- #### 装饰器设计模式、适配器设计模式

- #### 组合设计模式、原型设计模式

- #### 桥接设计模式、观察者设计模式

- #### 建造者设计模式、结构型设计模式

- #### 模版方法设计模式、命令设计模式

- #### 状态设计模式、中介者设计模式

- #### 访问者设计模式、备忘录设计模式

- #### 享元设计模式、外观设计模式

- #### 解释器设计模式



## 数据结构和算法篇

- #### [一个数组中只有两个数字是出现一次，其他所有数字都出现了两次，找出这两个数字。要求：空间复杂度o(1)，时间复杂度o(n)]()

- #### [找出二叉树上任意两个结点的最近共同父结点]()



## 其他

- #### maven生命周期及每一个生命周期的作用

- #### maven远程仓库、本地仓库及镜像的概率和区别







