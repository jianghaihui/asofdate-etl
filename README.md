## 项目状态
开发中...

有愿意加入项目的，请加QQ：309810957

主要功能目标:
![系统主界面](./HomePage.png)

![ETL调度管理界面](./dispatch_index.png)

## asofdate-etl项目简介
这是一个企业级ETL调度系统, 在成熟的spring框架基础上,实现ETL调度服务. 权限管理部分,采用[asofdate项目](https://github.com/hzwy23/asofdate),asofdate项目采用golang开发,asofdate-etl采用java重写了asofdate后台api,由于两个项目都是采用jwt加密用户连接信息,加密方式相同,所有,两个项目可以共同使用同一套数据模型.

## 引用组件
1. spring-boot spring社区中一款优秀的快速开发框架
2. spring-batch 一款非常优秀的批处理框架
3. spring-security 用户认证管理
4. quartz 调度器

## 主要功能点介绍

**调度服务主要业务流程**
```
	批次     任务组     job信息
	batch -- group1 -- job1
	      ------------ job2
	      -- group2 -- job3
    	  -- group3 -- job4
    	  ------------ job5
```

ETL调度系统,最小的调度单元job，job可以是下边几种类型:
```
> 数据库存储过程 (已实现，单元测试完成，通过)
> shell脚本 （已实现，单元测试完成，通过）
> CMD批次处理脚本 （已实现，单元测试完成，通过）
> 二进制可执行程序 （已实现，未测试）
> JAR可执行文件 （已实现，未测试）
```
在job之上是任务组, 任务组是对任务的一个打包处理, 同一个任务,可以被任务组包含多次. 任务组内对每个job设置上下依赖关系,ETL调度引擎,会根据依赖关系执行job.

在任务组之上是批次, 同一任务组,在一个批次中可以配置多次, 调度引擎,会根据任务组的依赖关系,解析依赖,执行任务组中的job.

当一个批次被运行起来时, 会首先解析批次中的任务组依赖关系, 根据依赖关系, 找到最顶层的任务组,如果顶层是多个任务组,则并发启动多个任务组.
任务组被启动后,调度引擎会解析任务组中的任务依赖关系,执行任务组中最顶层的job,当上一个job执行完成后,将会自动触发下一个job,
当一个任务组中的job全部执行完成后,任务组状态被设置成"已完成", 调度器将会解析下一个可以运行的任务组. 按照这个执行逻辑,直到所有的任务组中任务执行完成.
当所有任务执行完成后,调度器退出调度.

注意:一旦出现某个job执行失败,调度器将会停止调度这个批次. 其他正常运行你的批次,不受影响.

**剩余未开发功能点:**
1. 定时任务cron （开发中...）
2. 批次优先级控制 (开发中...)
3. 最大并发数控制 (开发中...)
4. 运行错误通知服务 (开发中...)

## 交流方式
e-mail: hzwy23@163.com