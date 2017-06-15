package com.asofdate.dispatch.dao.impl;

import com.asofdate.dispatch.dao.SysConfigDao;
import com.asofdate.dispatch.model.SysConfigModel;
import com.asofdate.sql.SqlDefine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by hzwy23 on 2017/6/15.
 */
@Repository
public class SysConfigDaoImpl implements SysConfigDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Override
    public List<SysConfigModel> findAll(String domainId) {
        RowMapper<SysConfigModel> rowMapper = new BeanPropertyRowMapper<SysConfigModel>(SysConfigModel.class);
        List<SysConfigModel> list= jdbcTemplate.query(SqlDefine.sys_rdbms_181,rowMapper);
        return list;
    }

    @Override
    public int setValue(String domainId, String configId, String configValue) {
        return 0;
    }

    @Override
    public String getValue(String domainId, String configId) {
        return null;
    }
}
