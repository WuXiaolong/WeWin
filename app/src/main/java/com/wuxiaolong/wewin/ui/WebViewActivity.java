package com.wuxiaolong.wewin.ui;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.wuxiaolong.androidutils.library.LogUtil;
import com.wuxiaolong.wewin.utils.AppConstants;
import com.xiaomolongstudio.wewin.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WebViewActivity extends BaseActivity {
    private String webviewTitle;
    private String webviewUrl;
    private String webviewUrlData;
    @BindView(R.id.webview)
    WebView webview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        ButterKnife.bind(this);
        webviewTitle = this.getIntent().getStringExtra(AppConstants.WEBVIEW_TITLE);
        webviewUrl = this.getIntent().getStringExtra(AppConstants.WEBVIEW_URL);
        webviewUrlData = this.getIntent().getStringExtra(AppConstants.WEBVIEW_URL_DATA);
        initToolbar(webviewTitle);
        if (!TextUtils.isEmpty(webviewUrl)) {
            webview.loadUrl(webviewUrl);
        }
        if (!TextUtils.isEmpty(webviewUrlData)) {
            webview.loadData(webviewUrlData, "text/html; charset=UTF-8", null);
        }
        LogUtil.d("webviewUrl=" + webviewUrl);
        WebSettings webSettings = webview.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webview.setWebViewClient(
                new WebViewClient() {

                    @Override
                    public void onPageStarted(WebView view, String url, Bitmap favicon) {
                        super.onPageStarted(view, url, favicon);
                    }

                    @Override
                    public void onPageFinished(WebView view, String url) {
                        super.onPageFinished(view, url);
                        LogUtil.d("onPageFinished=");
                    }

                    @Override
                    public void onReceivedHttpError(WebView view, WebResourceRequest request, WebResourceResponse errorResponse) {
                        super.onReceivedHttpError(view, request, errorResponse);
                        LogUtil.d("onReceivedHttpError");
                    }

                    @Override
                    public boolean shouldOverrideUrlLoading(WebView view, String url) {
                        webview.setVisibility(View.VISIBLE);
                        LogUtil.d("shouldOverrideUrlLoading");
//                        view.loadUrl(url);
//                        return super.shouldOverrideUrlLoading(view, url);
                        return true;
                    }

                    @Override
                    public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                        // Handle the error
                        LogUtil.d("onReceivedError errorCode=" + errorCode);
                        webview.setVisibility(View.GONE);

                    }

                    @Override
                    public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                        super.onReceivedError(view, request, error);
                        //表示此方法无效
                    }
                }
        );
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((KeyEvent.KEYCODE_BACK == keyCode) && webview.canGoBack()) {
            webview.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
