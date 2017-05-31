package com.asofdate.dispatch.dao.impl;

import com.asofdate.dispatch.dao.GroupArgumentDao;
import com.asofdate.dispatch.model.GroupArgumentModel;
import com.asofdate.sql.SqlDefine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by hzwy23 on 2017/5/30.
 */
@Repository
public class GroupArgumentDaoImpl implements GroupArgumentDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<GroupArgumentModel> findAll(String domainId) {
        RowMapper<GroupArgumentModel> rowMapper = new BeanPropertyRowMapper<>(GroupArgumentModel.class);
        List<GroupArgumentModel> list = jdbcTemplate.query(SqlDefine.sys_rdbms_114,rowMapper,domainId);
        return list;
    }
}
