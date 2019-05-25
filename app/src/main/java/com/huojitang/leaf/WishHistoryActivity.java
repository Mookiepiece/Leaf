package com.huojitang.leaf;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class WishHistoryActivity extends AppCompatActivity {
    WebView wv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);

        //初始化
        wv=findViewById(R.id.web_view);
        wv.getSettings().setJavaScriptEnabled(true);
        wv.loadUrl("file:////android_asset/localhost/wish.html");

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

    private class JavaFunc{

        @JavascriptInterface
        public void LoadInitialData(){

        }

        @JavascriptInterface
        public void LoadMonthData(){

        }






        //        startActivity(new Intent(WishHistoryActivity.this ,EditTagActivity.class));
//        WishHistoryActivity.this.finish();
    }
}
