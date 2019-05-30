package com.huojitang.leaf;

import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

/**
 * 选择标签颜色Fragment（照搬主页）
 * @author Mookiepiece
 */
public class EditTagColorFragment extends Fragment {

    private RecyclerView recyclerView;
    private TagColorAdapter tagColorAdapter;

    private static List<Integer> colorIdList= TagResHelper.getAllTagColorResId();;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.only_a_recycler_view, container, false);
        recyclerView = view.findViewById(R.id.tag_color_icon_fragment);
        initRecyclerView();
        return view;
    }

    private void initRecyclerView() {

        GridLayoutManager layoutManager=new GridLayoutManager(getContext(),EditTagActivity.ITEM_NUMBER_FOR_RECYCLER_VIEW);
        recyclerView.setLayoutManager(layoutManager);
        tagColorAdapter = new TagColorAdapter();
        recyclerView.setAdapter(tagColorAdapter);
        tagColorAdapter.notifyDataSetChanged();
    }

    public class TagColorAdapter extends RecyclerView.Adapter<TagColorAdapter.ViewHolder> {

        @Override
        public int getItemCount() {
            return colorIdList.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            TagColorItemView view;
            ViewHolder(View view) {
                super(view);
                this.view=view.findViewById(R.id.tag_color_item_view);
            }
        }
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.tag_color_item, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            holder.view.setBgColor(ResourcesCompat.getColor(getResources(),
                    colorIdList.get(position), null));
            }
        }

}