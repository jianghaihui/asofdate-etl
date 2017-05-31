## 项目状态
开发中...

有愿意加入项目的，请加QQ：309810957

## asofdate-etl项目简介
这是一个企业级ETL调度系统, 在成熟的spring框架基础上,实现ETL调度服务. 权限管理部分,采用[asofdate项目](https://github.com/hzwy23/asofdate),asofdate项目采用golang开发,asofdate-etl采用java重写了asofdate后台api,由于两个项目都是采用jwt加密用户连接信息,加密方式相同,所有,两个项目可以共同使用同一套数据模型.

## 引用组件
1. spring-boot spring社区中一款优秀的快速开发框架
2. spring-batch 一款非常优秀的批处理框架
3. spring-security 用户认证管理
4. quartz 调度器

## 主要功能点介绍

1. 调度服务主要业务流程
```
	批次     任务组     job信息
	batch -- group1 -- job1
    	  ------------ job2
      	  -- group2 -- job3
          -- group3 -- job4
          ------------ job5
```

2. job支持类型:
```
> 存储过程
> shell脚本
> perl脚本
> 可执行程序
```

3. 定时任务cron

4. 批次优先级控制

5. 最大并发数控制

6. 运行错误通知服务

## 交流方式
e-mail: hzwy23@163.com