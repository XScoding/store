package com.xs.store.demo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.xs.store.StoreObserver;

/**
 * Created by wangzhengtao767 on 2019/5/9.
 */

public class BaseActivity extends AppCompatActivity {

    protected StoreObserver storeObserver;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        storeObserver = new StoreObserver();
        storeObserver.onCreate();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        storeObserver.onDestroy();
    }


    protected void logTime(String lable) {
        Log.w("xsss",lable + " ----- " + System.currentTimeMillis());
    }
}
