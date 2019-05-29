package com.huojitang.leaf;

import android.content.Intent;

import com.google.android.material.tabs.TabLayout;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private MainActivityFragmentPagerAdapter myFragmentPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
    }

    //初始化两个Fragment(账单页和心愿单页)
    private void initViews() {

        //使用适配器将ViewPager与Fragment绑定在一起
        mViewPager= findViewById(R.id.viewPager);

        ArrayList<Fragment> fragments=new ArrayList<>();
        fragments.add(new MainActivityBillFragment());
        fragments.add(new MainActivityWishFragment());

        myFragmentPagerAdapter = new MainActivityFragmentPagerAdapter(getSupportFragmentManager(),fragments);
        mViewPager.setAdapter(myFragmentPagerAdapter);

        //将TabLayout与ViewPager绑定在一起
        mTabLayout = findViewById(R.id.tabLayout);
        mTabLayout.setupWithViewPager(mViewPager);
   }


    //实例化一个菜单实现跳转到选项界面
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    //菜单被点击（目前只有一个按钮，即跳转到选项界面）
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            Intent i=new Intent(MainActivity.this , OptionActivity.class);
            startActivity(i);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
