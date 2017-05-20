package com.asofdate.service;

import com.asofdate.model.HomeMenuModel;

import java.util.List;

/**
 * Created by hzwy23 on 2017/5/17.
 */
public interface HomeMenuService {
    public List<HomeMenuModel> findAuthedMenus(String userId,String typeId,String resId);
}
