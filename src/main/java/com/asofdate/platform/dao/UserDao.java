package com.asofdate.platform.dao;

import com.asofdate.platform.model.UserModel;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by hzwy23 on 2017/6/18.
 */
public interface UserDao {
    List<UserModel> findAll(String domainid);
    List<UserModel> findAll(String domainId,String orgId);
    List<UserModel> findAll(String domainId,String orgId,String statusCd);

    int add(UserModel userModel);
    int delete(JSONArray jsonArray);
    int update(UserModel userModel);
    int changePassword(JSONObject jsonObject);
    int changeStatus(String userId,String status);
}
