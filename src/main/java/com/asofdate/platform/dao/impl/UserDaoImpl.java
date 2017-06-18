package com.asofdate.platform.dao.impl;

import com.asofdate.platform.dao.UserDao;
import com.asofdate.platform.model.UserModel;
import com.asofdate.sql.SqlDefine;
import org.json.JSONArray;
import org.json.JSONObject;
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
public class UserDaoImpl implements UserDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<UserModel> findAll(String domainid) {
        RowMapper<UserModel> rowMapper = new BeanPropertyRowMapper<>(UserModel.class);
        return jdbcTemplate.query(SqlDefine.sys_rdbms_017,rowMapper,domainid);
    }

    @Override
    public List<UserModel> findAll(String domainId, String orgId) {
        return null;
    }

    @Override
    public List<UserModel> findAll(String domainId, String orgId, String statusCd) {
        return null;
    }

    @Override
    public int add(UserModel userModel) {
        return 0;
    }

    @Override
    public int delete(JSONArray jsonArray) {
        return 0;
    }

    @Override
    public int update(UserModel userModel) {
        return 0;
    }

    @Override
    public int changePassword(JSONObject jsonObject) {
        return 0;
    }

    @Override
    public int changeStatus(String userId, String status) {
        return 0;
    }
}
