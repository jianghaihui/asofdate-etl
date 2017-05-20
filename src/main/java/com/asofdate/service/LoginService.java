package com.asofdate.service;

import com.asofdate.model.UserLoginModel;

/**
 * Created by hzwy23 on 2017/5/16.
 */
public interface LoginService {
    public UserLoginModel loginValidator(String user_id,String password);
    public UserLoginModel findByUserId(String userId);
}
