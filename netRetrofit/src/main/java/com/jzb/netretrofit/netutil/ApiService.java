package com.jzb.netretrofit.netutil;

import com.jzb.netretrofit.bean.LoginBean;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
/**
 * created by jzb
 * on 2020/10/21
 **/
public interface ApiService {
    @POST("/login")
    Observable<LoginBean> login(@Body RequestBody requestBody);
}
