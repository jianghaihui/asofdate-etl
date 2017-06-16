package com.asofdate.dispatch.dao;

import com.asofdate.dispatch.model.BatchHistoryModel;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by hzwy23 on 2017/6/16.
 */
public interface BatchHistoryDao {
    List<BatchHistoryModel> findAll(String domainId);
    int delete(JSONArray jsonArray);
}
