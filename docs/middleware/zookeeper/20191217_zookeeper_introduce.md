#### 什么是zookeeper？zookeeper节点(znode)有几种类型？zookeeper应用场景有哪些？zookeeper集群架构。

##### 1.什么是zookeeper？

zookeeper类似于文件系统的数据结构，为解决应用中经常遇到的一些数据管理问题，主要用于统一命名服务、状态同步服务、集群管理、分布式应用配置项等。zookeeper相当于一个文件系统+监听通知机制。



##### 2.znode有几种类型？每种类型的主要应用场景是什么？

znode节点四种类型：

- 持久化节点(PERSISTENT)：客户端与zkserver断开之后，节点依旧存在；
- 持久化顺序编号节点(PERSISTENT_SEQUENTIAL)：客户端与zkserver断开之后，节点依旧存在，只是zkserver会给该节点名称进行顺序编号；
- 临时节点(EPHEMERAL)：客户端与zkserver断开之后，节点会被删除；
- 临时顺序编号节点(EPHEMERAL_SEQUENTIAL)：客户端与zkserver断开之后，节点会被删除，只是zkserver会给该节点名称进行顺序编号；



##### 3.zookeeper应用场景有哪些？

zk主要应用场景有命名服务、分布式锁、分布式协调、元数据/配置信息管理、队列管理、高可用等；



##### 4.zookeeper集群

zk集群是基于主从复制，包含leader、follower、observer三个角色。