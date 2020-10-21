package com.jzb.mvvmdemo.viewmodel;

import com.jzb.baselib.viewmodel.BaseViewMode;
import com.jzb.mvvmdemo.util.LogUtil;
import com.jzb.netretrofit.RetrofitEngine;
import com.jzb.netretrofit.base.BaseSubscriber;
import com.jzb.netretrofit.bean.LoginBean;
import com.jzb.netretrofit.netutil.JsonRequestBody;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * created by jzb
 * on 2020/10/21
 **/
public class MainVM  extends BaseViewMode {

    private static final String TAG = "MainVM";

    public void login(){
        Map map=new HashMap();
        RetrofitEngine.getInstance().login(JsonRequestBody.getRequestBody(map)).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<LoginBean>() {
                    @Override
                    public void onSuccessNext(LoginBean loginBean) {
                        if (loginBean.getCode().equals("0")){
                            LogUtil.i("----->登陆成功");
                        }else {
                            LogUtil.i("----->登陆失败");
                        }
                    }
                });
    }

}
