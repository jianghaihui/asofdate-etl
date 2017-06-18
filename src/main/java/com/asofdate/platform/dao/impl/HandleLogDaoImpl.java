package com.asofdate.platform.dao.impl;

import com.asofdate.platform.dao.HandleLogDao;
import com.asofdate.platform.model.HandleLogModel;
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
public class HandleLogDaoImpl implements HandleLogDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<HandleLogModel> findAll(String domainId) {
        RowMapper<HandleLogModel> rowMapper = new BeanPropertyRowMapper<>(HandleLogModel.class);
        return jdbcTemplate.query(SqlDefine.sys_rdbms_012,rowMapper,domainId);
    }

    @Override
    public List<HandleLogModel> findAll(String domainId, Integer offset, Integer limit) {
        RowMapper<HandleLogModel> rowMapper = new BeanPropertyRowMapper<>(HandleLogModel.class);
        return jdbcTemplate.query(SqlDefine.sys_rdbms_029,rowMapper,domainId,offset,limit);
    }

    @Override
    public Integer getTotal(String domainId) {
        return jdbcTemplate.queryForObject(SqlDefine.sys_rdbms_030,Integer.class,domainId);
    }
}
