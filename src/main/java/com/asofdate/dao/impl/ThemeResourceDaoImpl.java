package com.asofdate.dao.impl;

import com.asofdate.dao.ThemeResourceDao;
import com.asofdate.model.ThemeResourceModel;
import com.asofdate.sql.SqlDefine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by hzwy23 on 2017/5/17.
 */
@Repository
public class ThemeResourceDaoImpl implements ThemeResourceDao{
    @Autowired
    public JdbcTemplate jdbcTemplate;

    @Override
    public List findByThemeId(String themeId) {
        RowMapper<ThemeResourceModel> rowMapper = new BeanPropertyRowMapper<ThemeResourceModel>(ThemeResourceModel.class);
        List<ThemeResourceModel> list = jdbcTemplate.query(SqlDefine.sys_rdbms_101,rowMapper,themeId);
        return list;
    }
}
