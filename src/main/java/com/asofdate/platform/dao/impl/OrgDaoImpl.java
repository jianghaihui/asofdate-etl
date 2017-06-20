package com.asofdate.platform.dao.impl;

import com.asofdate.platform.dao.OrgDao;
import com.asofdate.platform.model.OrgModel;
import com.asofdate.sql.SqlDefine;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hzwy23 on 2017/6/18.
 */
@Repository
public class OrgDaoImpl implements OrgDao {
    private final Logger logger = LoggerFactory.getLogger(OrgDaoImpl.class);

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
        for (OrgModel m : list) {
            if (orgId.equals(m.getOrg_id())) {
                ret.add(m);
                break;
            }
        }
        getSub(list, orgId, ret);
        return ret;
    }

    @Override
    public int add(OrgModel orgModel) {
        return jdbcTemplate.update(SqlDefine.sys_rdbms_043,
                orgModel.getCode_number(),
                orgModel.getOrg_desc(),
                orgModel.getUp_org_id(),
                orgModel.getDomain_id(),
                orgModel.getCreate_user(),
                orgModel.getModify_user(),
                orgModel.getOrg_id());
    }

    @Transactional
    @Override
    public int delete(JSONArray jsonArray) {
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = (JSONObject) jsonArray.get(i);
            jdbcTemplate.update(SqlDefine.sys_rdbms_044,
                    jsonObject.getString("org_id"),
                    jsonObject.getString("domain_id"));
        }
        return 1;
    }

    @Override
    public int update(OrgModel orgModel) {
        logger.debug("{},{},{},{}", orgModel.getOrg_desc(), orgModel.getUp_org_id(), orgModel.getModify_user(), orgModel.getOrg_id());
        return jdbcTemplate.update(SqlDefine.sys_rdbms_069,
                orgModel.getOrg_desc(),
                orgModel.getUp_org_id(),
                orgModel.getModify_user(),
                orgModel.getOrg_id());
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
