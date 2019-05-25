package com.huojitang.global;

import android.content.Context;
import org.litepal.LitePalApplication;


/**
 * 全局的Context，其他地方需要Context时调用
 */
public class LeafApplication extends LitePalApplication {
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
