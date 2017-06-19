package com.asofdate.platform.service;

import org.json.JSONObject;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by hzwy23 on 2017/6/19.
 */
public interface AuthService {
    JSONObject domainAuth(HttpServletRequest request, String domainId, String mode);

    JSONObject basicAuth(HttpServletRequest request);
}
