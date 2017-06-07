package com.asofdate.dispatch.dao;

import org.json.JSONArray;

import java.util.List;

/**
 * Created by hzwy23 on 2017/5/24.
 */
public interface BatchGroupDao {
    List findAll(String domainId);

    JSONArray getGroup(String batchId);

    int addGroup(JSONArray jsonArray);

    int deleteGroup(JSONArray jsonArray);
}
