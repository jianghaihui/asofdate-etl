package com.asofdate.platform.service.impl;

import com.asofdate.platform.dao.UserCheckDao;
import com.asofdate.platform.model.UserLoginModel;
import com.asofdate.platform.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by hzwy23 on 2017/5/16.
 */
@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private UserCheckDao userCheckDao;

    private UserLoginModel setEmpty(UserLoginModel userCheck,String userId) {
        userCheck.setFlag(false);
        userCheck.setUsername(userId);
        userCheck.setMessage("The user does not exist");
        userCheck.setPassword("");
        return userCheck;
    }

    @Override
    public UserLoginModel loginValidator(String userId, String password) {
        List<UserLoginModel> list = userCheckDao.findByUserId(userId);
        UserLoginModel userCheck = new UserLoginModel();
        if (list.size() == 0) {
            userCheck.setRetCode("401");
            return setEmpty(userCheck,userId);
        }

        userCheck = list.get(0);
        if (list.size() > 1) {
            userCheck.setRetCode("402");
            userCheck.setFlag(false);
            userCheck.setUsername(userId);
            userCheck.setMessage("There are multiple users, user management has abnormal situation, no login, please contact management");
            userCheck.setPassword("");
            return userCheck;
        }

        if (userCheck.getContinueErrorCnt() > 7) {
            userCheck.setRetCode("403");
            userCheck.setUsername(userId);
            userCheck.setMessage("The id has been logged more than 7 times and has been locked. Please contact the administrator");
            userCheck.setFlag(false);
            userCheck.setPassword("");
            return userCheck;
        }

        if (!userCheck.getStatusId().equals("0")) {
            userCheck.setRetCode("406");
            userCheck.setUsername(userId);
            userCheck.setMessage("The account is locked, please contact the administrator");
            userCheck.setPassword("");
            userCheck.setFlag(false);
            return userCheck;
        }

        if (password.equals(userCheck.getPassword())) {
            userCheck.setFlag(true);
        } else {
            userCheck.setRetCode("405");
            userCheck.setFlag(false);
            userCheck.setMessage("Password error, please re-enter");
        }
        userCheck.setUsername(userId);
        userCheck.setPassword("");
        return userCheck;
    }

    @Override
    public UserLoginModel findByUserId(String userId) {
        List<UserLoginModel> list = userCheckDao.findByUserId(userId);
        if (list.size() == 0) {
            UserLoginModel userCheck = new UserLoginModel();
            return setEmpty(userCheck,userId);
        }
        return list.get(0);
    }
}
