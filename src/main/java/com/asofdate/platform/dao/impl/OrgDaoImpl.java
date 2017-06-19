package com.asofdate.platform.dao.impl;

import com.asofdate.platform.dao.OrgDao;
import com.asofdate.platform.model.OrgModel;
import com.asofdate.sql.SqlDefine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hzwy23 on 2017/6/18.
 */
@Repository
public class OrgDaoImpl implements OrgDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<OrgModel> findAll(String domainId) {
        RowMapper<OrgModel> rowMapper = new BeanPropertyRowMapper<>(OrgModel.class);
        return jdbcTemplate.query(SqlDefine.sys_rdbms_041, rowMapper, domainId);
    }

    @Override
    public List<OrgModel> findSub(String domainId, String orgId) {
        List<OrgModel> list = findAll(domainId);
        List<OrgModel> ret = new ArrayList<OrgModel>();
        getSub(list, orgId, ret);
        return ret;
    }

    private void getSub(List<OrgModel> all, String orgId, List<OrgModel> ret) {
        for (OrgModel a : all) {
            if (orgId.equals(a.getUp_org_id())) {
                if (ret.contains(a)) {
                    continue;
                }
                ret.add(a);
                getSub(all, a.getOrg_id(), ret);
            }
        }
    }
}
