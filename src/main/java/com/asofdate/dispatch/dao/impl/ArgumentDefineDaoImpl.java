package com.asofdate.dispatch.dao.impl;

import com.asofdate.dispatch.dao.ArgumentDefineDao;
import com.asofdate.dispatch.model.ArgumentDefineModel;
import com.asofdate.sql.SqlDefine;
import com.asofdate.utils.JoinCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by hzwy23 on 2017/5/24.
 */
@Repository
public class ArgumentDefineDaoImpl implements ArgumentDefineDao {
    private final Logger logger = LoggerFactory.getLogger(ArgumentDefineDaoImpl.class);
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List findAll(String domainId) {
        RowMapper<ArgumentDefineModel> rowMapper = new BeanPropertyRowMapper<ArgumentDefineModel>(ArgumentDefineModel.class);
        List<ArgumentDefineModel> list = jdbcTemplate.query(SqlDefine.sys_rdbms_104, rowMapper, domainId);
        return list;
    }

    @Override
    public int add(ArgumentDefineModel m) {
        String id = JoinCode.join(m.getDomainId(), m.getArgId());
        return jdbcTemplate.update(SqlDefine.sys_rdbms_119,
                id,
                m.getArgType(),
                m.getArgValue(),
                m.getArgId(),
                m.getCreateUser(),
                m.getModifyUser(),
                m.getDomainId(),
                m.getArgDesc(),
                m.getBindAsOfDate());
    }

    @Transactional
    @Override
    public String delete(List<ArgumentDefineModel> m) {
        try {
            for (ArgumentDefineModel l : m) {
                jdbcTemplate.update(SqlDefine.sys_rdbms_120, l.getArgId(), l.getDomainId());
            }
            return "success";
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    @Override
    public int update(ArgumentDefineModel m) {
        String id = JoinCode.join(m.getDomainId(), m.getArgId());
        return jdbcTemplate.update(SqlDefine.sys_rdbms_121,
                m.getModifyUser(),
                m.getBindAsOfDate(),
                m.getArgDesc(),
                m.getArgValue(),
                id,
                m.getDomainId());

    }
}
