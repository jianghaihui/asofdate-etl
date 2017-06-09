package com.asofdate.dispatch.dao.impl;

import com.asofdate.dispatch.dao.GroupDefineDao;
import com.asofdate.dispatch.model.GroupDefineModel;
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
public class GroupDefineDaoImpl implements GroupDefineDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List findAll(String domainId) {
        RowMapper<GroupDefineModel> rowMapper = new BeanPropertyRowMapper<GroupDefineModel>(GroupDefineModel.class);
        List list = jdbcTemplate.query(SqlDefine.sys_rdbms_108, rowMapper, domainId);
        return list;
    }

    @Override
    public int add(GroupDefineModel m) {
        return jdbcTemplate.update(SqlDefine.sys_rdbms_124,
                m.getGroupId(),
                m.getCodeNumber(),
                m.getGroupDesc(),
                m.getCreateUser(),
                m.getModifyUser(),
                m.getDomainId());
    }

    @Override
    public String delete(List<GroupDefineModel> m) {
        for (GroupDefineModel l : m) {
            int i = jdbcTemplate.update(SqlDefine.sys_rdbms_122, l.getGroupId(), l.getDomainId());
            if (i == 0) {
                return "删除[" + l.getCodeNumber() + "]失败,任务组已经被引用,请先解除引用.";
            }
        }
        return "success";
    }

    @Override
    public int update(GroupDefineModel m) {
        return jdbcTemplate.update(SqlDefine.sys_rdbms_123,
                m.getGroupDesc(),
                m.getGroupId(),
                m.getDomainId());
    }
}
