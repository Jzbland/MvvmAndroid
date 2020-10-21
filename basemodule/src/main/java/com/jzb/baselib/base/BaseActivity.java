package com.jzb.baselib.base;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProviders;

import java.lang.reflect.ParameterizedType;
/**
 * created by jzb
 * on 2020/10/21
 * BaseActivity
 **/
@SuppressWarnings("ALL")
public abstract class BaseActivity<VM extends ViewModel,VDB extends ViewDataBinding> extends AppCompatActivity {

    protected VM mVM;
    protected VDB mViewDataBinding;


    /**
     * 日志输出标志
     **/
    protected final String TAG = this.getClass().getSimpleName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //取消标题
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //取消状态栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        if (getSupportActionBar() != null){
            getSupportActionBar().hide();
        }
        setContentView(bindLayout());
        Bundle bundle = getIntent().getExtras();
        initParms(bundle);

        mViewDataBinding = DataBindingUtil.setContentView(this, bindLayout());
        mViewDataBinding.setLifecycleOwner(this);
        //获得泛型参数的实际类型
        Class<VM> vmClass = (Class<VM>) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        mVM = ViewModelProviders.of(this).get(vmClass);

        initData();
    }

    protected abstract void initParms(Bundle bundle);

    /**
     * [绑定布局]
     *
     * @return
     */
    public abstract int bindLayout();

    /**
     * [数据加载]
     *
     */
    public abstract void initData();

    /**
     * [页面跳转]
     *
     * @param clz
     */
    public void startActivity(Class<?> clz) {
        startActivity(new Intent(BaseActivity.this, clz));
    }

    /**
     * [页面跳转并关闭当前界面]
     *
     * @param clz
     */
    public void startActivity(Class<?> clz, boolean isFinish) {
        startActivity(new Intent(BaseActivity.this, clz));
        if (isFinish) {
            this.finish();
        }
    }

    /**
     * [携带数据的页面跳转]
     *
     * @param clz
     * @param bundle
     */
    public void startActivity(Class<?> clz, Bundle bundle) {
        Intent intent = new Intent();
        intent.setClass(this, clz);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    /**
     * [含有Bundle通过Class打开编辑界面]
     *
     * @param cls
     * @param bundle
     * @param requestCode
     */
    public void startActivityForResult(Class<?> cls, Bundle bundle,
                                       int requestCode) {
        Intent intent = new Intent();
        intent.setClass(this, cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivityForResult(intent, requestCode);
    }
    protected void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    protected void showLogi(String msg){
        Log.i(TAG, "showLogi: "+msg);
    }
    protected void showLoge(String msg){
        Log.e(TAG, "showLoge: "+msg);
    }
    protected void showLogd(String msg){
        Log.d(TAG, "showLogd: "+msg);
    }
}
