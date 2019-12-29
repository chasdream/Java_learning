## zookeeper选举流程，投票选举leader为什么需要超过半数？zkserver机器部署成奇数台(2n+1)?

#### 选举机制中概念

- myId: zk服务器的ID，即每个zk服务器都需要在数据文件夹下创建一个myid的文件，文件包含该服务器在整个zk集群下的唯一ID。myid文件中内容必须与对应serverID一一对应。文件内容如下：

  ```
  server.1=127.0.0.1:2888:3888
  server.2=127.0.0.10:2888:3888
  server.3=127.0.0.20:2888:3888
  ```

  

- zxId: ZAB协议事务ID，用于标识一次更新操作的proposal(提议) ID，为保证顺序zxid必须是单调递增的。zxid是一个64位的数字，高32位表示leader周期的epoch编号，低32位是一个单调递增的计数器。

- epoch: 投票的次数，同一轮投票过程中epoch的值是相同的。server每次投票完成epoch会增加，然后和接收到的其他server返回的投票信息中值进行比较，根据不同的值做出不同的判断。



#### 选举过程中服务器状态

- LOOKING: 不确定leader的状态，所有的server正在处于leader选举的过程；
- FOLLOWING: 跟随状态，表明当前服务器被当选follower，并且知道leader是谁；
- LEADING: 领导者状态，表明当前服务器被当选为leader，它维护与follower之间的心跳；
- OBSERVING: 观察者状态，当前服务器被当选为observer



#### zookeeper选举流程

zk的leader选举是基于TCP的fastLeaderElection算法进行的，每个server选票结果包含的信息(leader myid, zxid, epoch)，leader选举流程：

1. 开始选举阶段，每个server会读取自身的zxid;
2. 每个server第一轮会把选票投给自己，然后把选票信息广播给其他server；
3. server会接收其他server的选票信息并记录进自己的投票箱中；
4. server接收到其他server的投票信息后，首先判断epoch的大小，若接收到epoch大于自己的epoch，则更新自己的epoch值，并清空本轮epoch收集到的选票信息，然后判断是否需要更新当前自己选出的myid。判断规则：自己保存的zxid最大值和leader serverId来进行判断，首先判断zxid，zxid大的胜出；在判断myid，myid大的胜出；然后更新自己最新的选票结果并将结果广播给其他server；
5. 接收到的epoch小于自己的epoch，说明接收到的epoch处于相对落后的epoch中，只需将自己的选票结果广播给其他server；
6. 接收到的epoch等于自己的epoch，再根据第4步中判断规则进行选举，然后将自己的最新选票结果广播给其他server；
7. 统计选票结果，每轮投票服务器都会统计选票有超过半数的机器投票结果是投给相同的server，则确认已经选出了leader；
8. 每个server根据最终的选举结果更新自己的服务器状态，当选为leader的server状态更新为LEADING，其他参与leader选举的server更新状态为FOLLOWING；



当leader宕机之后，整个集群将暂停对外提供服务，将进入新一轮的投票选举。首先会变更服务器状态，当leader宕机之后，所有的非observer服务器将自己的状态变更为LOOKING，第一轮投票中每个服务器会把选票投给自己并把选票信息广播给其他server，并按照启动时的选举规则进行选举。



#### 投票选举leader为什么需要过半机制？什么是脑裂？

脑裂是指集群中如果不考虑超过半数的选举机制可能会出现了多个leader，例如：zk集群中有7台服务器，1个leader服务器和6个follower服务器，leader宕机之后，重新进入选举，不考虑超过半数选举机制，可能会出现3台服务器选举A服务器作为leader，另外3台选举B服务器作为leader，导致集群中出现了双leader。此时，集群中就出现了两个leader对外提供服务，如果出现断网等导致两个leader的集群断开之后，会导致两个集群中的数据不一致问题。

为防止集群中出现脑裂的现象出现，因此leader过半机制必须是大于(而不是大于等于)一半的机器数量。



#### zkserver机器部署成奇数台(2n+1)？

节约资源，因为zk集群只有在超过半数的机器正常时才能正常工作，所有2n+1台机器和2n+2台机器的集群容错率是相同的。例如：假设zk集群中有3台机器，当集群中有1台服务器宕机，集群可以正常工作，宕机2台集群因正常工作的机器没有超过半数，所有集群将无法正常工作。假设集群中有4台机器，1台宕机能够正常工作，宕机2台因正常工作机器没有超过半数，所有集群无法正常工作。所以集群中3台和4台机器的容错率是相同的。



[fastLeaderElection选举算法实现原理](./20191229_zookeeper_fastLeaderElection.md)







