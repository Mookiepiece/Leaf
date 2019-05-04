package com.huojitang.leaf;

import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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

    }

    class EditTagTouchCallback extends ItemTouchHelper.Callback {
        EditTagAdapter adapter;

        public EditTagTouchCallback(EditTagAdapter adapter) {
            this.adapter = adapter;
        }

        @Override
        public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
            return makeMovementFlags(ItemTouchHelper.DOWN|ItemTouchHelper.UP,0);
        }

        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder1, @NonNull RecyclerView.ViewHolder viewHolder2) {
            int fromPosition = viewHolder1.getAdapterPosition();
            int toPosition = viewHolder2.getAdapterPosition();
            if (fromPosition <  toPosition){
             for (int i = fromPosition; i < toPosition; i++){
              Collections.swap(tagList,i,i+1);
            }
            }else{
                for(int i=fromPosition;i>toPosition;i--){
                Collections.swap(tagList,i,i-1);
                }
            }
            adapter.notifyItemMoved(fromPosition,toPosition);
            return true;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

        }
    }

    class EditTagAdapter extends RecyclerView.Adapter<EditTagViewHolder>{

        @Override
        public EditTagViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            //View view = LayoutInflater.from(EditTagActivity.this).inflate(R.layout.tag_item, null);
            View view = LayoutInflater.from(EditTagActivity.this).inflate(R.layout.tag_item, viewGroup,false);
            return new EditTagViewHolder(view);
        }

        @Override
        public void onBindViewHolder(EditTagViewHolder editTagViewHolder, int i) {
            editTagViewHolder.name.setText( tagList.get(i).getTagName());
            int color=ColorConverter.String2Color(tagList.get(i).getColor());
            editTagViewHolder.name.setTextColor(color );
            editTagViewHolder.color.setBackgroundColor(color);
            editTagViewHolder.limit.setText(tagList.get(i).getTagLimit());
            editTagViewHolder.cmt.setText( tagList.get(i).getComment());
        }

        @Override
        public int getItemCount() {
            return tagList.size();
        }
    }

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
