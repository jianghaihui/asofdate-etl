package com.asofdate.dispatch.dao.impl;

import com.asofdate.dispatch.dao.GroupDefineDao;
import com.asofdate.dispatch.model.GroupDefineModel;
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
public class GroupDefineDaoImpl implements GroupDefineDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List findAll(String domainId) {
        RowMapper<GroupDefineModel> rowMapper = new BeanPropertyRowMapper<GroupDefineModel>(GroupDefineModel.class);
        List list = jdbcTemplate.query(SqlDefine.sys_rdbms_108, rowMapper, domainId);
        return list;
    }
}
