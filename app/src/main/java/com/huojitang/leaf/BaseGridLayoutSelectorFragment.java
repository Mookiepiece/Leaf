package com.huojitang.leaf;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.huojitang.leaf.activity.EditTagActivity;
import com.huojitang.leaf.view.TagColorItemView;

import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * 一个实现recyclerView自动网格布局并且里面的item能被选择的类
 * 尽管这里只是在TagColor 和 TagIcon里用到了
 * @author Mookiepiece
 * @param <T> list的类型
 * @param <T2> 需要重载的ViewHolder
 */
public abstract class BaseGridLayoutSelectorFragment<T,T2 extends androidx.recyclerview.widget.RecyclerView.ViewHolder > extends Fragment {

    protected RecyclerView recyclerView;
    protected List<T> list;
    private int selectedIndex=0;
    protected RecyclerView.Adapter<T2> adapter;
    protected OnItemSelectedListener listener;

    public BaseGridLayoutSelectorFragment(OnItemSelectedListener listener) {
        this.listener = listener;
    }

    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState, List<T> list, RecyclerView.Adapter<T2> adapter) {
        this.list = list;
        View view = inflater.inflate(R.layout.only_a_recycler_view, container, false);
        recyclerView = view.findViewById(R.id.tag_color_icon_fragment);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), EditTagActivity.ITEM_NUMBER_FOR_RECYCLER_VIEW));
        this.adapter=adapter;
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        return view;
    }

    public int getSelectedIndex(){
        return selectedIndex;
    }

    public void setSelectedIndex(int position){
        this.selectedIndex=position;
    }

    public interface OnItemSelectedListener {
        public void OnItemSelected(int position);
    }
}
