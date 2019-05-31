package com.huojitang.leaf;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.huojitang.leaf.view.TagIconItemView;

import org.jetbrains.annotations.NotNull;

/**
 * 选择标签图标Fragment（照搬主页）
 * @author Mookiepiece
 */
public class EditTagIconFragment extends BaseGridLayoutSelectorFragment<Integer, EditTagIconFragment.TagIconSelectorAdapter.TagIconViewHolder> {

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState,TagResManager.getAllTagIconResId(),new TagIconSelectorAdapter());
    }

    protected class TagIconSelectorAdapter extends RecyclerView.Adapter<TagIconSelectorAdapter.TagIconViewHolder> {


        @Override
        public TagIconViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.tag_icon_item, parent, false);
            return new TagIconViewHolder(view);
        }

        @Override
        public void onBindViewHolder(TagIconViewHolder holder, int position) {
            holder.view.setFgIcon(list.get(position));
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
                }
            });
        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        class TagIconViewHolder extends RecyclerView.ViewHolder {
            TagIconItemView view;
            TagIconViewHolder(View view) {
                super(view);
                this.view=view.findViewById(R.id.tag_icon_item_view);
            }
        }

    }
}