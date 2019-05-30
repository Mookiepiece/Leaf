package com.huojitang.leaf;

import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.huojitang.global.LeafApplication;

import java.util.ArrayList;

/**
 * 标签编辑界面
 *
 * @author Mookiepiece
 */
public class EditTagActivity extends AppCompatActivity {
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private EditTagFragmentPagerAdapter adapter;

    public static final int ITEM_NUMBER_FOR_RECYCLER_VIEW;
    static {

        //Mookiepiece:RecyclerBiew的GridLayoutManager需要指定一个列数，利用这个根据屏幕动态拍列
        //Android获取屏幕宽度 https://blog.csdn.net/wangliblog/article/details/22501141
        WindowManager wm = (WindowManager) LeafApplication.getContext().getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        int screenWidth = (int) (dm.widthPixels/dm.density);//屏幕宽度(dp) = 屏幕宽度（像素） / 屏幕密度（0.75 / 1.0 / 1.5）

        ITEM_NUMBER_FOR_RECYCLER_VIEW=screenWidth /50;
        Toast.makeText(LeafApplication.getContext(),String.valueOf(ITEM_NUMBER_FOR_RECYCLER_VIEW),Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_tag);
        initViews();
    }

    private void initViews() {
        //使用适配器将ViewPager与Fragment绑定在一起（参考MainActivity）
        viewPager = findViewById(R.id.activity_edit_tag_viewPager);

        ArrayList<Fragment> fragments=new ArrayList<>();
        fragments.add(new EditTagColorFragment());
        fragments.add(new EditTagIconFragment());

        adapter = new EditTagFragmentPagerAdapter(getSupportFragmentManager(),fragments);
        viewPager.setAdapter(adapter);

        //将TabLayout与ViewPager绑定在一起
        tabLayout = findViewById(R.id.activity_edit_tag_tabLayout);
        tabLayout.setupWithViewPager(viewPager);
    }


}
