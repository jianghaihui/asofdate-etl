package com.asofdate.platform.dao.impl;

import com.asofdate.platform.dao.OrgDao;
import com.asofdate.platform.dao.UserDao;
import com.asofdate.platform.model.OrgModel;
import com.asofdate.platform.model.UserModel;
import com.asofdate.sql.SqlDefine;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by hzwy23 on 2017/6/18.
 */
@Repository
public class UserDaoImpl implements UserDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private OrgDao orgDao;

    @Override
    public List<UserModel> findAll(String domainid) {
        RowMapper<UserModel> rowMapper = new BeanPropertyRowMapper<>(UserModel.class);
        return jdbcTemplate.query(SqlDefine.sys_rdbms_017, rowMapper, domainid);
    }

    @Override
    public List<UserModel> findAll(String domainId, String orgId, String statusCd) {
        List<UserModel> list = findAll(domainId);
        if ("0".equals(statusCd) || "1".equals(statusCd)) {
            for (int i = 0; i < list.size(); i++) {
                if (!statusCd.equals(list.get(i).getStatus_cd())) {
                    list.remove(i);
                    i--;
                }
            }
        }
        if (orgId != null && !orgId.isEmpty()) {
            List<OrgModel> orgList = orgDao.findSub(domainId, orgId);
            Set<String> set = new HashSet<>();
            for (OrgModel om : orgList) {
                set.add(om.getOrg_id());
            }
            set.add(orgId);
            for (int i = 0; i < list.size(); i++) {
                if (!set.contains(list.get(i).getOrg_unit_id())) {
                    list.remove(i);
                    i--;
                }
            }
        }
        return list;
    }

    @Override
    public int add(UserModel userModel) {
        return 0;
    }

    @Override
    public int delete(JSONArray jsonArray) {
        return 0;
    }

    @Override
    public int update(UserModel userModel) {
        return 0;
    }

    @Override
    public int changePassword(JSONObject jsonObject) {
        return 0;
    }

    @Override
    public int changeStatus(String userId, String status) {
        return 0;
    }
}
