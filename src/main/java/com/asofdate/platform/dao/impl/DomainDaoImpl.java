package com.asofdate.platform.dao.impl;

import com.asofdate.platform.dao.DomainDao;
import com.asofdate.platform.model.DomainModel;
import com.asofdate.sql.SqlDefine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by hzwy23 on 2017/6/1.
 */
@Repository
public class DomainDaoImpl implements DomainDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<DomainModel> findAll() {
        RowMapper<DomainModel> rowMapper = new BeanPropertyRowMapper<>(DomainModel.class);
        List<DomainModel> list = jdbcTemplate.query(SqlDefine.sys_rdbms_118,rowMapper);
        return list;
    }
}
