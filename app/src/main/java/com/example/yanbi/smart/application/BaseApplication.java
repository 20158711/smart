package com.example.yanbi.smart.application;

import android.app.Application;

import com.example.yanbi.smart.utils.StaticClass;
import com.tencent.bugly.crashreport.CrashReport;

import cn.bmob.v3.Bmob;

/**
 * Created by yanbi on 2017/9/10.
 */

public class BaseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        //初始化bugly
        CrashReport.initCrashReport(getApplicationContext(), StaticClass.BUGLY_APP_ID, true);

        //初始化bmob
        Bmob.initialize(this, StaticClass.BMOB_APP_ID);
    }
}
