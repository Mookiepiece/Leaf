package com.huojitang.leaf;
import android.graphics.Color;

class ColorConverter {
    public static int String2Color(String str) {
        if(str.length()==3||str.length()==6)
            str='#'+str;
        if(str.length()==4){
            str=""+str.charAt(0)+str.charAt(1)+str.charAt(1)+str.charAt(2)+str.charAt(2)+str.charAt(3)+str.charAt(3);
        }
        return Color.parseColor(str);
    }

    public static String Color2String(int color) {
        String R = Integer.toHexString(Color.red(color));
        R = R.length()<2?R:R.substring(1);
        String G = Integer.toHexString(Color.green(color));
        G = G.length()<2?G:G.substring(1);
        String B = Integer.toHexString(Color.blue(color));
        B = B.length()<2?B:B.substring(1);
        return '#'+R+G+B;
    }
}
