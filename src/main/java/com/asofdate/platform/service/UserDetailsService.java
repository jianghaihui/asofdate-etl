package com.asofdate.platform.service;

import com.asofdate.platform.model.UserDetailsModel;

/**
 * Created by hzwy23 on 2017/5/18.
 */
public interface UserDetailsService {
    UserDetailsModel findById(String userId);
}
