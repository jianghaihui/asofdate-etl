package com.asofdate.dispatch.dao.impl;

import com.asofdate.dispatch.dao.TaskDependencyDao;
import com.asofdate.dispatch.model.GroupTaskModel;
import com.asofdate.dispatch.model.TaskDependencyModel;
import com.asofdate.sql.SqlDefine;
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
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by hzwy23 on 2017/5/27.
 */
@Repository
public class TaskDependencyDaoImpl implements TaskDependencyDao {
    private final Logger logger = LoggerFactory.getLogger(TaskDependencyDaoImpl.class);
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<TaskDependencyModel> findAll(String domainId) {
        RowMapper<TaskDependencyModel> rowMapper = new BeanPropertyRowMapper<TaskDependencyModel>(TaskDependencyModel.class);
        List<TaskDependencyModel> list = jdbcTemplate.query(SqlDefine.sys_rdbms_113, rowMapper, domainId);
        return list;
    }

    @Override
    public List<TaskDependencyModel> findById(String domainId, String batchId) {
        List<TaskDependencyModel> list = findAll(domainId);
        for (int i = 0; i < list.size(); i++) {
            if (batchId.equals(list.get(i))) {
                list.remove(i);
                i--;
            }
        }
        return list;
    }

    @Transactional
    @Override
    public JSONArray getTaskDependency(String id) {
        JSONArray jsonArray = new JSONArray();
        logger.info("id is :" + id);
        jdbcTemplate.query(SqlDefine.sys_rdbms_134, new RowCallbackHandler() {
            @Override
            public void processRow(ResultSet resultSet) throws SQLException {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("uuid", resultSet.getString("uuid"));
                jsonObject.put("id", resultSet.getString("id"));
                jsonObject.put("up_id", resultSet.getString("up_id"));
                jsonObject.put("domain_id", resultSet.getString("domain_id"));
                jsonObject.put("group_id", resultSet.getString("group_id"));
                jsonObject.put("task_id", resultSet.getString("task_id"));
                jsonObject.put("task_desc", resultSet.getString("task_desc"));
                jsonObject.put("code_number", resultSet.getString("code_number"));
                jsonArray.put(jsonObject);
            }
        }, id);
        return jsonArray;
    }

    @Override
    public List<GroupTaskModel> getGroupTasks(String groupId,String id) {
        logger.info("groupId is :" + groupId);
        RowMapper<GroupTaskModel> rowMapper = new BeanPropertyRowMapper<>(GroupTaskModel.class);
        List<GroupTaskModel> list = jdbcTemplate.query(SqlDefine.sys_rdbms_150, rowMapper, groupId);
        Set<String> set = getChildren(groupId,id);
        for(String m : set){
            System.out.println(m);
        }
        for (int i = 0; i < list.size(); i++){
            String subid = list.get(i).getId();
            if (set.contains(subid)) {
                list.remove(i);
                i--;
            }
        }
        return list;
    }

    @Override
    public int addTaskDependency(JSONArray jsonArray) {
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = (JSONObject) jsonArray.get(i);

            if (1 != jdbcTemplate.update(SqlDefine.sys_rdbms_151,
                    jsonObject.getString("id"),
                    jsonObject.getString("up_id"),
                    jsonObject.getString("domain_id"))) {
                return -1;
            }
        }
        return 1;
    }

    private Set<String> getChildren(String groupId, String id){
        Set<String> set = new HashSet<>();
        RowMapper<TaskDependencyModel> rowMapper = new BeanPropertyRowMapper<>(TaskDependencyModel.class);
        List<TaskDependencyModel> list = jdbcTemplate.query(SqlDefine.sys_rdbms_075,rowMapper,groupId);
        children(list,id,set);
        set.add(id);
        return set;
    }

    private void children(List<TaskDependencyModel> all,String id,Set<String> set){
        for ( TaskDependencyModel m:all ) {
            String upId = m.getUpId();
            if (upId == null || set.contains(m.getId())){
                continue;
            }
            if (upId.equals(id)){
                set.add(m.getId());
                children(all,m.getId(),set);
            }
        }
    }

    @Override
    public int deleteTaskDependency(String uuid) {
        return jdbcTemplate.update(SqlDefine.sys_rdbms_152, uuid);
    }
}
