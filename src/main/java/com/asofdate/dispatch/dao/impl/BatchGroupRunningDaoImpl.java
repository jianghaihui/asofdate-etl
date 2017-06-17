package com.asofdate.dispatch.dao.impl;

import com.asofdate.dispatch.dao.BatchGroupRunningDao;
import com.asofdate.dispatch.model.BatchGroupHistoryModel;
import com.asofdate.dispatch.model.BatchGroupStatusModel;
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
public class BatchGroupRunningDaoImpl implements BatchGroupRunningDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<BatchGroupStatusModel> findAll(String uuid) {
        RowMapper<BatchGroupStatusModel> rowMapper = new BeanPropertyRowMapper<>(BatchGroupStatusModel.class);
        List<BatchGroupStatusModel> list = jdbcTemplate.query(SqlDefine.sys_rdbms_201, rowMapper, uuid);
        for (BatchGroupStatusModel bh : list) {

            Integer totalCnt = getTotalJobs(uuid, bh.getGid());

            Integer completeCnt = getCompleteJobs(uuid, bh.getGid());

            bh.setTotalJobsCnt(totalCnt);

            bh.setCompleteJobsCnt(completeCnt);
        }
        return list;
    }

    private Integer getTotalJobs(String uuid, String gid) {
        return jdbcTemplate.queryForObject(SqlDefine.sys_rdbms_202, Integer.class, uuid, gid);
    }

    private Integer getCompleteJobs(String uuid, String gid) {
        return jdbcTemplate.queryForObject(SqlDefine.sys_rdbms_203, Integer.class, uuid, gid);
    }
}
