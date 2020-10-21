package com.jzb.netretrofit;


import com.google.gson.Gson;
import com.jzb.netretrofit.base.BaseAPI;
import com.jzb.netretrofit.netutil.ApiService;
import com.jzb.netretrofit.netutil.ResponseConverterFactory;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * created by jzb
 * on 2020/10/21
 *  Retrofit发动机 直接起飞
 **/
public class RetrofitEngine {

    private static RetrofitEngine retrofitEngine;
    private Retrofit retrofit;
    public volatile static ApiService apiService;

    private RetrofitEngine() {

        OkHttpClient.Builder builder = new OkHttpClient.Builder().connectTimeout(30, TimeUnit.SECONDS).readTimeout(30, TimeUnit.SECONDS).writeTimeout(10, TimeUnit.SECONDS);

        builder.addNetworkInterceptor(new NetInterceptor()).retryOnConnectionFailure(true).addNetworkInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.HEADERS)).hostnameVerifier(new HostnameVerifier() {
            @Override
            public boolean verify(String hostname, SSLSession session) {
                return true;
            }
        });
        HttpLoggingInterceptor.Level level = HttpLoggingInterceptor.Level.BODY;
        //新建log拦截器
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
            }
        });
        loggingInterceptor.setLevel(level);
        builder.addInterceptor(loggingInterceptor);

        OkHttpClient httpClient = builder.build();


        ScalarsConverterFactory scalarsConverterFactory = ScalarsConverterFactory.create();
        retrofit = new Retrofit.Builder()
                .addConverterFactory(ResponseConverterFactory.create()).addConverterFactory(GsonConverterFactory.create(new Gson()))
                .addConverterFactory(scalarsConverterFactory)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(httpClient)
                .baseUrl(BaseAPI.BASE_URL)
                .build();
        apiService = retrofit.create(ApiService.class);
    }


    class NetInterceptor implements Interceptor {

        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request().newBuilder().addHeader("Connection", "close").build();
            return chain.proceed(request);
        }
    }


    /**
     * 创建单例
     *
     * @return
     */
    public static ApiService getInstance() {
        if (retrofitEngine == null) {
            synchronized (RetrofitEngine.class) {
                retrofitEngine = new RetrofitEngine();
            }
        }
        return apiService;

    }
}