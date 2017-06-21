package com.asofdate.platform.service;

import com.asofdate.platform.model.RoleModel;
import org.json.JSONArray;

import java.util.List;

/**
 * Created by hzwy23 on 2017/6/18.
 */
public interface RoleService {
    List<RoleModel> findAll(String domainId);

    List<RoleModel> getOther(String userId);

    List<RoleModel> getOwner(String userId);

    RoleModel getDetails(String roleId);

    int auth(JSONArray jsonArray,String modifyUserId);

    int revoke(JSONArray jsonArray);

    int batchAuth(JSONArray jsonArray,String modifyUserId);

    int add(RoleModel roleModel);

    int delete(JSONArray jsonArray);

    int update(RoleModel roleModel);
}
