package com.llh.smartbutler.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.llh.smartbutler.R;

public class WebViewActivity extends BaseActivity {
    private WebView mWebView;
    private ProgressBar mProgressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        initView();
    }

    private void initView() {
        mWebView = findViewById(R.id.mWebView);
        mProgressBar = (ProgressBar)findViewById(R.id.mProgress);
        Intent intent = getIntent();
        String title = intent.getStringExtra("title");
        final String url = intent.getStringExtra("url");
        //设置标题
        getSupportActionBar().setTitle(title);
        //网页加载逻辑
        //设置支持js脚本
        mWebView.getSettings().setJavaScriptEnabled(true);
        //设置支持缩放
        mWebView.getSettings().setSupportZoom(true);
        //设置控制器
        mWebView.getSettings().setBuiltInZoomControls(true);
        //设置本地的显示，进度条加载监听
        mWebView.setWebChromeClient(new WebViewClient());
        //加载网页
        mWebView.loadUrl(url);
        //设置本地webView控件中显示网页
        mWebView.setWebViewClient(new android.webkit.WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                view.loadUrl(url);
                return true;
            }
        });
    }
    //进度条加载监听
    public class WebViewClient extends WebChromeClient{
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            if(newProgress == 100)
            {
                mProgressBar.setVisibility(View.GONE);
            }
            super.onProgressChanged(view, newProgress);
        }
    }
}
