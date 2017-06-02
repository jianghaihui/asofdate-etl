package com.asofdate.dispatch.dao.impl;

import com.asofdate.dispatch.dao.TaskArgumentDao;
import com.asofdate.dispatch.model.TaskArgumentModel;
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
public class TaskArgumentDaoImpl implements TaskArgumentDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List findAll(String domainId) {
        RowMapper<TaskArgumentModel> rowMapper = new BeanPropertyRowMapper<TaskArgumentModel>(TaskArgumentModel.class);
        List list = jdbcTemplate.query(SqlDefine.sys_rdbms_110, rowMapper, domainId);
        return list;
    }

    @Override
    public JSONArray getTaskArg(String taskId) {
        JSONArray jsonArray = new JSONArray();
        jdbcTemplate.query(SqlDefine.sys_rdbms_132, new RowCallbackHandler() {
            @Override
            public void processRow(ResultSet resultSet) throws SQLException {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("uuid",resultSet.getString("uuid"));
                jsonObject.put("task_id",resultSet.getString("task_id"));
                jsonObject.put("arg_id",resultSet.getString("arg_id"));
                switch (resultSet.getString("arg_type")){
                    case "1":
                        jsonObject.put("arg_value",resultSet.getString("fixed_arg_value"));
                        break;
                    case "2":
                        jsonObject.put("arg_value",resultSet.getString("arg_value"));
                        break;
                    default:
                        jsonObject.put("arg_value","-");
                }

                jsonObject.put("sort_id",resultSet.getString("sort_id"));
                jsonObject.put("domain_id",resultSet.getString("domain_id"));
                jsonObject.put("arg_type",resultSet.getString("arg_type"));
                jsonObject.put("arg_type_desc",resultSet.getString("arg_type_desc"));
                jsonObject.put("arg_desc",resultSet.getString("arg_desc"));
                jsonObject.put("code_number",resultSet.getString("code_number"));
                jsonArray.put(jsonObject);
            }
        },taskId);
        return jsonArray;
    }
}
