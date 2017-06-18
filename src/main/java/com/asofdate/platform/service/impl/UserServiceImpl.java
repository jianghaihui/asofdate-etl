package com.asofdate.platform.service.impl;

import com.asofdate.platform.dao.UserDao;
import com.asofdate.platform.model.UserModel;
import com.asofdate.platform.service.UserService;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by hzwy23 on 2017/6/18.
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;

    @Override
    public List<UserModel> findAll(String domainid) {
        return userDao.findAll(domainid);
    }

    @Override
    public List<UserModel> findAll(String domainId, String orgId) {
        return null;
    }

    @Override
    public List<UserModel> findAll(String domainId, String orgId, String statusCd) {
        return null;
    }

    @Override
    public int add(UserModel userModel) {
        return 0;
    }

    @Override
    public int delete(JSONArray jsonArray) {
        return 0;
    }

    @Override
    public int update(UserModel userModel) {
        return 0;
    }

    @Override
    public int changePassword(JSONObject jsonObject) {
        return 0;
    }

    @Override
    public int changeStatus(String userId, String status) {
        return 0;
    }
}
