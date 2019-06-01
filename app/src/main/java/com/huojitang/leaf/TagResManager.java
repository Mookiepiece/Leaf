package com.huojitang.leaf;

import java.util.ArrayList;

public class TagResManager {

    private static ArrayList<Integer> tagColors=new ArrayList<>();
    private static ArrayList<Integer> tagIcons=new ArrayList<>();

    public static int getTagColorsLength(){
        return tagColors.size();
    }

    public static int getTagColorResId(int position){
        return tagColors.get(position);
    }

    public static ArrayList<Integer> getAllTagColorResId(){
        return tagColors;
    }

    public static ArrayList<Integer> getAllTagIconResId(){
        return tagIcons;
    }

    public static int getTagIconsLength(){
        return tagIcons.size();
    }

    public static int getTagIconsResId(int position){
        return tagIcons.get(position);
    }

    static{
        int[] a={
                R.color.tag_color_0,
                R.color.tag_color_1,
                R.color.tag_color_2,
                R.color.tag_color_3,
                R.color.tag_color_4,
                R.color.tag_color_5,
                R.color.tag_color_6,
                R.color.tag_color_7,
                R.color.tag_color_8,
                R.color.tag_color_9,
                R.color.tag_color_10,
                R.color.tag_color_11,
                R.color.tag_color_12,
                R.color.tag_color_13,
        };

        for(int i : a)
            tagColors.add(i);

        int[] b={
                R.drawable.icon_1, R.drawable.icon_2, R.drawable.icon_3, R.drawable.icon_4, R.drawable.icon_5, R.drawable.icon_6, R.drawable.icon_7, R.drawable.icon_8, R.drawable.icon_9, R.drawable.icon_10, R.drawable.icon_11, R.drawable.icon_12, R.drawable.icon_13, R.drawable.icon_14, R.drawable.icon_15, R.drawable.icon_16, R.drawable.icon_17, R.drawable.icon_18, R.drawable.icon_19, R.drawable.icon_20, R.drawable.icon_21, R.drawable.icon_22, R.drawable.icon_23, R.drawable.icon_24, R.drawable.icon_25,
                R.drawable.icon_26, R.drawable.icon_27,
        };

        for(int i : b)
            tagIcons.add(i);
    }

    private TagResManager() {

    }
}
