package com.huojitang.leaf.global;

import android.content.Context;
import android.content.SharedPreferences;

import org.litepal.LitePalApplication;


/**
 * 全局的Context，其他地方需要Context时调用
 */
public class LeafApplication extends LitePalApplication {
    private static Context mContext;
    private static SharedPreferences preferences;

    public static final String LEAF_MASSAGE = "com.huojitang.leaf.MESSAGE";

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
        preferences = LeafApplication.getContext().getSharedPreferences("leaf-data", Context.MODE_PRIVATE);
    }

    //全局获取Context对象
    public static Context getContext(){
        return mContext;
    }

    //全局获取SharedPreferences对象
    public static SharedPreferences getPreferences() {
        return preferences;
    }

    //全局获取SharedPreferences.Edit对象
    public static SharedPreferences.Editor getPreferencesEditor() {
        return preferences.edit();
    }
}
