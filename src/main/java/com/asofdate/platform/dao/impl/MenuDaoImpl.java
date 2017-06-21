package com.asofdate.platform.dao.impl;

import com.asofdate.platform.dao.MenuDao;
import com.asofdate.platform.dao.ResourceDao;
import com.asofdate.platform.model.MenuModel;
import com.asofdate.platform.model.ResourceModel;
import com.asofdate.platform.model.ThemeValueModel;
import com.asofdate.sql.SqlDefine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by hzwy23 on 2017/6/18.
 */
@Repository
public class MenuDaoImpl implements MenuDao {
    private final Logger logger = LoggerFactory.getLogger(MenuDaoImpl.class);
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private ResourceDao resourceDao;

    @Override
    public List<MenuModel> findAll() {
        RowMapper<MenuModel> rowMapper = new BeanPropertyRowMapper<>(MenuModel.class);
        return jdbcTemplate.query(SqlDefine.sys_rdbms_071, rowMapper);
    }

    @Override
    public MenuModel getDetails(String resId) {
        RowMapper<MenuModel> rowMapper = new BeanPropertyRowMapper<>(MenuModel.class);
        List<MenuModel> list = jdbcTemplate.query(SqlDefine.sys_rdbms_089, rowMapper,resId);
        if ( list.size() == 1 ) {
            return list.get(0);
        }
        return null;
    }

    @Transactional
    @Override
    public String add(ThemeValueModel themeValueModel) {
        try {
            jdbcTemplate.update(SqlDefine.sys_rdbms_072,
                    themeValueModel.getRes_id(),
                    themeValueModel.getRes_name(),
                    themeValueModel.getRes_attr(),
                    themeValueModel.getRes_up_id(),
                    themeValueModel.getRes_type());

            String resType = themeValueModel.getRes_type();
            if ("0".equals(resType) || "1".equals(resType) || "2".equals(resType)) {
                jdbcTemplate.update(SqlDefine.sys_rdbms_008,
                        themeValueModel.getTheme_id(),
                        themeValueModel.getRes_id(),
                        themeValueModel.getRes_url(),
                        themeValueModel.getRes_open_type(),
                        themeValueModel.getRes_bg_color(),
                        themeValueModel.getRes_class(),
                        themeValueModel.getGroup_id(),
                        themeValueModel.getRes_img(),
                        themeValueModel.getSort_id());
            }
            return "success";
        } catch (Exception e){
            logger.info(e.getMessage());
            return e.getMessage();
        }
    }

    @Transactional
    @Override
    public String delete(String resId) {
        List<ResourceModel> list = resourceDao.findSubByUpId(resId);
        try {
            jdbcTemplate.update(SqlDefine.sys_rdbms_077,resId);
            for(ResourceModel m: list){
                jdbcTemplate.update(SqlDefine.sys_rdbms_077,m.getRes_id());
            }
            return "success";
        } catch (Exception e) {
            logger.info(e.getMessage());
            return e.getMessage();
        }
    }

    @Override
    public String update(String resId, String resDesc,String resUpId) {
        List<ResourceModel> list = resourceDao.findSubByUpId(resId);
        for (ResourceModel m: list){
            if (resUpId.equals(m.getRes_id())){
                return "不能将菜单的上级编码设置成自己的下级菜单";
            }
        }
        jdbcTemplate.update(SqlDefine.sys_rdbms_005,resDesc,resUpId,resId);
        return "success";
    }

    @Override
    public ThemeValueModel getThemeDetails(String themeId, String resId) {
        RowMapper<ThemeValueModel> rowMapper = new BeanPropertyRowMapper<>(ThemeValueModel.class);
        List<ThemeValueModel> list = jdbcTemplate.query(SqlDefine.sys_rdbms_070, rowMapper,themeId,resId);
        if (list.size() == 1){
            return list.get(0);
        }
        return null;
    }

    @Override
    public String updateTheme(ThemeValueModel themeValueModel) {
        String resId = themeValueModel.getRes_id();
        String themeId = themeValueModel.getTheme_id();
        Integer cnt = jdbcTemplate.queryForObject(SqlDefine.sys_rdbms_006,Integer.class,themeId,resId);
        if (cnt == 0){
            try {
                jdbcTemplate.update(SqlDefine.sys_rdbms_008,
                        themeValueModel.getTheme_id(),
                        themeValueModel.getRes_id(),
                        themeValueModel.getRes_url(),
                        themeValueModel.getRes_open_type(),
                        themeValueModel.getRes_bg_color(),
                        themeValueModel.getRes_class(),
                        themeValueModel.getGroup_id(),
                        themeValueModel.getRes_img(),
                        themeValueModel.getSort_id());
                return "success";
            } catch (Exception e){
                logger.info(e.getMessage());
                return e.getMessage();
            }
        }
        try {
            jdbcTemplate.update(SqlDefine.sys_rdbms_009,
                    themeValueModel.getRes_url(),
                    themeValueModel.getRes_bg_color(),
                    themeValueModel.getRes_class(),
                    themeValueModel.getRes_img(),
                    themeValueModel.getGroup_id(),
                    themeValueModel.getSort_id(),
                    themeValueModel.getRes_open_type(),
                    themeValueModel.getTheme_id(),
                    themeValueModel.getRes_id());
            return "success";
        } catch (Exception e){
            logger.info(e.getMessage());
            return e.getMessage();
        }
    }
}
