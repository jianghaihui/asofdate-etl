package com.asofdate.platform.dao;

import com.asofdate.platform.model.ResourceModel;
import com.asofdate.platform.model.RoleResourceModel;

import java.util.List;

/**
 * Created by hzwy23 on 2017/6/20.
 */
public interface RoleResourceDao {
    List<RoleResourceModel> findAll(String roleId);

    List<ResourceModel> getOther(String roleId);

    int revoke(String roleId, String resId);

    int auth(String roleId, String resId);
}
