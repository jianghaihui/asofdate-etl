package com.asofdate.service.impl;

import com.asofdate.dao.UserDetailsDao;
import com.asofdate.model.UserDetailsModel;
import com.asofdate.service.UserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by hzwy23 on 2017/5/18.
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    public UserDetailsDao userDetailsDao;

    @Override
    public UserDetailsModel findById(String userId) {
        List<UserDetailsModel> list = userDetailsDao.findById(userId);
        if (list != null && list.size() > 0){
            return list.get(0);
        }
        return null;
    }
}
