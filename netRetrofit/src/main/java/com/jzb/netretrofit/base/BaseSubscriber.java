package com.jzb.netretrofit.base;

import android.content.Context;
import android.util.Log;

import com.jzb.netretrofit.util.ApiException;

import java.lang.ref.WeakReference;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import io.reactivex.annotations.NonNull;
import io.reactivex.observers.DisposableObserver;

/**
 * created by jzb
 * on 2020/10/21
 *
 * 统一处理error
 * 预防内存溢出
 *
 **/
public abstract class BaseSubscriber<T> extends DisposableObserver<T> {

    private static final String TAG = "BaseSubscriber";
    
    private WeakReference<Context> contextWeakReference;

    public  BaseSubscriber(){}

    public BaseSubscriber(Context context){
        if (context!=null){
            contextWeakReference = new WeakReference<>(context);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (contextWeakReference != null && contextWeakReference.get() != null){
            onComplete();
        }
    }

    @Override
    public void onNext(@NonNull T t) {
        Log.e(TAG, "onNext: "+t.toString() );
        onSuccessNext(t);
    }

    @Override
    public void onError(@NonNull Throwable e) {
        Log.e("http->error","-->http is onError");
        if (e instanceof ApiException) {
            Log.e("http->error","-->e instanceof ApiException err:"+e);
            ApiException.handleException(e);
        } else {
            Log.e("http->error","-->e !instanceof ApiException err:"+e);
        }
    }

    @Override
    public void onComplete() {
        dispose();
        onAfter();
    }

    private void onAfter() {
    }

    public String getActionBeanName(){
        try {
            Class clz = this.getClass();
            Type type = clz.getGenericSuperclass();
            ParameterizedType pt = (ParameterizedType)type;
            Class modelClass = (Class)pt.getActualTypeArguments()[0];
            T model = (T) modelClass.getConstructor().newInstance();
            return model.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    public abstract void onSuccessNext(T t);

}
