package com.asofdate.dispatch.dao;

import com.asofdate.dispatch.model.GroupArgumentModel;
import org.json.JSONArray;

import java.util.List;

/**
 * Created by hzwy23 on 2017/5/30.
 */
public interface GroupArgumentDao {
    List<GroupArgumentModel> findAll(String domainId);

    JSONArray getGroupArg(String id);

    int updateArg(String argValue,String uuid,String argId);
}
