package com.huojitang.leaf.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.huojitang.leaf.BaseGridLayoutSelectorFragment;
import com.huojitang.leaf.CashierInputFilter;
import com.huojitang.leaf.TagResManager;
import com.huojitang.leaf.global.LeafApplication;
import com.huojitang.leaf.EditTagColorFragment;
import com.huojitang.leaf.EditTagIconFragment;
import com.huojitang.leaf.LeafFragmentPagerAdapter;
import com.huojitang.leaf.R;
import com.huojitang.leaf.view.TagIconResultView;

import java.util.ArrayList;

/**
 * 标签编辑界面
 *
 * @author Mookiepiece
 */
public class EditTagActivity extends AppCompatActivity {
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private LeafFragmentPagerAdapter adapter;
    private TagIconResultView iconResultView;

    private EditText tagNameEditText;
    private EditText tagBudgetEditText;
    private EditText tagCommentEditText;
    public static final int ITEM_NUMBER_FOR_RECYCLER_VIEW;

    static {

        //Mookiepiece:RecyclerView的GridLayoutManager需要指定一个列数，利用这个根据屏幕动态拍列
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

        Intent intent = getIntent();
        String message = intent.getStringExtra(LeafApplication.LEAF_MASSAGE);

        initViews();
    }

    private void initViews() {
        //使用适配器将ViewPager与Fragment绑定在一起（参考MainActivity）
        viewPager = findViewById(R.id.activity_edit_tag_viewPager);

        ArrayList<Fragment> fragments=new ArrayList<>();
        fragments.add(new EditTagColorFragment(new BaseGridLayoutSelectorFragment.OnItemSelectedListener() {
            @Override
            public void OnItemSelected(int position) {
                iconResultView.setBgColor(TagResManager.getTagColorResId(position));
            }
        }));
        fragments.add(new EditTagIconFragment(new BaseGridLayoutSelectorFragment.OnItemSelectedListener() {
            @Override
            public void OnItemSelected(int position) {
                iconResultView.setFgIcon(TagResManager.getTagIconsResId(position));
            }
        }));

        adapter = new LeafFragmentPagerAdapter(getSupportFragmentManager(),new String[]{"背景","图标"},fragments);
        viewPager.setAdapter(adapter);

        //将TabLayout与ViewPager绑定在一起
        tabLayout = findViewById(R.id.activity_edit_tag_tabLayout);
        tabLayout.setupWithViewPager(viewPager);

        //其他组件
        iconResultView=findViewById(R.id.activity_edit_tag_icon_result);

        //取消按钮
        findViewById(R.id.activity_edit_tag_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //文本框判断输入
        tagNameEditText=findViewById(R.id.activity_edit_tag_name);
        tagCommentEditText=findViewById(R.id.activity_edit_tag_cmt);
        tagBudgetEditText=findViewById(R.id.activity_edit_tag_budget);

        tagNameEditText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(20)});
        tagCommentEditText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(100)});
        tagBudgetEditText.setFilters(new InputFilter[] {new CashierInputFilter(9),new InputFilter.LengthFilter(9)});
    }


}
