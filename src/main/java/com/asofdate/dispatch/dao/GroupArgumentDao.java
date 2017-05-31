package com.asofdate.dispatch.dao;

import com.asofdate.dispatch.model.GroupArgumentModel;

import java.util.List;

/**
 * Created by hzwy23 on 2017/5/30.
 */
public interface GroupArgumentDao {
    List<GroupArgumentModel> findAll(String domainId);
}
