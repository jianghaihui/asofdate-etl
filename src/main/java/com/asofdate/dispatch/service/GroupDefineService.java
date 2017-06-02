package com.asofdate.dispatch.service;

import com.asofdate.dispatch.model.GroupDefineModel;

import java.util.List;

/**
 * Created by hzwy23 on 2017/6/1.
 */
public interface GroupDefineService {
    List<GroupDefineModel> findAll(String domainId);

    int update(GroupDefineModel m);

    String delete(List<GroupDefineModel> m);

    int add(GroupDefineModel m);
}
