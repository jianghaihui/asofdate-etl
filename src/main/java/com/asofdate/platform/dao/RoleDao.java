package com.asofdate.platform.dao;

import com.asofdate.platform.model.RoleModel;
import org.json.JSONArray;

import java.util.List;

/**
 * Created by hzwy23 on 2017/6/18.
 */
public interface RoleDao {
    List<RoleModel> findAll(String domainId);

    RoleModel getDetails(String roleId);

    int add(RoleModel roleModel);

    int delete(JSONArray jsonArray);

    int update(RoleModel roleModel);
}
