package com.huojitang.leaf;

import java.util.ArrayList;

public class TagResHelper {

    private static ArrayList<Integer> tagColors=new ArrayList<>();
    private static ArrayList<Integer> tagIcons=new ArrayList<>();

    public static int getTagColorsLength(){
        return tagColors.size();
    }

    public static int getTagColorResId(int id){
        return tagColors.get(id);
    }

    public static int getTagIconsLength(){
        return tagIcons.size();
    }

    public static int getTagIconsResId(int id){
        return tagIcons.get(id);
    }
    static{
        int[] a={
                R.drawable.tag_color_0,
                R.drawable.tag_color_1,
                R.drawable.tag_color_2,
                R.drawable.tag_color_3,
        };

        for(int i : a)
            tagColors.add(i);

        int[] b={
                R.drawable.analytics_graph_bar,
                R.drawable.baby_trolley,
                R.drawable.baggage,
                R.drawable.beach_parasol_water_1,
                R.drawable.biking_person,
                R.drawable.bin_2,
                R.drawable.binocular,
                R.drawable.bomb_grenade,
        };

        for(int i : b)
            tagIcons.add(i);
    }

    private TagResHelper() {

    }
}
