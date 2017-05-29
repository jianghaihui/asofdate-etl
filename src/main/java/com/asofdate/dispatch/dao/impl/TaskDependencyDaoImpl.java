package com.asofdate.dispatch.dao.impl;

import com.asofdate.dispatch.dao.TaskDependencyDao;
import com.asofdate.dispatch.model.TaskDependencyModel;
import com.asofdate.sql.SqlDefine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by hzwy23 on 2017/5/27.
 */
@Repository
public class TaskDependencyDaoImpl implements TaskDependencyDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<TaskDependencyModel> findAll(String domainId) {
        RowMapper<TaskDependencyModel> rowMapper = new BeanPropertyRowMapper<TaskDependencyModel>(TaskDependencyModel.class);
        List<TaskDependencyModel> list = jdbcTemplate.query(SqlDefine.sys_rdbms_113, rowMapper, domainId);
        return list;
    }

    @Override
    public List<TaskDependencyModel> findById(String domainId, String batchId) {
        List<TaskDependencyModel> list = findAll(domainId);
        for (int i = 0; i < list.size(); i++) {
            if (batchId.equals(list.get(i))) {
                list.remove(i);
                i--;
            }
        }
        return list;
    }
}
