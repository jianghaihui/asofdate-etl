package com.asofdate.dispatch.dao.impl;

import com.asofdate.dispatch.dao.GroupDependencyDao;
import com.asofdate.dispatch.model.GroupDependencyModel;
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
 * Created by hzwy23 on 2017/5/27.
 */
@Repository
public class GroupDependencyDaoImpl implements GroupDependencyDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<GroupDependencyModel> findAll(String domainId) {
        RowMapper<GroupDependencyModel> rowMapper = new BeanPropertyRowMapper<GroupDependencyModel>(GroupDependencyModel.class);
        List<GroupDependencyModel> list = jdbcTemplate.query(SqlDefine.sys_rdbms_112, rowMapper, domainId);
        return list;
    }

    @Override
    public List<GroupDependencyModel> findById(String domainId, String batchId) {
        List<GroupDependencyModel> list = findAll(domainId);
        for (int i = 0; i < list.size(); i++) {
            if (batchId.equals(list.get(i))) {
                list.remove(i);
                i--;
            }
        }
        return list;
    }

    @Override
    public JSONArray getGroupDependency(String id) {
        JSONArray jsonArray = new JSONArray();
        jdbcTemplate.query(SqlDefine.sys_rdbms_138, new RowCallbackHandler() {
            @Override
            public void processRow(ResultSet resultSet) throws SQLException {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("uuid", resultSet.getString("uuid"));
                jsonObject.put("id", resultSet.getString("id"));
                jsonObject.put("up_id", resultSet.getString("up_id"));
                jsonObject.put("domain_id", resultSet.getString("domain_id"));
                jsonObject.put("group_id", resultSet.getString("group_id"));
                jsonObject.put("group_desc", resultSet.getString("group_desc"));
                jsonObject.put("code_number", resultSet.getString("code_number"));
                jsonArray.put(jsonObject);
            }
        }, id);
        return jsonArray;
    }

}
