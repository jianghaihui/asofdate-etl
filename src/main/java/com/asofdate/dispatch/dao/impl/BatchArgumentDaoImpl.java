package com.asofdate.dispatch.dao.impl;

import com.asofdate.dispatch.dao.BatchArgumentDao;
import com.asofdate.dispatch.model.BatchArgumentModel;
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
public class BatchArgumentDaoImpl implements BatchArgumentDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List findAll(String domainId) {
        RowMapper<BatchArgumentModel> rowMapper = new BeanPropertyRowMapper<BatchArgumentModel>(BatchArgumentModel.class);
        List list = jdbcTemplate.query(SqlDefine.sys_rdbms_105, rowMapper, domainId);
        return list;
    }
}
