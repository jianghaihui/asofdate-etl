package com.asofdate.dispatch.dao.impl;

import com.asofdate.dispatch.dao.TaskDefineDao;
import com.asofdate.dispatch.model.TaskDefineModel;
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
public class TaskDefineDaoImpl implements TaskDefineDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List findAll(String domainId) {
        RowMapper<TaskDefineModel> rowMapper = new BeanPropertyRowMapper<TaskDefineModel>(TaskDefineModel.class);
        List<TaskDefineModel> list = jdbcTemplate.query(SqlDefine.sys_rdbms_111, rowMapper, domainId);
        return list;
    }

    @Override
    public int add(TaskDefineModel m) {
        return jdbcTemplate.update(SqlDefine.sys_rdbms_125,
                m.getTaskId(),
                m.getCodeNumber(),
                m.getTaskDesc(),
                m.getTaskType(),
                m.getCreateUser(),
                m.getModifyUser(),
                m.getDomainId(),
                m.getScriptFile());
    }

    @Override
    public String delete(List<TaskDefineModel> m) {
        for (TaskDefineModel l : m) {
            if (1 != jdbcTemplate.update(SqlDefine.sys_rdbms_127, l.getTaskId(), l.getDomainId())) {
                return "删除[" + l.getCodeNumber() + "]失败";
            }
        }
        return "success";
    }

    @Override
    public int update(TaskDefineModel m) {
        return jdbcTemplate.update(SqlDefine.sys_rdbms_126,
                m.getTaskDesc(),
                m.getTaskType(),
                m.getScriptFile(),
                m.getTaskId(),
                m.getDomainId());
    }
}
