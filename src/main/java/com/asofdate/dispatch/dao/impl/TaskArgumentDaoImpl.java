package com.asofdate.dispatch.dao.impl;

import com.asofdate.dispatch.dao.TaskArgumentDao;
import com.asofdate.dispatch.model.TaskArgumentModel;
import com.asofdate.sql.SqlDefine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by hzwy23 on 2017/5/24.
 */
@Repository
public class TaskArgumentDaoImpl implements TaskArgumentDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List findAll(String domainId) {
        RowMapper<TaskArgumentModel> rowMapper = new BeanPropertyRowMapper<TaskArgumentModel>(TaskArgumentModel.class);
        List list = jdbcTemplate.query(SqlDefine.sys_rdbms_110, rowMapper, domainId);
        return list;
    }
}
