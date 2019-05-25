package com.huojitang.leaf;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.webkit.WebViewClient;


public class OptionActivity extends AppCompatActivity {
    WebView wv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);

        //初始化
        wv=findViewById(R.id.web_view);
        wv.getSettings().setJavaScriptEnabled(true);
        wv.loadUrl("file:////android_asset/localhost/option.html");

        //禁用跳转到默认浏览器
        wv.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });

        //暴露java方法
        wv.addJavascriptInterface(new JavaFunc(),"java");
    }

    //返回本窗口则调用JS加载FadeIn动画
    @Override
    protected void onResume() {
        super.onResume();
        wv.loadUrl("javascript:FadeIn()");
    }

    private class JavaFunc{
        @JavascriptInterface
        public void Back(){
            OptionActivity.this.finish();
        }

        @JavascriptInterface
        public void About(){
            startActivity(new Intent(OptionActivity.this ,AboutActivity.class));
        }

        @JavascriptInterface
        public void Tag(){
            startActivity(new Intent(OptionActivity.this ,EditTagActivity.class));
        }
    }

}
