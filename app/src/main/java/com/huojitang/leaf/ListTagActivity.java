package com.huojitang.leaf;

import androidx.annotation.NonNull;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.ItemTouchHelper;
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
import com.huojitang.leaf.dao.TagDao;
import com.huojitang.leaf.model.Tag;
import com.huojitang.util.PriceTransUtil;


/**
 * 编辑标签界面
 */
public class ListTagActivity extends AppCompatActivity {
    private TagDao tagDao=TagDao.getInstance();
    List<Tag> tagList=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_tag);

        tagList=tagDao.list(false);

        //RecyclerView代码
        RecyclerView rv=findViewById(R.id.edit_tag_recycler_view);
        rv.setHasFixedSize(true);//优化性能?
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
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
        if(tagDao.getCount(true)>=99){
            Toast.makeText(ListTagActivity.this,"不能再增加标签数量了", Toast.LENGTH_SHORT).show();
            return;
        }

        Tag tag=new Tag();
        new AddTagDialog(ListTagActivity.this,tag, new AddTagDialog.ConfirmOnclickListener(){
            @Override
            public void ConfirmClick() {
                //TODO MK 给数据库更新
            }
        }).show();

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
            tagDao.updateTagIndexes(tagList,swapStartPosition,swapEndPosition);
            //重置标记
            swapStartPosition=swapEndPosition=-1;
        }
    }

    //适配器负责将Entity内容映射成item
    class EditTagAdapter extends RecyclerView.Adapter<EditTagViewHolder>{

        //创建ViewHolder
        @Override
        public EditTagViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View view = LayoutInflater.from(ListTagActivity.this).inflate(R.layout.tag_item, viewGroup,false);
            return new EditTagViewHolder(view);
        }

        //为item赋值
        @Override
        public void onBindViewHolder(EditTagViewHolder editTagViewHolder, int i) {
            editTagViewHolder.name.setText( tagList.get(i).getName());
            int color= tagList.get(i).getColor();
            editTagViewHolder.name.setTextColor(color);
            editTagViewHolder.color.setBackgroundColor(color);
//            getBudget()
            editTagViewHolder.limit.setText(PriceTransUtil.Int2Decimal(tagList.get(i).getBudget()));
            editTagViewHolder.cmt.setText( tagList.get(i).getComment());
            editTagViewHolder.layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(ListTagActivity.this,"oooooo",Toast.LENGTH_SHORT).show();
                }
            });
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
        LinearLayout layout;

        public EditTagViewHolder(View itemView) {
            super(itemView);
            this.name=itemView.findViewById(R.id.edit_tag_item_name);
            this.limit=itemView.findViewById(R.id.edit_tag_item_limit);
            this.color=itemView.findViewById(R.id.edit_tag_item_color);
            this.cmt=itemView.findViewById(R.id.edit_tag_item_comment);
            this.layout=itemView.findViewById(R.id.edit_tag_layout);
        }
    }
}
