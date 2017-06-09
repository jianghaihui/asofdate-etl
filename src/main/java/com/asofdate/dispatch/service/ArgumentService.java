package com.asofdate.dispatch.service;

import com.asofdate.dispatch.model.ArgumentDefineModel;
import com.asofdate.dispatch.model.TaskArgumentModel;

import java.util.List;

/**
 * Created by hzwy23 on 2017/5/28.
 * 参数服务接口
 */
public interface ArgumentService {

    // 初始化参数信息
    // 在使用@Autowired自动注入这个类得实例后,需要手工调用这个方法初始化对象中的变量属性
    void afterPropertySet(String domainId, String batchId);

    // 参数 id 是 dispatch_group_task_relation表中的id字段
    // 根据id值,查询这个任务所有的参数信息
    // 只有执行了上边的afterPropertySet函数, 初始化对象的属性后,
    // queryArgument方法才会返回正确的值,否则为null
    List<TaskArgumentModel> queryArgument(String id);


    // 查询指定域中的所有参数信息
    // domainID 只域编码
    List<ArgumentDefineModel> findAll(String domainID);

    // 向参数定义表中新增参数
    int add(ArgumentDefineModel m);

    // 删除已经定义的参数信息
    String delete(List<ArgumentDefineModel> m);

    // 更新参数信息
    int update(ArgumentDefineModel m);
}
