package com.huojitang.leaf.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.InputType;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.huojitang.leaf.BaseGridLayoutSelectorFragment;
import com.huojitang.leaf.CashierInputFilter;
import com.huojitang.leaf.dao.BillItemDao;
import com.huojitang.leaf.dao.TagDao;
import com.huojitang.leaf.global.LeafApplication;
import com.huojitang.leaf.EditTagColorFragment;
import com.huojitang.leaf.EditTagIconFragment;
import com.huojitang.leaf.LeafFragmentPagerAdapter;
import com.huojitang.leaf.R;
import com.huojitang.leaf.model.BillItem;
import com.huojitang.leaf.model.Tag;
import com.huojitang.leaf.view.TagIconResultView;

import java.util.ArrayList;
import java.util.List;

/**
 * 标签编辑界面
 *
 * @author Mookiepiece
 */
public class EditTagActivity extends AppCompatActivity {


    private TagIconResultView iconPreview;
    private EditText tagNameEditText;
    private EditText tagBudgetEditText;
    private EditText tagCommentEditText;

    private int message;  //从上一个界面接收一个tag id

    private Tag tag;
    private TagDao tagDao=TagDao.getInstance();
    private LeafFragmentPagerAdapter adapter;

    //动态计算RecyclerView的GridLayoutManager的列数以保证适配屏幕
    public static final int ITEM_NUMBER_FOR_RECYCLER_VIEW;
    static {
        //Mookie:RecyclerView的GridLayoutManager需要指定一个列数，利用这个根据屏幕动态拍列
        //Android获取屏幕宽度 https://blog.csdn.net/wangliblog/article/details/22501141
        WindowManager wm = (WindowManager) LeafApplication.getContext().getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        int screenWidth = (int) (dm.widthPixels/dm.density);//屏幕宽度(dp) = 屏幕宽度（像素） / 屏幕密度（0.75 / 1.0 / 1.5）
        ITEM_NUMBER_FOR_RECYCLER_VIEW=screenWidth /50;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_tag);

        //获取意图中的id信息，添加则没有
        Intent intent = getIntent();
        message = intent.getIntExtra(LeafApplication.LEAF_MASSAGE,-1);

        //先初始化界面
        tagNameEditText=findViewById(R.id.activity_edit_tag_name);
        tagCommentEditText=findViewById(R.id.activity_edit_tag_cmt);
        tagBudgetEditText=findViewById(R.id.activity_edit_tag_budget);
        iconPreview =findViewById(R.id.activity_edit_tag_icon_result);
        initViews();

        //然后添加tag原来的内容
        //-1表示不是编辑而是添加
        if(message==-1){
            tag=new Tag();
        }else{
            tag=tagDao.getById(message);
            tagNameEditText.setText(tag.getName());
            tagBudgetEditText.setText(tag.getBudgetString());
            tagCommentEditText.setText(tag.getComment());
            iconPreview.setFgIcon(tag.getIcon());
            iconPreview.setBgColor(tag.getColor());
        }
    }

    private void initViews() {

        //使用适配器将ViewPager与Fragment绑定在一起（参考MainActivity）
        ViewPager viewPager = findViewById(R.id.activity_edit_tag_viewPager);
        ArrayList<Fragment> fragments=new ArrayList<>();
        fragments.add(new EditTagColorFragment(new BaseGridLayoutSelectorFragment.OnItemSelectedListener() {
            @Override
            public void OnItemSelected(int position) {
                iconPreview.setBgColor(position);
                tag.setColor(position);
            }
        }));
        fragments.add(new EditTagIconFragment(new BaseGridLayoutSelectorFragment.OnItemSelectedListener() {
            @Override
            public void OnItemSelected(int position) {
                iconPreview.setFgIcon(position);
                tag.setIcon(position);
            }
        }));
        adapter = new LeafFragmentPagerAdapter(getSupportFragmentManager(),new String[]{"背景","图标"},fragments);
        viewPager.setAdapter(adapter);

        //将TabLayout与ViewPager绑定在一起
        TabLayout tabLayout = findViewById(R.id.activity_edit_tag_tabLayout);
        tabLayout.setupWithViewPager(viewPager);

        //删除按钮
        if(message==-1){
            findViewById(R.id.activity_edit_tag_delete).setVisibility(View.GONE);
        }
        else{
            findViewById(R.id.activity_edit_tag_delete).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder dialog=new AlertDialog.Builder(EditTagActivity.this);
                    dialog.setTitle("确定删除吗");
                    dialog.setMessage("删除"+tag.getName()+"后，旗下的账目会移动到未分类中");
                    dialog.setCancelable(true);  //点对话框外面是否可以退出，默认true
                    dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            //移动到未分类
                            List<BillItem> list=tag.getBillItems();
                            BillItemDao billItemDao=BillItemDao.getInstance();
                            Tag reserved=tagDao.getResrvedTag();
                            for(BillItem i:list){
                                i.setTag(reserved);
                                billItemDao.update(i);
                            }
                            //删除
                            tagDao.delete(tag.getId());
                            //使用Intent返回受改变项
                            Intent intent = new Intent();
                            setResult(RESULT_DELETED, intent);
                            finish();
                        }
                    });
                    dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    dialog.show();

                }
            });
        }

        //取消按钮
        findViewById(R.id.activity_edit_tag_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_CANCELED, new Intent());
                finish();
            }
        });
        //确定按钮
        findViewById(R.id.activity_edit_tag_confirm).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //用户输入判断
                if (tagNameEditText.getText().toString().trim().equals("")) {
                    Toast.makeText(EditTagActivity.this, "名称不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }

                //更新数据库(图标)
                tag.setName(tagNameEditText.getText().toString());
                tag.setBudget(Double.valueOf(tagBudgetEditText.getText().toString()));
                tag.setComment(tagCommentEditText.getText().toString());
                tag.setIcon(iconPreview.getFgIcon());
                tag.setColor(iconPreview.getBgColor());
                tagDao.update(tag);

                //使用Intent返回受改变项
                Intent intent = new Intent();
                setResult(RESULT_OK, intent);
                finish();
            }
        });

        //文本框判断输入

        tagNameEditText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(20)});
        tagCommentEditText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(100)});
        tagBudgetEditText.setFilters(new InputFilter[] {new CashierInputFilter(9),new InputFilter.LengthFilter(9)});
        tagBudgetEditText.setInputType(InputType.TYPE_CLASS_NUMBER|InputType.TYPE_NUMBER_FLAG_DECIMAL);

    }

    public final static int RESULT_DELETED=2;
    public final static int RESULT_OK=0;
    public final static int RESULT_CANCELED=1;
}
