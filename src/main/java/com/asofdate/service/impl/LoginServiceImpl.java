package com.asofdate.service.impl;

import com.asofdate.dao.UserCheckDao;
import com.asofdate.model.UserLoginModel;
import com.asofdate.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by hzwy23 on 2017/5/16.
 */
@Service
public class LoginServiceImpl implements LoginService{

    @Autowired
    private UserCheckDao userCheckDao;

    private UserLoginModel setEmpty(UserLoginModel userCheck){
        userCheck.setFlag(false);
        userCheck.setMessage("用户不存在");
        userCheck.setPassword("");
        return userCheck;
    }

    @Override
    public UserLoginModel loginValidator(String userId,String password) {
        List<UserLoginModel> list = userCheckDao.findByUserId(userId);
        UserLoginModel userCheck = new UserLoginModel();
        if (list.size() == 0) {
            return setEmpty(userCheck);
        }

        userCheck =  list.get(0);
        if (list.size() > 1){
            userCheck.setFlag(false);
            userCheck.setMessage("存在多个用户,用户管理出现异常情况,禁止登陆,请联系管理员");
            userCheck.setPassword("");
            return userCheck;
        }

        if (userCheck.getContinueErrorCnt() > 7) {
            userCheck.setMessage("账号连续错误登陆超过7次,已经被锁定,请联系管理员");
            userCheck.setFlag(false);
            userCheck.setPassword("");
            return userCheck;
        }

        if (!userCheck.getStatusId().equals("0")){
            userCheck.setMessage("账号被锁定,请联系管理员");
            userCheck.setPassword("");
            userCheck.setFlag(false);
            return userCheck;
        }

        if (password.equals(userCheck.getPassword())){
            userCheck.setFlag(true);
        } else {
            userCheck.setFlag(false);
            userCheck.setMessage("密码错误,请重新输入");
        }
        userCheck.setPassword("");
        return userCheck;
    }

    @Override
    public UserLoginModel findByUserId(String userId) {
        List<UserLoginModel> list = userCheckDao.findByUserId(userId);
        if (list.size() == 0) {
            UserLoginModel userCheck = new UserLoginModel();
            return setEmpty(userCheck);
        }
        return list.get(0);
    }
}
