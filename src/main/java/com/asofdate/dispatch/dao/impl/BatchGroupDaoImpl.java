package com.asofdate.dispatch.dao.impl;

import com.asofdate.dispatch.dao.BatchGroupDao;
import com.asofdate.dispatch.model.BatchGroupModel;
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
public class BatchGroupDaoImpl implements BatchGroupDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List findAll(String domainId) {
        RowMapper<BatchGroupModel> rowMapper = new BeanPropertyRowMapper<>(BatchGroupModel.class);
        List<BatchGroupModel> list = jdbcTemplate.query(SqlDefine.sys_rdbms_106, rowMapper, domainId);
        return list;
    }

    @Override
    public JSONArray getGroup(String batchId) {
        JSONArray jsonArray = new JSONArray();
        jdbcTemplate.query(SqlDefine.sys_rdbms_137, new RowCallbackHandler() {
            @Override
            public void processRow(ResultSet resultSet) throws SQLException {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("id", resultSet.getString("id"));
                jsonObject.put("batch_id", resultSet.getString("batch_id"));
                jsonObject.put("group_id", resultSet.getString("group_id"));
                jsonObject.put("domain_id", resultSet.getString("domain_id"));
                jsonObject.put("group_desc", resultSet.getString("group_desc"));
                jsonObject.put("code_number", resultSet.getString("code_number"));
                jsonArray.put(jsonObject);
            }
        }, batchId);
        return jsonArray;
    }
}
