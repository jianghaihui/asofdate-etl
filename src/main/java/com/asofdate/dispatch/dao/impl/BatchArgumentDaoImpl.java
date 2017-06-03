package com.asofdate.dispatch.dao.impl;

import com.asofdate.dispatch.dao.BatchArgumentDao;
import com.asofdate.dispatch.model.BatchArgumentModel;
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

    @Override
    public JSONArray getBatchArg(String batchId) {
        JSONArray jsonArray = new JSONArray();
        jdbcTemplate.query(SqlDefine.sys_rdbms_139, new RowCallbackHandler() {
            @Override
            public void processRow(ResultSet resultSet) throws SQLException {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("uuid", resultSet.getString("uuid"));
                jsonObject.put("batch_id", resultSet.getString("batch_id"));
                jsonObject.put("arg_id", resultSet.getString("arg_id"));
                jsonObject.put("arg_value", resultSet.getString("arg_value"));
                jsonObject.put("domain_id", resultSet.getString("domain_id"));
                jsonObject.put("arg_desc", resultSet.getString("arg_desc"));
                jsonObject.put("arg_type", resultSet.getString("arg_type"));
                jsonObject.put("arg_type_desc", resultSet.getString("arg_type_desc"));
                jsonObject.put("code_number", resultSet.getString("code_number"));
                jsonArray.put(jsonObject);
            }
        }, batchId);
        return jsonArray;
    }
}
