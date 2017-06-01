package com.asofdate.dispatch.dao.impl;

import com.asofdate.dispatch.dao.ArgumentDefineDao;
import com.asofdate.dispatch.model.ArgumentDefineModel;
import com.asofdate.sql.SqlDefine;
import com.asofdate.utils.JoinCode;
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
public class ArgumentDefineDaoImpl implements ArgumentDefineDao {
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
        String id = JoinCode.join(m.getDomain_id(), m.getArg_id());
        return jdbcTemplate.update(SqlDefine.sys_rdbms_119,
                id,
                m.getArg_type(),
                m.getArg_value(),
                m.getArg_id(),
                m.getCreate_user(),
                m.getModify_user(),
                m.getDomain_id(),
                m.getArg_desc());
    }

    @Override
    public String delete(List<ArgumentDefineModel> m) {
        int row = 0;
        for (ArgumentDefineModel l : m) {
            row = jdbcTemplate.update(SqlDefine.sys_rdbms_120, l.getArg_id(), l.getDomain_id());
            if (row == 0) {
                return "删除[" + l.getCode_number() + "]参数失败";
            }
        }
        return "success";
    }

    @Override
    public int update(ArgumentDefineModel m) {
        String id = JoinCode.join(m.getDomain_id(), m.getArg_id());
        return jdbcTemplate.update(SqlDefine.sys_rdbms_121,
                m.getArg_desc(),
                m.getArg_type(),
                m.getArg_value(),
                id,
                m.getDomain_id());
    }
}
