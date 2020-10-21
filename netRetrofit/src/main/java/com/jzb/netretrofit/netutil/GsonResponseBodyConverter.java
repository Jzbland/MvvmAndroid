package com.jzb.netretrofit.netutil;

import android.util.Log;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Type;

import okhttp3.ResponseBody;
import retrofit2.Converter;
/**
 * created by jzb
 * on 2020/10/21
 **/
public class GsonResponseBodyConverter<T> implements Converter<ResponseBody, T> {
    Gson gson;
    Type type;

    public GsonResponseBodyConverter(Gson gson, Type type) {
        this.gson = gson;
        this.type = type;
    }

    @Override
    public T convert(ResponseBody value) throws IOException {
        String response = value.string();
        value.close();
        Log.e("zxjt", "convert: "+response);
        T responseBody = getResponseBody(response);
        return responseBody;

    }

    private T getResponseBody(String response) {

        if (type instanceof Class) {
            Class clazz = (Class) type;
            //如果返回类型 为JsonObject
            if (clazz.equals(JSONObject.class)) {
                try {
                    return (T) new JSONObject(response);
                } catch (JSONException e) {
                }
            }
            if (clazz.equals(JSONArray.class)) {
                try {
                    return (T) new JSONArray(response);
                } catch (JSONException e) {
                }
            }
        }
        return gson.fromJson(response, type);
    }
}
