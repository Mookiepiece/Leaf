package com.huojitang.leaf;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.webkit.JavascriptInterface;
import android.webkit.ValueCallback;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.huojitang.leaf.dao.WishDao;

import java.time.YearMonth;

public class WishHistoryActivity extends AppCompatActivity {

    private enum listMode{START_TIME,FINISHED_TIME};

    WebView webView;
    WishDao wishDao= WishDao.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);

        //初始化
        webView =findViewById(R.id.web_view);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl("file:////android_asset/localhost/wish.html");

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

    private JSONObject startAndEndTime=JSON.parseObject("{'StartTime':'2019-10','endTime':'2023-8'}");

    private class JavaFunc{

        @JavascriptInterface
        public void getBeginAndEndMonth() {
            webView.evaluateJavascript("javascript:javaDataLoader.getBeginAndEndMonth("+startAndEndTime.toString()+")", new ValueCallback<String>() {
                @Override
                public void onReceiveValue(String value) {

                }
            });
        }

        /**
         * 返回指定时间开始的个数
         * @param dateJson {data:{year,month}, num}
         */
        @JavascriptInterface
        public void LoadData(String dateJson){
            JSONObject jsonObject=JSON.parseObject(dateJson);



            String str="[{\"date\":{\"year\":2021,\"month\":12},\"item\":[{\"name\":\"item1\",\"state\":\"resolved\"},{\"name\":\"item2\",\"state\":\"cancelled\"},{\"name\":\"item2\",\"state\":\"cancelled\"},{\"name\":\"item2\",\"state\":\"resolved\"},{\"name\":\"item2\",\"state\":\"onprogress\"}]},{\"date\":{\"year\":2022,\"month\":1},\"item\":[{\"name\":\"item1\",\"state\":\"resolved\"},{\"name\":\"item2\",\"state\":\"resolved\"}]},{\"date\":{\"year\":2022,\"month\":2},\"item\":[{\"name\":\"item1\",\"state\":\"resolved\"},{\"name\":\"item2\",\"state\":\"resolved\"}]},{\"date\":{\"year\":2022,\"month\":3},\"item\":[{\"name\":\"item1\",\"state\":\"resolved\"},{\"name\":\"item2\",\"state\":\"resolved\"}]},{\"date\":{\"year\":2022,\"month\":4},\"item\":[{\"name\":\"item1\",\"state\":\"resolved\"},{\"name\":\"item2\",\"state\":\"resolved\"},{\"name\":\"item2\",\"state\":\"resolved\"},{\"name\":\"item2\",\"state\":\"resolved\"},{\"name\":\"item2\",\"state\":\"resolved\"},{\"name\":\"item2\",\"state\":\"resolved\"}]},{\"date\":{\"year\":2022,\"month\":5},\"item\":[{\"name\":\"item1\",\"state\":\"resolved\"},{\"name\":\"item2\",\"state\":\"resolved\"}]}]";

            webView.evaluateJavascript("javascript:javaDataLoader.javaGetMonthVal("+jsonObject.toString()+")", new ValueCallback<String>() {
                @Override
                public void onReceiveValue(String value) {

                }
            });
        }

        private void LoadMonthData(YearMonth date){

            JSONObject jsonObject=new JSONObject();

            JSONObject dateJson = new JSONObject();
            dateJson.put("year",date.getYear());
            dateJson.put("month",date.getMonthValue());
            jsonObject.put("date",dateJson);

            JSONArray itemArray=new JSONArray();
            JSONObject wish=new JSONObject();
            wish.put("name","b");
            wish.put("state",0);
            wish.put("id",666);

            itemArray.add(wish);
            jsonObject.put("wishes",itemArray);

            webView.evaluateJavascript("javascript:javaDataLoader.javaGetMonthVal("+jsonObject.toString()+")", new ValueCallback<String>() {
                @Override
                public void onReceiveValue(String value) {

                }
            });
        }

    }
}
