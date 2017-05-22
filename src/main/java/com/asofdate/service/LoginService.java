package com.asofdate.service;

import com.asofdate.model.UserLoginModel;

/**
 * Created by hzwy23 on 2017/5/16.
 */
public interface LoginService {
    UserLoginModel loginValidator(String user_id,String password);
    UserLoginModel findByUserId(String userId);
}
