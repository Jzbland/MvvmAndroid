package com.jzb.netretrofit.netutil;

import android.util.Log;

import com.google.gson.Gson;

import java.util.Map;

import okhttp3.MediaType;
import okhttp3.RequestBody;
/**
 * created by jzb
 * on 2020/10/21
 **/
public class JsonRequestBody {

    public static RequestBody getRequestBody(Map<String, Object> map) {
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), getRequestJson(map));
        return body;
    }

    public static String getRequestJson(Map<String, Object> paramMap) {
        Gson gson = new Gson();
        String timestamp = String.valueOf(System.currentTimeMillis());
        paramMap.put("timestamp",timestamp);
        String jsonStr = gson.toJson(paramMap);
        Log.i("net", "getRequestJson: "+jsonStr);
        return jsonStr;
    }
}
