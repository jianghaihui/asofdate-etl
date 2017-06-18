package com.asofdate.platform.dao;

import com.asofdate.platform.model.DomainModel;
import org.json.JSONArray;

import java.util.List;

/**
 * Created by hzwy23 on 2017/6/1.
 */
public interface DomainDao {
    List<DomainModel> findAll();
    List<DomainModel> getAll();
    int update(DomainModel domainModel);
    String delete(JSONArray jsonArray);
    int add(DomainModel domainModel);
}
