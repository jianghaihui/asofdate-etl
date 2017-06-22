package com.asofdate.platform.dao.impl;

import com.asofdate.platform.dao.RoleDao;
import com.asofdate.platform.model.RoleModel;
import com.asofdate.sql.SqlDefine;
import com.asofdate.utils.JoinCode;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by hzwy23 on 2017/6/18.
 */
@Repository
public class RoleDaoImpl implements RoleDao {
    private final Logger logger = LoggerFactory.getLogger(RoleDaoImpl.class);
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<RoleModel> findAll(String domainId) {
        RowMapper<RoleModel> rowMapper = new BeanPropertyRowMapper<>(RoleModel.class);
        return jdbcTemplate.query(SqlDefine.sys_rdbms_028, rowMapper, domainId);
    }

    @Override
    public RoleModel getDetails(String roleId) {
        RoleModel roleModel = new RoleModel();
        jdbcTemplate.query(SqlDefine.sys_rdbms_208, new RowCallbackHandler() {
            @Override
            public void processRow(ResultSet resultSet) throws SQLException {
                roleModel.setDomain_id(resultSet.getString("domain_id"));
                roleModel.setRole_id(resultSet.getString("role_id"));
                roleModel.setCode_number(resultSet.getString("code_number"));
                roleModel.setRole_name(resultSet.getString("role_name"));
                roleModel.setDomain_desc(resultSet.getString("domain_desc"));
            }
        }, roleId);
        return roleModel;
    }

    @Override
    public List<RoleModel> getOther(String userId) {
        RowMapper<RoleModel> rowMapper = new BeanPropertyRowMapper<>(RoleModel.class);
        return jdbcTemplate.query(SqlDefine.sys_rdbms_095, rowMapper, userId);
    }

    @Override
    public List<RoleModel> getOwner(String userId) {
        RowMapper<RoleModel> rowMapper = new BeanPropertyRowMapper<>(RoleModel.class);
        return jdbcTemplate.query(SqlDefine.sys_rdbms_094, rowMapper, userId);
    }

    @Transactional
    @Override
    public int auth(JSONArray jsonArray, String modifyUserId) {
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject arg = (JSONObject) jsonArray.get(i);
            String userId = arg.getString("user_id");
            String roleId = arg.getString("role_id");
            String uuid = JoinCode.join(userId, roleId);
            jdbcTemplate.update(SqlDefine.sys_rdbms_096, uuid, roleId, userId, modifyUserId);
        }
        return 1;
    }

    @Transactional
    @Override
    public int batchAuth(JSONArray jsonArray, String modifyUserId) {
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject arg = (JSONObject) jsonArray.get(i);
            String userId = arg.getString("user_id");
            String roleId = arg.getString("role_id");
            String uuid = JoinCode.join(userId, roleId);
            try {
                jdbcTemplate.update(SqlDefine.sys_rdbms_096, uuid, roleId, userId, modifyUserId);
            } catch (Exception e) {
                logger.info("用户[{}]已经拥有了角色[{}],无需重复授权", userId, roleId);
                logger.info(e.getMessage());
            }
        }
        return 1;
    }

    @Transactional
    @Override
    public int revoke(JSONArray jsonArray) {
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject arg = (JSONObject) jsonArray.get(i);
            String userId = arg.getString("user_id");
            String roleId = arg.getString("role_id");
            String uuid = JoinCode.join(userId, roleId);
            jdbcTemplate.update(SqlDefine.sys_rdbms_097, uuid);
        }
        return 1;
    }

    @Override
    public int add(RoleModel roleModel) {
        return jdbcTemplate.update(SqlDefine.sys_rdbms_026,
                roleModel.getRole_id(),
                roleModel.getRole_name(),
                roleModel.getCreate_user(),
                roleModel.getRole_status_code(),
                roleModel.getDomain_id(),
                roleModel.getModify_user(),
                roleModel.getCode_number());
    }

    @Override
    public int delete(JSONArray jsonArray) {
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = (JSONObject) jsonArray.get(i);
            String roleId = jsonObject.getString("role_id");
            String domainId = jsonObject.getString("domain_id");
            jdbcTemplate.update(SqlDefine.sys_rdbms_027, roleId, domainId);
        }
        return 1;
    }

    @Override
    public int update(RoleModel roleModel) {
        logger.debug("{},{},{},{}",
                roleModel.getRole_name(),
                roleModel.getRole_status_code(),
                roleModel.getModify_user(),
                roleModel.getRole_id());
        return jdbcTemplate.update(SqlDefine.sys_rdbms_050,
                roleModel.getRole_name(),
                roleModel.getRole_status_code(),
                roleModel.getModify_user(),
                roleModel.getRole_id());
    }
}
