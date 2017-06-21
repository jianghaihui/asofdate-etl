package com.asofdate.platform.dao.impl;

import com.asofdate.platform.dao.ResourceDao;
import com.asofdate.platform.model.ResourceModel;
import com.asofdate.sql.SqlDefine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hzwy23 on 2017/5/17.
 */
@Repository
public class ResourceDaoImpl implements ResourceDao {
    @Autowired
    public JdbcTemplate jdbcTemplate;

    @Override
    public List findAll() {
        RowMapper<ResourceModel> rowMapper = new BeanPropertyRowMapper<ResourceModel>(ResourceModel.class);
        List list = jdbcTemplate.query(SqlDefine.sys_rdbms_071, rowMapper);
        return list;
    }

    @Override
    public List findSubByUpId(String resUpId) {
        List<ResourceModel> list = findAll();
        List<ResourceModel> ret = new ArrayList<>();
        dfs(list, resUpId, ret);
        return ret;
    }

    private void dfs(List<ResourceModel> all, String resUpId, List<ResourceModel> ret) {
        for (ResourceModel m : all) {
            String upId = m.getRes_up_id();
            if (upId != null){
                if (upId.equals(resUpId)) {
                    ret.add(m);
                    dfs(all, m.getRes_id(), ret);
                }
            }
        }
    }
}
