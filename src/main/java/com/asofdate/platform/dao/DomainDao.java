package com.asofdate.platform.dao;

import com.asofdate.platform.model.DomainModel;

import java.util.List;

/**
 * Created by hzwy23 on 2017/6/1.
 */
public interface DomainDao {
    List<DomainModel> findAll();
}
