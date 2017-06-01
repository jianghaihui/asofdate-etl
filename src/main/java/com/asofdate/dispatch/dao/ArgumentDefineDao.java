package com.asofdate.dispatch.dao;

import com.asofdate.dispatch.model.ArgumentDefineModel;

import java.util.List;

/**
 * Created by hzwy23 on 2017/5/24.
 */
public interface ArgumentDefineDao {
    /*
    * 查询全部任务参数
    * */
    List findAll(String domainId);

    /*
    * 新增任务
    * */
    int add(ArgumentDefineModel m);

    /*
    * 删除任务
    * */
    String delete(List<ArgumentDefineModel> m);

    /*
    * 更新任务
    * */
    int update(ArgumentDefineModel m);
}
