package com.asofdate.dispatch.dao.impl;

import com.asofdate.dispatch.dao.BatchDefineDao;
import com.asofdate.dispatch.model.BatchDefineModel;
import com.asofdate.sql.SqlDefine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by hzwy23 on 2017/5/24.
 */
@Repository
public class BatchDefineDaoImpl implements BatchDefineDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List findAll(String domainId) {
        RowMapper<BatchDefineModel> rowMapper = new BeanPropertyRowMapper<BatchDefineModel>(BatchDefineModel.class);
        List list = jdbcTemplate.query(SqlDefine.sys_rdbms_107, rowMapper, domainId);
        return list;
    }

    @Override
    public int add(BatchDefineModel m) {
        return jdbcTemplate.update(SqlDefine.sys_rdbms_128,
                m.getBatch_id(),
                m.getCode_number(),
                m.getBatch_desc(),
                m.getBatch_status(),
                m.getAs_of_date(),
                m.getCreate_user(),
                m.getModify_user(),
                m.getDomain_id());
    }

    @Override
    public String delete(List<BatchDefineModel> m) {
        for (BatchDefineModel l : m) {
            if (1 != jdbcTemplate.update(SqlDefine.sys_rdbms_129, l.getBatch_id(), l.getDomain_id())) {
                return "删除[" + l.getBatch_desc() + "]失败";
            }
        }
        return "success";
    }

    @Override
    public int update(BatchDefineModel m) {
        return jdbcTemplate.update(SqlDefine.sys_rdbms_130,
                m.getBatch_desc(),
                m.getBatch_status(),
                m.getAs_of_date(),
                m.getBatch_id(),
                m.getDomain_id());
    }

    @Override
    public int getStatus(String batchId) {
        return jdbcTemplate.queryForObject(SqlDefine.sys_rdbms_131, Integer.class, batchId);
    }

    @Override
    public int setStatus(String batchId, int status) {
        return jdbcTemplate.update(SqlDefine.sys_rdbms_140, Integer.toString(status), batchId);
    }

    @Override
    public int updateAsofdate(String asofdate, String batchId) {
        return jdbcTemplate.update(SqlDefine.sys_rdbms_161,asofdate,batchId);
    }
}
