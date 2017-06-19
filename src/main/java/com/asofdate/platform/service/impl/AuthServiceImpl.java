package com.asofdate.platform.service.impl;

import com.asofdate.platform.authentication.JwtService;
import com.asofdate.platform.service.AuthService;
import com.asofdate.sql.SqlDefine;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by hzwy23 on 2017/6/19.
 */
@Service
public class AuthServiceImpl implements AuthService {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    private final String READ_MODE = "r";
    private final String WRITE_MODE = "w";

    private Integer checkMode(String mode){
        if ( mode.toLowerCase().equals(READ_MODE) ) {
            return 1;
        } else if (mode.toLowerCase().equals(WRITE_MODE)) {
            return 2;
        } else {
            return -1;
        }
    }

    private JSONObject result(Boolean flag,String message){
        JSONObject ret = new JSONObject();
        ret.put("status",flag);
        ret.put("message",message);
        return ret;
    }

    @Override
    public JSONObject domainAuth(HttpServletRequest request, String domainId, String mode) {
        String userDomainId = JwtService.getConnectUser(request).getString("DomainId");
        if (userDomainId.equals(domainId)){
            return result(true,"success");
        }
        try {
            Integer level = jdbcTemplate.queryForObject(SqlDefine.sys_rdbms_010,Integer.class,domainId,userDomainId);
            if (level == 2){
                return result(true,"success");
            } else if (level == 1 && checkMode(mode) == 2){
                return result(false,"只有读取权限,没有写入权限");
            } else if (level == 1 && checkMode(mode) == 1){
                return result(true,"success");
            }
            return result(false,"您没有被授权访问这个域");
        } catch (Exception e){
            return result(false,"您没有被授权访问域 ["+domainId+"]");
        }
    }

    @Override
    public JSONObject basicAuth(HttpServletRequest request) {
        return null;
    }
}
