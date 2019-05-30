package com.huojitang.leaf;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

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
