package com.asofdate.platform.dao.impl;

import com.asofdate.platform.dao.DomainDao;
import com.asofdate.platform.model.DomainModel;
import com.asofdate.sql.SqlDefine;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.ResultSet;
import java.sql.SQLException;
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

    @Override
    public DomainModel getDomainDetails(String domainId) {
        DomainModel domainModel = new DomainModel();
        jdbcTemplate.query(SqlDefine.sys_rdbms_084, new RowCallbackHandler() {
            @Override
            public void processRow(ResultSet resultSet) throws SQLException {
                domainModel.setDomain_id(domainId);
                domainModel.setDomain_desc(resultSet.getString("domain_desc"));
                domainModel.setDomain_status_desc(resultSet.getString("domain_status_desc"));
                domainModel.setMaintance_date(resultSet.getString("maintance_date"));
                domainModel.setCreate_user_id(resultSet.getString("create_user_id"));
                domainModel.setDomain_modify_user(resultSet.getString("domain_modify_user"));
                domainModel.setDomain_modify_date(resultSet.getString("domain_modify_date"));
            }
        }, domainId);
        return domainModel;
    }
}
