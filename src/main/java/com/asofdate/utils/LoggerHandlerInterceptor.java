package com.asofdate.utils;

import com.asofdate.platform.authentication.JwtService;
import com.asofdate.sql.SqlDefine;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * Created by hzwy23 on 2017/6/19.
 */
@Component
public class LoggerHandlerInterceptor implements HandlerInterceptor {
    private final Logger logger = LoggerFactory.getLogger(LoggerHandlerInterceptor.class);
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
        JSONObject httpConn = JwtService.getConnectUser(httpServletRequest);
        String userId = httpConn.getString("UserId");
        String domainId = httpConn.getString("DomainId");
        String clientIp = httpServletRequest.getRemoteAddr();
        Integer statuCd = httpServletResponse.getStatus();
        String method = httpServletRequest.getMethod();
        String uri = httpServletRequest.getRequestURI();
        Map<String,String[]> map = httpServletRequest.getParameterMap();
        JSONObject jsonObject = new JSONObject();
        for(Map.Entry<String,String[]> m : map.entrySet()){
            jsonObject.put(m.getKey(),m.getValue());
        }
        jdbcTemplate.update(SqlDefine.sys_rdbms_207,userId,clientIp,statuCd,method,uri,jsonObject.toString(),domainId);
    }
}
