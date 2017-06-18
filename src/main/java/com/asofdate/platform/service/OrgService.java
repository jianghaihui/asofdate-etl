package com.asofdate.platform.service;

import com.asofdate.platform.model.OrgModel;

import java.util.List;

/**
 * Created by hzwy23 on 2017/6/18.
 */
public interface OrgService {
    List<OrgModel> findAll(String domainId);
}
