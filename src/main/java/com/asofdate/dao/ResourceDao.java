package com.asofdate.dao;

import java.util.List;

/**
 * Created by hzwy23 on 2017/5/17.
 */
public interface ResourceDao {
    public List findAll();
    public List findSubByUpId(String resUpId);
}
