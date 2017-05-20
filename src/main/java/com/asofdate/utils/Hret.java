package com.asofdate.utils;

import org.json.JSONObject;

/**
 * Created by hzwy23 on 2017/5/18.
 */
public class Hret {
    public static String error(int status,String message,Object result){
        JSONObject jsonObject = new JSONObject(){{
            put("error_code", status);
            put("error_msg", message);
            put("error_details", result);
        }};
        return jsonObject.toString();
    }
    public static String success(int status,String message,Object result){
        JSONObject jsonObject = new JSONObject(){{
            put("reply_code", status);
            put("reply_msg", message);
            put("data", result);
        }};
        return jsonObject.toString();
    }
}
