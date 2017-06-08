package com.asofdate.utils;

import com.asofdate.sql.OracleDefine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.security.sasl.SaslServer;

/**
 * Created by hzwy23 on 2017/6/8.
 */
@Component
public class Adaptor {
    @Autowired
    private Environment env;

    private static Adaptor adaptor;

    @PostConstruct
    public void init(){
        adaptor = this;
        adaptor.env = this.env;
    }

    public static void initDb(){
        String dbname = adaptor.env.getProperty("spring.jpa.database");
        switch (dbname){
            case "oracle":
                OracleDefine.oracleInit();
                break;
        }
    }
}
