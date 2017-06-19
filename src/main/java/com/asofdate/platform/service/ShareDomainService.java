package com.asofdate.platform.service;

import com.asofdate.platform.model.ShareDomainModel;
import org.json.JSONArray;

import java.util.List;

/**
 * Created by hzwy23 on 2017/6/19.
 */
public interface ShareDomainService {
    List<ShareDomainModel> findAll(String domainId);

    List<ShareDomainModel> unShareTarget(String domainId);

    int add(ShareDomainModel shareDomainModel);

    int delete(JSONArray jsonArray);

    int update(ShareDomainModel shareDomainModel);
}
