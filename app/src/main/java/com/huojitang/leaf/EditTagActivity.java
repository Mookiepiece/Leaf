package com.huojitang.leaf;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.huojitang.DAO.TagDAO;
import com.huojitang.entities.TagEntity;
import com.huojitang.util.ColorConverter;

/**
 * 编辑标签界面
 */
public class EditTagActivity extends AppCompatActivity {
    private TagDAO tagDAO;
    List<TagEntity> tagList=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_tag);

        tagDAO=new TagDAO(this);
        tagList=tagDAO.getTagsWithoutDefault();

        //RecyclerView代码
        RecyclerView rv=findViewById(R.id.edit_tag_recycler_view);

        rv.setHasFixedSize(true);//优化性能?

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rv.setLayoutManager(layoutManager);

        EditTagAdapter adapter=new EditTagAdapter();
        rv.setAdapter(adapter);

        EditTagTouchCallback callback=new EditTagTouchCallback(adapter);
        ItemTouchHelper helper = new ItemTouchHelper(callback);
        helper.attachToRecyclerView(rv);

        //添加标签按钮
        Button add=findViewById(R.id.edit_tag_activity_add_btn);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowAddTagDialog();
            }
        });
    }

    public void ShowAddTagDialog(){
        if(tagDAO.getCountWithoutDefault()>=99){
            Toast.makeText(EditTagActivity.this,"不能再增加标签数量了",Toast.LENGTH_SHORT).show();
            return;
        }

        TagEntity tagEntity=new TagEntity();
        new AddTagDialog(EditTagActivity.this,tagEntity, new AddTagDialog.ConfirmOnclickListener(){
            @Override
            public void ConfirmClick() {
                //TODO MK 给数据库更新
            }
        }).show();
//
//        tagDAO.insertTag();

    }


    class EditTagTouchCallback extends ItemTouchHelper.Callback {
        EditTagAdapter adapter;
        int swapStartPosition=-1;
        int swapEndPosition=-1;

        public EditTagTouchCallback(EditTagAdapter adapter) {
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
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

        }

        //手指松开
        @Override
        public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
            super.clearView(recyclerView, viewHolder);
            adapter.notifyDataSetChanged();

            //更新被标记移动的tag的index
            if(swapStartPosition!=-1&&swapEndPosition!=-1)
            tagDAO.updateTagIndexes(tagList,swapStartPosition,swapEndPosition);
            //重置标记
            swapStartPosition=swapEndPosition=-1;
        }
    }

    //适配器负责将Entity内容映射成item
    class EditTagAdapter extends RecyclerView.Adapter<EditTagViewHolder>{

        //创建ViewHolder
        @Override
        public EditTagViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            //View view = LayoutInflater.from(EditTagActivity.this).inflate(R.layout.tag_item, null);
            View view = LayoutInflater.from(EditTagActivity.this).inflate(R.layout.tag_item, viewGroup,false);
            return new EditTagViewHolder(view);
        }

        //为item赋值
        @Override
        public void onBindViewHolder(EditTagViewHolder editTagViewHolder, int i) {
            editTagViewHolder.name.setText( tagList.get(i).getTagName());
            int color= ColorConverter.String2Color(tagList.get(i).getColor());
            editTagViewHolder.name.setTextColor(color );
            editTagViewHolder.color.setBackgroundColor(color);
            editTagViewHolder.limit.setText(tagList.get(i).getTagLimitDecimal());
            editTagViewHolder.cmt.setText( tagList.get(i).getComment());
        }

        @Override
        public int getItemCount() {
            return tagList.size();
        }
    }

    //ViewHolder负责缓存界面组件的id，避免重复读id以提升性能
    class EditTagViewHolder extends RecyclerView.ViewHolder{
        TextView name;
        TextView limit;
        TextView color;
        TextView cmt;

        public EditTagViewHolder(View itemView) {
            super(itemView);
            this.name=itemView.findViewById(R.id.edit_tag_item_name);
            this.limit=itemView.findViewById(R.id.edit_tag_item_limit);
            this.color=itemView.findViewById(R.id.edit_tag_item_color);
            this.cmt=itemView.findViewById(R.id.edit_tag_item_comment);
        }
    }


}
