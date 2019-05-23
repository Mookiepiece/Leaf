package com.huojitang.global;

import android.app.Application;
import android.content.Context;


/**
 * 全局的Context，其他地方需要Context时调用
 */
public class LeafApplication extends Application {
    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        //获取context
        mContext = getApplicationContext();
    }
    //创建一个静态的方法，以便获取context对象
    public static Context getContext(){
        return mContext;
    }
}
