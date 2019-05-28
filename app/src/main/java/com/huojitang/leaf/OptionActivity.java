package com.huojitang.leaf;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.webkit.WebViewClient;


public class OptionActivity extends AppCompatActivity {
    WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);

        //初始化
        webView =findViewById(R.id.web_view);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl("file:////android_asset/localhost/option.html");

        //禁用跳转到默认浏览器
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });

        //暴露java方法
        webView.addJavascriptInterface(new JavaFunc(),"java");
    }

    //返回本窗口则调用JS加载FadeIn动画
    @Override
    protected void onResume() {
        super.onResume();
        webView.loadUrl("javascript:FadeIn()");
    }

    private class JavaFunc{
        @JavascriptInterface
        public void back(){
            OptionActivity.this.finish();
        }

        @JavascriptInterface
        public void about(){
            startActivity(new Intent(OptionActivity.this ,AboutActivity.class));
        }

        @JavascriptInterface
        public void tag(){
            startActivity(new Intent(OptionActivity.this ,EditTagActivity.class));
        }
    }

}
