package com.asofdate.platform.dao.impl;

import com.asofdate.platform.dao.DomainDao;
import com.asofdate.platform.dao.DomainShareDao;
import com.asofdate.platform.model.DomainModel;
import com.asofdate.platform.model.DomainShareModel;
import com.asofdate.sql.SqlDefine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by hzwy23 on 2017/6/1.
 */
@Repository
public class DomainShareDaoImpl implements DomainShareDao{

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private DomainDao domainDao;

    @Override
    public List<DomainShareModel> findAuth(String targetDomainID) {
        RowMapper<DomainShareModel> rowMapper = new BeanPropertyRowMapper<>(DomainShareModel.class);
        List<DomainShareModel> list = jdbcTemplate.query(SqlDefine.sys_rdbms_116,rowMapper,targetDomainID);
        return list;
    }

    @Override
    public List<DomainModel> findUnshareTo(String domainId) {
        List<DomainModel> list = domainDao.findAll();
        List<DomainShareModel> dslist = findShareTo(domainId);
        Set<String> set = new HashSet<>();
        for (DomainShareModel m: dslist){
            set.add(m.getTarget_domain_id());
        }
        for (int i = 0; i < list.size();i++){
            if (set.contains(list.get(i).getDomain_id())){
                list.remove(i);
                i--;
            }
        }
        return list;
    }

    @Override
    public List<DomainShareModel> findShareTo(String domainId) {
        RowMapper<DomainShareModel> rowMapper = new BeanPropertyRowMapper<>(DomainShareModel.class);
        List<DomainShareModel> list = jdbcTemplate.query(SqlDefine.sys_rdbms_117,rowMapper,domainId);
        return list;
    }

    @Override
    public Set<String> findAll(String targetDomainId) {
        RowMapper<DomainShareModel> rowMapper = new BeanPropertyRowMapper<>(DomainShareModel.class);
        List<DomainShareModel> list = jdbcTemplate.query(SqlDefine.sys_rdbms_116,rowMapper,targetDomainId);

        Set<String> set = new HashSet<>();
        set.add(targetDomainId);
        for (DomainShareModel m: list){
            set.add(m.getDomain_id());
        }
        return set;
    }

}
