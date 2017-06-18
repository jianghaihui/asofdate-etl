package com.asofdate.platform.dao.impl;

import com.asofdate.platform.dao.RoleDao;
import com.asofdate.platform.model.RoleModel;
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
public class RoleDaoImpl implements RoleDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<RoleModel> findAll(String domainId) {
        RowMapper<RoleModel> rowMapper = new BeanPropertyRowMapper<>(RoleModel.class);
        return jdbcTemplate.query(SqlDefine.sys_rdbms_028,rowMapper,domainId);
    }
}
