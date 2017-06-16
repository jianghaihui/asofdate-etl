package com.asofdate.dispatch.dao.impl;

import com.asofdate.dispatch.dao.BatchHistoryDao;
import com.asofdate.dispatch.model.BatchHistoryModel;
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
 * Created by hzwy23 on 2017/6/16.
 */
@Repository
public class BatchHistoryDaoImpl implements BatchHistoryDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<BatchHistoryModel> findAll(String domainId) {
        RowMapper<BatchHistoryModel> rowMapper = new BeanPropertyRowMapper<>(BatchHistoryModel.class);
        List<BatchHistoryModel> list = jdbcTemplate.query(SqlDefine.sys_rdbms_193,rowMapper,domainId);
        return list;
    }

    @Transactional
    @Override
    public int delete(JSONArray jsonArray) {
        for (int i = 0; i < jsonArray.length(); i++){
            String uuid = ((JSONObject)jsonArray.get(i)).getString("uuid");
            int size = jdbcTemplate.update(SqlDefine.sys_rdbms_194,uuid);
            if (size != 1){
                return -1;
            }
        }
        return 1;
    }
}
