package com.asofdate.platform.dao;

import java.util.List;
import java.util.Set;

/**
 * Created by hzwy23 on 2017/6/1.
 */
public interface UserResourceDao {
    Set<String> findAll(String userId);
}
