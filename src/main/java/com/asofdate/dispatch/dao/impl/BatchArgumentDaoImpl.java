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
        String asOfDate = getAsOfDate(batchId);
        System.out.println("as of date is :" + asOfDate);
        jdbcTemplate.query(SqlDefine.sys_rdbms_139, new RowCallbackHandler() {
            @Override
            public void processRow(ResultSet resultSet) throws SQLException {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("code_number", resultSet.getString("code_number"));
                jsonObject.put("batch_id", resultSet.getString("batch_id"));
                jsonObject.put("arg_id", resultSet.getString("arg_id"));
                jsonObject.put("domain_id",resultSet.getString("domain_id"));

                if ("1".equals(resultSet.getString("bind_as_of_date"))){
                    jsonObject.put("arg_value", asOfDate);
                } else {
                    jsonObject.put("arg_value", resultSet.getString("arg_value"));
                }

                jsonObject.put("bind_as_of_date",resultSet.getString("bind_as_of_date"));
                jsonObject.put("arg_desc", resultSet.getString("arg_desc"));
                jsonObject.put("uuid", resultSet.getString("uuid"));
                jsonArray.put(jsonObject);
            }
        }, batchId);
        System.out.println(jsonArray.toString());
        return jsonArray;
    }

    @Override
    public String getAsOfDate(String batchId) {
        return jdbcTemplate.queryForObject(SqlDefine.sys_rdbms_157,String.class,batchId);
    }

    @Override
    public int addBatchArg(JSONArray jsonArray) {
        for (int i = 0; i < jsonArray.length(); i++){
            JSONObject jsonObject = (JSONObject) jsonArray.get(i);




            if (1 != jdbcTemplate.update(SqlDefine.sys_rdbms_158,
                    jsonObject.getString("batch_id"),
                    jsonObject.getString("arg_id"),
                    jsonObject.getString("arg_value"),
                    jsonObject.getString("domain_id"))){
                return -1;
            }
        }
        return 1;
    }
}
