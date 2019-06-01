package com.huojitang.leaf.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.webkit.JavascriptInterface;
import android.webkit.ValueCallback;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.huojitang.leaf.R;
import com.huojitang.leaf.dao.WishDao;
import com.huojitang.leaf.global.LeafApplication;
import com.huojitang.leaf.model.Wish;
import com.huojitang.leaf.util.LeafDateSupport;

import java.time.LocalDate;
import java.time.Year;
import java.time.YearMonth;
import java.util.List;


/**
 * 心愿历史界面
 * 使用JSON来传递数据并回显在本地网页上
 * @author Mookiepiece
 */
public class WishHistoryActivity extends AppCompatActivity {
    WebView webView;
    WishDao wishDao= WishDao.getInstance();

    //心愿头尾日期
    private LocalDate firstWishStartTime;
    private LocalDate lastWishEndTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);

        boolean dateParseSuccessFlag=false;
        if(wishDao.getEarliestStartTime()!=null){
            firstWishStartTime=LeafDateSupport.parseFromLongDate(wishDao.getEarliestStartTime());

            if(wishDao.getLatestFinishedTime()!=null){
                lastWishEndTime=LeafDateSupport.parseFromLongDate(wishDao.getLatestFinishedTime());
            }
            else{
                lastWishEndTime=LeafDateSupport.getCurrentLocalDate();
            }
        }
        else{
            firstWishStartTime=lastWishEndTime=LeafDateSupport.getCurrentLocalDate();
        }


        //初始化
        webView =findViewById(R.id.web_view);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl("file:////android_asset/localhost/wish.production.html");

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


    private class JavaFunc{

        /**
         * 网页加载后会请求心愿的总开始和截至日期
         */
        @JavascriptInterface
        public void getBeginAndEndMonth() {

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    JSONObject startAndEndTime=new JSONObject();
                    JSONObject startTime=new JSONObject();
                    startTime.put("year",firstWishStartTime.getYear());
                    startTime.put("month",firstWishStartTime.getMonthValue());
                    JSONObject endTime=new JSONObject();
                    endTime.put("year",lastWishEndTime.getYear());
                    endTime.put("month",lastWishEndTime.getMonthValue());

                    startAndEndTime.put("FirstWishStartTime",startTime);
                    startAndEndTime.put("LastWishEndTime",endTime);

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            webView.evaluateJavascript("javascript:javaDataReceiver.getBeginAndEndMonth(\'"+startAndEndTime.toJSONString()+"\')", null);
                        }
                    });
                     }
            });
        }


        private static final String TAG = "JavaFunc";

        /**
         * 返回多个月的值
         * @param dateJsonPlus {data:{year,month}, length}
         */
        @JavascriptInterface
        public void javaGetMonthVal(String dateJsonPlus){
            JSONObject jsonObject=JSON.parseObject(dateJsonPlus);
            YearMonth yearMonth=parseDate(jsonObject);

            int length=jsonObject.getInteger("length");

            JSONArray jsonArray=new JSONArray();

            //先插入前一个月，后插入后一个月以防前端（雾）显示错误
            //本程序最大的错误就是将DATE用String储存且没有比较Date大小的方法，插入数据库后Date不透明
            for(int i=0;i<length;i++){
                yearMonth=LeafDateSupport.prevMonth(yearMonth);
            }
            for(int i=0;i<length;i++){
                jsonArray.add(loadMonthDataToJson(yearMonth));
                yearMonth=LeafDateSupport.nextMonth(yearMonth);
            }
            String str=jsonArray.toJSONString();

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    webView.evaluateJavascript("javascript:javaDataReceiver.javaGetMonthVal(\'"+str+"\')",null);
                }
            });
        }
        /**
         * 返回单个月的值
         * @param dateJson {date:{year,month}}
         */
        @JavascriptInterface
        public void loadMonth(String dateJson){
            JSONObject jsonObject=JSONObject.parseObject(dateJson);
            YearMonth ym=parseDate(jsonObject);

            JSONArray jsonArray=new JSONArray();
            jsonArray.add(loadMonthDataToJson(ym));
            String str=jsonArray.toJSONString();

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    webView.evaluateJavascript("javascript:javaDataReceiver.javaGetMonthVal(\'"+str+"\')",null);
                }
            });
        }
    }

    private YearMonth parseDate(JSONObject jsonObject){
        String year=jsonObject.getString("year");
        String month=jsonObject.getString("month");
        if(month.length()==1) month="0"+month;
        return LeafDateSupport.parseFromShortDate(year+'-'+month);
    }

    /**
     * 调用DAO取得某月的值
     * @param date
     * @return
     */
    private JSONObject loadMonthDataToJson(YearMonth date){

        List<Wish> l=wishDao.listByStartTime(date.toString());

        JSONObject jsonObject=new JSONObject();

        JSONObject dateJson = new JSONObject();
        dateJson.put("year",date.getYear());
        dateJson.put("month",date.getMonthValue());
        jsonObject.put("date",dateJson);

        JSONArray itemArray=new JSONArray();
        JSONObject wish;
        for(int i=0;i<l.size();i++){
            wish=new JSONObject();
            wish.put("name",l.get(i).getName());
            wish.put("state",l.get(i).getState());
            wish.put("id",l.get(i).getId());
            itemArray.add(wish);
        }
        jsonObject.put("item",itemArray);

        return jsonObject;
    }
}
