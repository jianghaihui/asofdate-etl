package com.asofdate.platform.service;

import com.asofdate.platform.model.DomainModel;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by hzwy23 on 2017/6/1.
 */
public interface DomainService {
    JSONObject findAll(String domainId);
    List<DomainModel> getAll();
    int update(DomainModel domainModel);
    String delete(JSONArray jsonArray);
    int add(DomainModel domainModel);
}
