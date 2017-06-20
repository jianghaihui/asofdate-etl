package com.asofdate.platform.dao;

import com.asofdate.platform.model.OrgModel;
import org.json.JSONArray;

import java.util.List;

/**
 * Created by hzwy23 on 2017/6/18.
 */
public interface OrgDao {
    List<OrgModel> findAll(String domainId);

    List<OrgModel> findSub(String domainId, String orgId);

    int add(OrgModel orgModel);

    int delete(JSONArray jsonArray);

    int update(OrgModel orgModel);
}
