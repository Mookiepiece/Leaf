package com.huojitang.leaf.activity;

import androidx.annotation.NonNull;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.ItemTouchHelper;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.huojitang.leaf.R;
import com.huojitang.leaf.TagResManager;
import com.huojitang.leaf.dao.TagDao;
import com.huojitang.leaf.global.LeafApplication;
import com.huojitang.leaf.model.Tag;
import com.huojitang.leaf.view.TagIconResultView;
import com.huojitang.leaf.util.PriceTransUtil;

import org.jetbrains.annotations.NotNull;

/**
 * 编辑标签界面
 */
public class ListTagActivity extends AppCompatActivity {
    private TagDao tagDao=TagDao.getInstance();
    List<Tag> tagList=new ArrayList<>();
    EditTagAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_tag);

        tagList=tagDao.list(false);

        //RecyclerView代码
        RecyclerView rv=findViewById(R.id.list_tag_recycler_view);
        rv.setHasFixedSize(true);//优化性能?
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        rv.setLayoutManager(layoutManager);
        adapter=new EditTagAdapter();
        rv.setAdapter(adapter);
        EditTagTouchCallback callback=new EditTagTouchCallback(adapter);
        ItemTouchHelper helper = new ItemTouchHelper(callback);
        helper.attachToRecyclerView(rv);

        //添加标签按钮
        findViewById(R.id.list_tag_activity_add_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowEditTagActivity();
            }
        });
    }

    /**
     * 弹出标签编辑界面（添加）
     */
    public void ShowEditTagActivity(){
        Intent intent = new Intent(ListTagActivity.this, EditTagActivity.class);
        startActivity(intent);
    }

    /**
     * 弹出标签编辑界面（修改）
     * @param position 被选择的item在list中的位置
     */
    public void ShowEditTagActivity(int position){
        Intent intent = new Intent(ListTagActivity.this, EditTagActivity.class);
        intent.putExtra(LeafApplication.LEAF_MASSAGE, tagList.get(position).getId());
        startActivity(intent);
    }

    //TODO MK

    /**
     * 返回时重读数据库
     */
    @Override
    protected void onResume() {
        super.onResume();
        tagList = tagDao.list(false); //TODO MK 暴力重读不可取(划掉，试着在onActivityResult写逻辑但是失败了)
        adapter.notifyDataSetChanged();
    }

    /**
     * 重载接收 标签编辑界面 的返回值
     * @param requestCode 请求码 参见ShowEditTagActivity方法 -1 或 position
     * @param resultCode 结果码 参见EditTagActivity内的调用
     * @param data 返回的意图（结果值
     */
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        //Bundle result = data.getExtras();//关闭编辑界面后返回的数据
//        //if(result==null) return;
//
//
//      //  tagList = tagDao.list(false); //TODO MK 暴力重读不可取
//      //  adapter.notifyDataSetChanged();
//        /*
//        if(requestCode!=-1){ //修改标签
//            switch (resultCode){
//                case EditTagActivity.RESULT_CANCELED:
//                    break;
//                case EditTagActivity.RESULT_OK:
//                    int id=tagList.get(requestCode).getId();
//                    tagList.remove(requestCode);
//                    tagList.add(requestCode,tagDao.getById(id));
//                    adapter.notifyItemChanged(requestCode);
//                    break;
//                case EditTagActivity.RESULT_DELETED:
//                    tagList.remove(requestCode);
//                    adapter.notifyItemRemoved(requestCode);
//                    break;
//                default:
//                    break;
//            }
//        }
//        else{  //新建（插入标签）
//            switch (resultCode){
//                case EditTagActivity.RESULT_CANCELED:
//                    break;
//                case EditTagActivity.RESULT_OK:
//
//                    break;
//                default:
//                    break;
//            }
//        }
//        Log.d("JAVA", "onActivityResult: ");*/
//    }

    /**
     * touchcallbback实现了拖拽排序
     */
    class EditTagTouchCallback extends ItemTouchHelper.Callback {
        EditTagAdapter adapter;
        int swapStartPosition=-1;
        int swapEndPosition=-1;

        EditTagTouchCallback(EditTagAdapter adapter) {
            this.adapter = adapter;
        }

        @Override
        public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
            //响应上下的拖动操作，不响应滑动操作
            return makeMovementFlags(ItemTouchHelper.DOWN|ItemTouchHelper.UP,0);
        }

        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder1, @NonNull RecyclerView.ViewHolder viewHolder2) {
            int fromPosition = viewHolder1.getAdapterPosition();
            int toPosition = viewHolder2.getAdapterPosition();

            //拖动则对起始位置和结束位置的item遍历，让他们逐个+1移动
            if (fromPosition <  toPosition){
             for (int i = fromPosition; i < toPosition; i++){
              Collections.swap(tagList,i,i+1);
            }
            }else{
                for(int i=fromPosition;i>toPosition;i--){
                Collections.swap(tagList,i,i-1);
                }
            }
            //提醒刷新
            adapter.notifyItemMoved(fromPosition,toPosition);

            //标记哪些位置需要更新到数据库
            if (fromPosition >  toPosition){int t=fromPosition;fromPosition=toPosition;toPosition=t;}
            if(swapStartPosition==-1||swapStartPosition>fromPosition)
                swapStartPosition=fromPosition;
            if(swapEndPosition==-1||swapEndPosition<toPosition)
                swapEndPosition=toPosition;

            return true;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
        }

        //手指松开
        @Override
        public void clearView(@NotNull RecyclerView recyclerView, @NotNull RecyclerView.ViewHolder viewHolder) {
            super.clearView(recyclerView, viewHolder);
            adapter.notifyDataSetChanged();

            //更新被标记移动的tag的index
            if(swapStartPosition!=-1&&swapEndPosition!=-1)
            tagDao.updateTagIndexes(tagList,swapStartPosition,swapEndPosition);
            //重置标记
            swapStartPosition=swapEndPosition=-1;
        }
    }

    /**
     * 适配器负责将Entity内容映射成item
     */
    class EditTagAdapter extends RecyclerView.Adapter<EditTagViewHolder>{

        //创建ViewHolder
        @NotNull
        @Override
        public EditTagViewHolder onCreateViewHolder(@NotNull ViewGroup viewGroup, int viewType) {
            View view = LayoutInflater.from(ListTagActivity.this).inflate(R.layout.tag_item, viewGroup,false);
            return new EditTagViewHolder(view);
        }

        //为item赋值
        @Override
        public void onBindViewHolder(@NotNull EditTagViewHolder editTagViewHolder, int position) {
            Tag tag=tagList.get(position);

            int colorResId=TagResManager.getTagColorResId(tag.getColor());
            editTagViewHolder.name.setTextColor(colorResId);
            editTagViewHolder.name.setText(tag.getName());

            editTagViewHolder.icon.setBgColor(tag.getColor());
            editTagViewHolder.icon.setFgIcon(tag.getIcon());

            if(tag.getBudget()==0){
                editTagViewHolder.limit.setText("");
            }
            else{
                editTagViewHolder.limit.setText(PriceTransUtil.Int2Decimal(tag.getBudget()));
            }

            editTagViewHolder.cmt.setText( tag.getComment());

            editTagViewHolder.layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ShowEditTagActivity(position);
                }
            });
        }

        @Override
        public int getItemCount() {
            return tagList.size();
        }
    }

    /**
     * ViewHolder负责缓存界面组件的id，避免重复读id以提升性能
     */
    class EditTagViewHolder extends RecyclerView.ViewHolder{
        TextView name;
        TextView limit;
        TagIconResultView icon;
        TextView cmt;
        LinearLayout layout;

        EditTagViewHolder(View itemView) {
            super(itemView);
            this.name=itemView.findViewById(R.id.list_tag_item_name);
            this.limit=itemView.findViewById(R.id.list_tag_item_limit);
            this.icon =itemView.findViewById(R.id.list_tag_item_icon);
            this.cmt=itemView.findViewById(R.id.list_tag_item_comment);
            this.layout=itemView.findViewById(R.id.edit_tag_layout);
        }
    }
}
