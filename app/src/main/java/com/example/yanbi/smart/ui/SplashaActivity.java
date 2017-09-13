package com.example.yanbi.smart.ui;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.yanbi.smart.MainActivity;
import com.example.yanbi.smart.R;
import com.example.yanbi.smart.utils.ShareUtils;
import com.example.yanbi.smart.utils.StaticClass;
import com.example.yanbi.smart.utils.UtilsTools;

/**
 * Created by yanbi on 2017/9/10.
 */

public class SplashaActivity extends AppCompatActivity {

    private TextView tv_splash;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case StaticClass.HANDLER_SPLASH:
                    if (isFirst()) {
                        startActivity(new Intent(SplashaActivity.this, GuideActivity.class));
                    } else {
                        startActivity(new Intent(SplashaActivity.this, LoginActivity.class));
//                        startActivity(new Intent(SplashaActivity.this, MainActivity.class));
                    }
                    finish();
                    break;
            }
        }
    };


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        initView();
    }

    private void initView() {
        handler.sendEmptyMessageDelayed(StaticClass.HANDLER_SPLASH, 2000);
        tv_splash = (TextView) findViewById(R.id.tv_splash);

        //设置字体
        UtilsTools.setFont(this,tv_splash);
    }

    private boolean isFirst() {
        boolean isFirst = ShareUtils.getBoolean(this, StaticClass.SHARE_IS_FIRST, true);
        if (isFirst) {
            ShareUtils.putBoolean(this, StaticClass.SHARE_IS_FIRST, false);
            return true;
        } else {
            return false;
        }
    }

    //禁止返回键
    @Override
    public void onBackPressed() {
//        super.onBackPressed();
    }
}
