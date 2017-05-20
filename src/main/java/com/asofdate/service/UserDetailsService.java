package com.asofdate.service;

import com.asofdate.model.UserDetailsModel;

/**
 * Created by hzwy23 on 2017/5/18.
 */
public interface UserDetailsService {
    public UserDetailsModel findById(String userId);
}
