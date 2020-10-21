package com.jzb.mvvmdemo.ui;

import android.os.Bundle;

import com.jzb.baselib.base.BaseActivity;
import com.jzb.mvvmdemo.R;
import com.jzb.mvvmdemo.databinding.ActivityMainBinding;
import com.jzb.mvvmdemo.viewmodel.MainVM;

public class MainActivity extends BaseActivity<MainVM, ActivityMainBinding> {

    @Override
    protected void initParms(Bundle bundle) {

    }

    @Override
    public int bindLayout() {
        return R.layout.activity_main;
    }

    @Override
    public void initData() {
        showToast("点击登陆");
        mViewDataBinding.setLoginVM(mVM);
    }
}