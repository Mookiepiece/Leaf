package com.huojitang.leaf;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.huojitang.leaf.global.LeafApplication;
import com.huojitang.leaf.view.TagColorItemView;

import org.jetbrains.annotations.NotNull;

/**
 * 选择标签颜色Fragment（照搬主页）
 * @author Mookiepiece
 */
public class EditTagColorFragment extends BaseGridLayoutSelectorFragment<Integer, EditTagColorFragment.TagColorSelectorAdapter.TagColorViewHolder> {


    public EditTagColorFragment(OnItemSelectedListener listener) {
        super(listener);
    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState,
                TagResManager.getAllTagColorResId(),new TagColorSelectorAdapter());
    }

    protected class TagColorSelectorAdapter extends RecyclerView.Adapter<TagColorSelectorAdapter.TagColorViewHolder> {

        @NotNull
        @Override
        public TagColorViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.tag_color_item, parent, false);
            return new TagColorViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NotNull TagColorViewHolder holder, int position) {
            holder.view.setBgColor(list.get(position));
            if(getSelectedIndex()==position){
                holder.view.setActive(true);
            }
            else{
                holder.view.setActive(false);
            }
            holder.view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int temp=getSelectedIndex();
                    setSelectedIndex(position);
                    adapter.notifyItemChanged(temp);
                    adapter.notifyItemChanged(getSelectedIndex());
                    listener.OnItemSelected(position);
                }
            });
        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        class TagColorViewHolder extends RecyclerView.ViewHolder {
            TagColorItemView view;
            TagColorViewHolder(View view) {
                super(view);
                this.view=view.findViewById(R.id.tag_color_item_view);
            }
        }
    }

}