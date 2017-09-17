package com.example.yanbi.smart.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.widget.ProgressBar;

import com.example.yanbi.smart.R;

/**
 * Created by yanbi on 2017/9/17.
 */

public class WebViewActivity extends BaseActivity {

    private WebView mWebView;
    private ProgressBar mProgressBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);

        initView();
    }

    private void initView() {
        mProgressBar= (ProgressBar) findViewById(R.id.mProgressBar);
        mWebView= (WebView) findViewById(R.id.mWebView);
        Intent intent=getIntent();
        String title = intent.getStringExtra("title");
        final String url = intent.getStringExtra("url");

        //设置标题
        getSupportActionBar().setTitle(title);

        //加载网页
        //支持JS
        mWebView.getSettings().setJavaScriptEnabled(true);
        //支持缩放
        mWebView.getSettings().setSupportZoom(true);
        mWebView.getSettings().setBuiltInZoomControls(true);
        //接口回调
        mWebView.setWebChromeClient(new WebViewClient());
        //加载网页
        mWebView.loadUrl(url);
        //本场显示
        mWebView.setWebViewClient(new android.webkit.WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                view.loadUrl(url);
                //我接受这个事件
                return true;
            }
        });


    }

    class WebViewClient extends WebChromeClient{
        //进度变化的监听

        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            if(newProgress==100){
                mProgressBar.setVisibility(View.GONE);
            }
            super.onProgressChanged(view, newProgress);
        }
    }
}
