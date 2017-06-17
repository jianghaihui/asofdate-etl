package com.asofdate.dispatch.dao.impl;

import com.asofdate.dispatch.dao.BatchJobRunningDao;
import com.asofdate.dispatch.model.BatchJobHistoryModel;
import com.asofdate.dispatch.model.BatchJobStatusModel;
import com.asofdate.sql.SqlDefine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by hzwy23 on 2017/6/17.
 */
@Repository
public class BatchJobRunningDaoImpl implements BatchJobRunningDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<BatchJobStatusModel> findAll(String batchId, String gid) {
        RowMapper<BatchJobStatusModel> rowMapper = new BeanPropertyRowMapper<>(BatchJobStatusModel.class);
        return jdbcTemplate.query(SqlDefine.sys_rdbms_204,rowMapper,batchId,gid);
    }
}
