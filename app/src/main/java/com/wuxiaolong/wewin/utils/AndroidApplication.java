package com.wuxiaolong.wewin.utils;

import android.app.Application;

import com.squareup.leakcanary.LeakCanary;

/**
 * Created by Administrator
 * on 2016/11/28.
 */

public class AndroidApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        LeakCanary.install(this);
        // Normal app init code...
    }
}
