package com.huojitang.leaf;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;

import java.util.ArrayList;
import java.util.List;

public class WishFragment<FragmentAdapter> extends Fragment {
    private String[] names = {"相机","小米九","平板电脑","内存条","移动硬盘"};
    private String[] price = {"2000.00元","3199.00元","1700.00元","300.00元","500.00元"};
    private String[] states = {"已完成","已完成","已取消","未完成","已取消"};
    private String[] starttime = {"2019-01","2019-01","2019-02","2019-04","2019-04"};
    private String[] endtime = {"2019-03","2019-04","2019-03","","2019-05"};

    private List<WishMessage> wishMessageList = new ArrayList<>();
    SwipeMenuListView mListView;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedTnstanceState){
        View view = inflater.inflate(R.layout.fragment_wish,container,false);

        mListView = (SwipeMenuListView) view.findViewById(R.id.LV_2);
        initWishMessage();

        final WishFragment.FragmentAdapter fragmentAdapter = new WishFragment.FragmentAdapter();
        mListView.setAdapter(fragmentAdapter);

        SwipeMenuCreator creator = new SwipeMenuCreator() {
            @Override
            public void create(SwipeMenu menu) {
                SwipeMenuItem finishItem = new SwipeMenuItem(getContext());
                finishItem.setBackground(new ColorDrawable(Color.rgb(0xc9,0xc9,0xce)));
                finishItem.setWidth(dp2px(90));
                finishItem.setTitle("完成");
                finishItem.setTitleSize(18);
                finishItem.setTitleColor(Color.WHITE);

                menu.addMenuItem(finishItem);

                SwipeMenuItem cancelItem = new SwipeMenuItem(getContext());
                cancelItem.setBackground(new ColorDrawable(Color.rgb(0xd4,0xd4,0xdc)));
                cancelItem.setWidth(dp2px(90));
                cancelItem.setTitle("取消");
                cancelItem.setTitleSize(18);
                cancelItem.setTitleColor(Color.WHITE);

                menu.addMenuItem(cancelItem);

                SwipeMenuItem addItem = new SwipeMenuItem(getContext());
                addItem.setBackground(new ColorDrawable(Color.rgb(0xf9,0x3f,0x25)));
                addItem.setWidth(dp2px(90));
                addItem.setTitle("添加");
                addItem.setTitleSize(18);
                addItem.setTitleColor(Color.WHITE);

                menu.addMenuItem(addItem);
            }
        };

        mListView.setMenuCreator(creator);

        mListView.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                WishMessage message = wishMessageList.get(position);
                switch (index){
                    case 0:
                        Toast.makeText(getContext(),"you clicked finish",Toast.LENGTH_SHORT).show();
                        break;
                    case 1:
                        Toast.makeText(getContext(),"you clicked cancel",Toast.LENGTH_SHORT).show();
                        break;
                    case 2:
                        Toast.makeText(getContext(),"you clicked add",Toast.LENGTH_SHORT).show();
                        break;
                }
                return false;
            }
        });
        mListView.setOnSwipeListener(new SwipeMenuListView.OnSwipeListener() {
            @Override
            public void onSwipeStart(int position) {

            }

            @Override
            public void onSwipeEnd(int position) {

            }
        });

        mListView.setOnMenuStateChangeListener(new SwipeMenuListView.OnMenuStateChangeListener() {
            @Override
            public void onMenuOpen(int position) {

            }

            @Override
            public void onMenuClose(int position) {

            }
        });

        mListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getContext(),position+"long click",Toast.LENGTH_SHORT).show();
                return false;
            }
        });
        setHasOptionsMenu(true);
        return view;
    }

    private void initWishMessage() {
        for(int i = 0;i<names.length;i++){
            WishMessage wishMessage = new WishMessage(names[i],price[i],states[i],starttime[i],endtime[i]);
            wishMessageList.add(wishMessage);
        }
    }

    public class FragmentAdapter extends BaseSwipListAdapter{
        @Override
        public int getCount() {
            return wishMessageList.size();
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = getLayoutInflater().inflate(R.layout.wish_list,null);
            new ViewHolder(view);

            ViewHolder holder = (ViewHolder) view.getTag();

            holder.wishName.setText(wishMessageList.get(position).getWishName());
            holder.wishPrice.setText(wishMessageList.get(position).getWishPrice());
            holder.wishState.setText(wishMessageList.get(position).getWishState());
            holder.startTime.setText(wishMessageList.get(position).getStartTime());
            holder.endTime.setText(wishMessageList.get(position).getEndTime());
            return view;
        }

        class ViewHolder{
            TextView wishName;
            TextView wishPrice;
            TextView wishState;
            TextView startTime;
            TextView endTime;

            public ViewHolder(View view){
                wishName = view.findViewById(R.id.wish_name);
                wishPrice = view.findViewById(R.id.wish_price);
                wishState = view.findViewById(R.id.wish_state);
                startTime = view.findViewById(R.id.wish_start_time);
                endTime = view.findViewById(R.id.wish_end_time);

                view.setTag(this);
            }
        }
    }
    private int dp2px(int value){
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,value,getResources().getDisplayMetrics());
    }
    private int dp2px(float value){
        final float scale = getResources().getDisplayMetrics().density;
        return (int)(value*scale+0.5f);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
    }
}

