package com.asofdate.platform.dao.impl;

import com.asofdate.platform.dao.UserThemeDao;
import com.asofdate.sql.SqlDefine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * Created by hzwy23 on 2017/5/17.
 */
@Repository
public class UserThemeDaoImpl implements UserThemeDao {
    @Autowired
    public JdbcTemplate jdbcTemplate;

    @Override
    public String findById(String userId) {
        String themeId = jdbcTemplate.queryForObject(SqlDefine.sys_rdbms_103, String.class, userId);
        return themeId;
    }
}
