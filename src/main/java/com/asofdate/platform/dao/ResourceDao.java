package com.asofdate.platform.dao;

import com.asofdate.platform.model.ResourceModel;

import java.util.List;

/**
 * Created by hzwy23 on 2017/5/17.
 */
public interface ResourceDao {
    List findAll();

    List findSubByUpId(String resUpId);

}
