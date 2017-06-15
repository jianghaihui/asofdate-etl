package com.asofdate.dispatch;

import com.asofdate.sql.SqlDefine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;

/**
 * Created by hzwy23 on 2017/6/15.
 */
@Component
public class InitBatch {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    private static InitBatch initBatch;

    @Transactional
    public static void initBatchInfo(){
           initBatch.jdbcTemplate.update(SqlDefine.sys_rdbms_180);
    }

    @PostConstruct
    public void init(){
        this.initBatch = this;
        this.initBatch.jdbcTemplate = jdbcTemplate;
    }
}
