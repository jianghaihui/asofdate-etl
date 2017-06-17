package com.asofdate.dispatch.dao.impl;

import com.asofdate.dispatch.dao.BatchGroupHistoryDao;
import com.asofdate.dispatch.model.BatchGroupHistoryModel;
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
public class BatchGroupHistoryDaoImpl implements BatchGroupHistoryDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<BatchGroupHistoryModel> findAll(String uuid) {
        RowMapper<BatchGroupHistoryModel> rowMapper = new BeanPropertyRowMapper<>(BatchGroupHistoryModel.class);
        List<BatchGroupHistoryModel> list = jdbcTemplate.query(SqlDefine.sys_rdbms_197, rowMapper, uuid);
        for (BatchGroupHistoryModel bh : list) {
            String gid = bh.getGid();
            Integer totalCnt = getTotalJobs(uuid, gid);
            Integer completeCnt = getCompleteJobs(uuid, gid);
            bh.setTotalJobsCnt(totalCnt);
            bh.setCompleteJobsCnt(completeCnt);
        }
        return list;
    }

    private Integer getTotalJobs(String uuid, String gid) {
        return jdbcTemplate.queryForObject(SqlDefine.sys_rdbms_198, Integer.class, uuid, gid);
    }

    private Integer getCompleteJobs(String uuid, String gid) {
        return jdbcTemplate.queryForObject(SqlDefine.sys_rdbms_199, Integer.class, uuid, gid);
    }

}
