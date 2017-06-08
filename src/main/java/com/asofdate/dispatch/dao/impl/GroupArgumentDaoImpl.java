package com.asofdate.dispatch.dao.impl;

import com.asofdate.dispatch.dao.GroupArgumentDao;
import com.asofdate.dispatch.model.GroupArgumentModel;
import com.asofdate.sql.SqlDefine;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
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
        List<GroupArgumentModel> list = jdbcTemplate.query(SqlDefine.sys_rdbms_114, rowMapper, domainId);
        return list;
    }

    @Override
    public JSONArray getGroupArg(String id) {
        JSONArray jsonArray = new JSONArray();
        jdbcTemplate.query(SqlDefine.sys_rdbms_135, new RowCallbackHandler() {
            @Override
            public void processRow(ResultSet resultSet) throws SQLException {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("id", resultSet.getString("id"));
                jsonObject.put("arg_id", resultSet.getString("arg_id"));
                jsonObject.put("arg_value", resultSet.getString("arg_value"));
                jsonObject.put("domain_id", resultSet.getString("domain_id"));
                jsonObject.put("group_id", resultSet.getString("group_id"));
                jsonObject.put("task_id", resultSet.getString("task_id"));
                jsonArray.put(jsonObject);
            }
        }, id);
        return jsonArray;
    }

    @Override
    public int updateArg(String argValue, String uuid, String argId) {
        return jdbcTemplate.update(SqlDefine.sys_rdbms_146, argValue, uuid, argId);
    }
}
