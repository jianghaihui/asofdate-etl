package com.asofdate.dao;

import java.util.List;

/**
 * Created by hzwy23 on 2017/5/18.
 */
public interface UserDetailsDao {
    public List findById(String userId);
}
