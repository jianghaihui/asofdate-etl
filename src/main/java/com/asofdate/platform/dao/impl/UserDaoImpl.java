package com.asofdate.platform.dao.impl;

import com.asofdate.platform.dao.OrgDao;
import com.asofdate.platform.dao.UserDao;
import com.asofdate.platform.model.OrgModel;
import com.asofdate.platform.model.UserModel;
import com.asofdate.sql.SqlDefine;
import com.asofdate.utils.CryptoAES;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional
    @Override
    public int add(UserModel userModel) {
        jdbcTemplate.update(SqlDefine.sys_rdbms_018,
                userModel.getUser_id(),
                userModel.getUser_name(),
                userModel.getCreate_user(),
                userModel.getUser_email(),
                userModel.getUser_phone(),
                userModel.getOrg_unit_id(),
                userModel.getModify_user());
        String password = CryptoAES.aesEncrypt(userModel.getUser_passwd());
        return jdbcTemplate.update(SqlDefine.sys_rdbms_019,
                userModel.getUser_id(), password, 0);
    }

    @Transactional
    @Override
    public int delete(JSONArray jsonArray) {
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject row = (JSONObject) jsonArray.get(i);
            jdbcTemplate.update(SqlDefine.sys_rdbms_007,
                    row.getString("user_id"),
                    row.getString("org_unit_id"));
        }
        return 1;
    }

    @Override
    public int update(UserModel userModel) {
        return jdbcTemplate.update(SqlDefine.sys_rdbms_021,
                userModel.getUser_name(),
                userModel.getUser_phone(),
                userModel.getUser_email(),
                userModel.getModify_user(),
                userModel.getOrg_unit_id(),
                userModel.getUser_id());
    }

    @Override
    public int changePassword(JSONObject jsonObject) {
        String userId = jsonObject.getString("userId");
        String newPd = jsonObject.getString("newPasswd");
        String passwd = CryptoAES.aesEncrypt(newPd);
        return jdbcTemplate.update(SqlDefine.sys_rdbms_015,
                passwd, userId);
    }

    @Override
    public int changeStatus(String userId, String status) {
        return jdbcTemplate.update(SqlDefine.sys_rdbms_016, status, userId);
    }
}
