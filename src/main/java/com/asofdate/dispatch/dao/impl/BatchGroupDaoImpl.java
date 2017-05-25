package com.asofdate.dispatch.dao.impl;

import com.asofdate.dispatch.dao.BatchGroupDao;
import com.asofdate.dispatch.model.BatchGroupModel;
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
public class BatchGroupDaoImpl implements BatchGroupDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List findAll(String domainId) {
        RowMapper<BatchGroupModel> rowMapper = new BeanPropertyRowMapper<>(BatchGroupModel.class);
        List<BatchGroupModel> list = jdbcTemplate.query(SqlDefine.sys_rdbms_106, rowMapper, domainId);
        return list;
    }
}
