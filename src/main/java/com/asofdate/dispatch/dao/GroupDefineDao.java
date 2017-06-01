package com.asofdate.dispatch.dao;

import com.asofdate.dispatch.model.GroupDefineModel;

import java.util.List;

/**
 * Created by hzwy23 on 2017/5/24.
 */
public interface GroupDefineDao {
    List findAll(String domainId);
    int update(GroupDefineModel m);
    String delete(List<GroupDefineModel> m);
    int add(GroupDefineModel m);
}
