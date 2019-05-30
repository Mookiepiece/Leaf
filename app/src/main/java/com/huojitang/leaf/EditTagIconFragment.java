package com.huojitang.leaf;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

/**
 * 选择标签图标Fragment（照搬主页）
 * @author Mookiepiece
 */
public class EditTagIconFragment extends Fragment {

    //创建进度条
    private int status = 0;
    private ProgressBar bar;

    private RecyclerView recyclerView;
    private TagColorAdapter tagColorAdapter;

    private static List<Integer> resIdList = TagResHelper.getAllTagIconResId();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tag_color_and_icons, container, false);
        recyclerView = view.findViewById(R.id.tag_color_icon_fragment);
        initRecyclerView();
        return view;
    }

    private void initRecyclerView() {
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),5));
        tagColorAdapter = new TagColorAdapter();
        recyclerView.setAdapter(tagColorAdapter);
        tagColorAdapter.notifyDataSetChanged();
    }

    public class TagColorAdapter extends RecyclerView.Adapter<TagColorAdapter.ViewHolder> {


        @Override
        public int getItemCount() {
            return resIdList.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            TagIconItemView view;
            ViewHolder(View view) {
                super(view);
                this.view=view.findViewById(R.id.tag_icon_item_view);
            }
        }
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.tag_icon_item, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            holder.view.setFgIcon(resIdList.get(position));
            }
        }
}