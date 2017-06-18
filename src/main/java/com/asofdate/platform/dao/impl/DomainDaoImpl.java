package com.asofdate.platform.dao.impl;

import com.asofdate.platform.dao.DomainDao;
import com.asofdate.platform.model.DomainModel;
import com.asofdate.sql.SqlDefine;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

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
        List<DomainModel> list = jdbcTemplate.query(SqlDefine.sys_rdbms_118, rowMapper);
        return list;
    }

    @Override
    public List<DomainModel> getAll() {
        RowMapper<DomainModel> rowMapper = new BeanPropertyRowMapper<>(DomainModel.class);
        List<DomainModel> list = jdbcTemplate.query(SqlDefine.sys_rdbms_025, rowMapper);
        return list;
    }

    @Override
    public int update(DomainModel domainModel) {
        return jdbcTemplate.update(SqlDefine.sys_rdbms_038,
                domainModel.getDomain_desc(),
                domainModel.getDomain_status_id(),
                domainModel.getDomain_modify_user(),
                domainModel.getDomain_id());
    }

    @Transactional
    @Override
    public String delete(JSONArray jsonArray) {
        for(int i=0; i< jsonArray.length(); i++){
            String domainId = ((JSONObject)jsonArray.get(i)).getString("domain_id");
            jdbcTemplate.update(SqlDefine.sys_rdbms_037,domainId);
        }
        return "success";
    }

    @Override
    public int add(DomainModel domainModel) {
        return jdbcTemplate.update(SqlDefine.sys_rdbms_036,
                domainModel.getDomain_id(),
                domainModel.getDomain_desc(),
                domainModel.getDomain_status_id(),
                domainModel.getCreate_user_id(),
                domainModel.getDomain_modify_user());
    }
}
