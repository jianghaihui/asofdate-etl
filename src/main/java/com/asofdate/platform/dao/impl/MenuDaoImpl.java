package com.asofdate.platform.dao.impl;

import com.asofdate.platform.dao.MenuDao;
import com.asofdate.platform.model.MenuModel;
import com.asofdate.sql.SqlDefine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by hzwy23 on 2017/6/18.
 */
@Repository
public class MenuDaoImpl implements MenuDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<MenuModel> findAll() {
        RowMapper<MenuModel> rowMapper = new BeanPropertyRowMapper<>(MenuModel.class);
        return jdbcTemplate.query(SqlDefine.sys_rdbms_071, rowMapper);
    }
}
