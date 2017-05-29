package com.asofdate.dispatch.dao.impl;

import com.asofdate.dispatch.dao.GroupDependencyDao;
import com.asofdate.dispatch.model.GroupDependencyModel;
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
public class GroupDependencyDaoImpl implements GroupDependencyDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<GroupDependencyModel> findAll(String domainId) {
        RowMapper<GroupDependencyModel> rowMapper = new BeanPropertyRowMapper<GroupDependencyModel>(GroupDependencyModel.class);
        List<GroupDependencyModel> list = jdbcTemplate.query(SqlDefine.sys_rdbms_112, rowMapper, domainId);
        return list;
    }

    @Override
    public List<GroupDependencyModel> findById(String domainId, String batchId) {
        List<GroupDependencyModel> list = findAll(domainId);
        for (int i = 0; i < list.size(); i++) {
            if (batchId.equals(list.get(i))) {
                list.remove(i);
                i--;
            }
        }
        return list;
    }
}
