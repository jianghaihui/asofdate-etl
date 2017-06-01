package com.asofdate.platform.dao.impl;

import com.asofdate.platform.dao.*;
import com.asofdate.platform.model.HomeMenuModel;
import com.asofdate.platform.model.ResourceModel;
import com.asofdate.platform.model.ThemeResourceModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.*;

/**
 * Created by hzwy23 on 2017/5/17.
 */
@Repository
public class HomeMenuDaoImpl implements HomeMenuDao {
    @Autowired
    public JdbcTemplate jdbcTemplate;

    @Autowired
    public UserThemeDao userThemeDao;

    @Autowired
    public ThemeResourceDao themeResourceDao;

    @Autowired
    public ResourceDao resourceDao;

    @Autowired
    private UserResourceDao userResourceDao;

    private String getThemeId(String userId) {
        return userThemeDao.findById(userId);
    }

    private List getSubResource(String resUpId) {
        List<ResourceModel> list = resourceDao.findSubByUpId(resUpId);
        return list;
    }

    private List getThemeResource(String themeId) {
        return themeResourceDao.findByThemeId(themeId);
    }

    /*
    * 将菜单资源树转从List转换成Map类型
    * 方便后边的匹配操作
    * key : res_id
    * value: ResourceModel
    * */
    private Map<String, ResourceModel> list2Map(List<ResourceModel> list, String typeId) {
        Map<String, ResourceModel> map = new HashMap<String, ResourceModel>();
        for (ResourceModel m : list) {
            if (m.getRes_type().equals(typeId)) {
                map.put(m.getRes_id(), m);
            }
        }
        return map;
    }

    @Override
    public List findById(String userId, String typeId, String resId) {

        Set<String> set = userResourceDao.findAll(userId);
        // 获取用户配置的主题信息
        String themeId = getThemeId(userId);

        //获取主题对应的资源信息
        List<ThemeResourceModel> list = getThemeResource(themeId);

        // 获取指定菜单的所以下级菜单信息
        List<ResourceModel> resourceList = getSubResource(resId);

        // 将指定资源的所有下级信息转换成Map
        // 方便后边匹配查询
        Map<String, ResourceModel> resourceMap = list2Map(resourceList, typeId);

        List<HomeMenuModel> rst = new ArrayList<HomeMenuModel>();
        for (ThemeResourceModel m : list) {
            if (!set.contains(m.getRes_id())) {
                continue;
            }
            if (resourceMap.containsKey(m.getRes_id())) {
                HomeMenuModel homeMenuModel = new HomeMenuModel();
                homeMenuModel.setGroup_id(m.getGroup_id());
                homeMenuModel.setRes_bg_color(m.getRes_bg_color());
                homeMenuModel.setRes_class(m.getRes_class());
                homeMenuModel.setRes_id(m.getRes_id());
                homeMenuModel.setRes_img(m.getRes_img().replaceFirst("^/static", ""));
                homeMenuModel.setRes_name(resourceMap.get(m.getRes_id()).getRes_name());
                homeMenuModel.setRes_open_type(m.getRes_type());
                homeMenuModel.setRes_url(m.getRes_url().replaceFirst("^/views", ""));
                rst.add(homeMenuModel);
            }
        }
        return rst;
    }
}
