package com.huojitang.leaf;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.appcompat.widget.PopupMenu;

import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import com.huojitang.leaf.model.Tag;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

public class MainActivityWishFragment<FragmentAdapter> extends Fragment {
    private String[] names = {"相机","小米九","平板电脑","内存条","移动硬盘"};
    private String[] price = {"2000.00元","3199.00元","1700.00元","300.00元","500.00元"};
    private String[] states = {"已完成","已完成","已取消","未完成","已取消"};
    private String[] starttime = {"2019-01","2019-01","2019-02","2019-04","2019-04"};
    private String[] endtime = {"2019-03","2019-04","2019-03","","2019-05"};

    private void initWishMessage() {
        for(int i = 0;i<names.length;i++){
            WishMessage wishMessage = new WishMessage(names[i],price[i],states[i],starttime[i],endtime[i]);
            wishMessageList.add(wishMessage);
        }
    }

    private List<WishMessage> wishMessageList = new ArrayList<>();//TODO
    ListView mListView;
    Calendar calendar;
    String year;
    String month;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedTnstanceState){
        View view = inflater.inflate(R.layout.fragment_wish,container,false);
        calendar = Calendar.getInstance();
        calendar.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
        year = String.valueOf(calendar.get(Calendar.YEAR));
        if(calendar.get(Calendar.MONTH)<10){
            month = "0" + String.valueOf(calendar.get(Calendar.MONTH)+1);
        }else {
            month = String.valueOf(calendar.get(Calendar.MONTH)+1);
        }

        mListView = view.findViewById(R.id.LV_2);
        initWishMessage();

        final MainActivityWishFragment.FragmentAdapter fragmentAdapter = new MainActivityWishFragment.FragmentAdapter();
        mListView.setAdapter(fragmentAdapter);

        mListView.setOnItemLongClickListener (new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                    if(wishMessageList.get(position).getWishState().equals("未完成")){
                        PopupMenu popupMenu = new PopupMenu(getContext(), view);
                        popupMenu.inflate(R.menu.popup_menu);
                        popupMenu.show();
                        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                            @Override
                            public boolean onMenuItemClick(MenuItem item) {
                                switch (item.getItemId()) {
                                    case R.id.wish_finish:
                                        wishMessageList.get(position).setWishState("已完成");
                                        wishMessageList.get(position).setEndTime(year+"-"+month);
                                        fragmentAdapter.notifyDataSetChanged();
                                        Toast.makeText(getContext(), "你选择了完成选项", Toast.LENGTH_SHORT).show();
                                        break;
                                    case R.id.wish_cancel:
                                        wishMessageList.get(position).setWishState("已取消");
                                        wishMessageList.get(position).setEndTime(year+"-"+month);
                                        fragmentAdapter.notifyDataSetChanged();
                                        Toast.makeText(getContext(), "你选择了取消选项", Toast.LENGTH_SHORT).show();
                                        break;
                                    case R.id.add_wish:
                                        Toast.makeText(getContext(), "你选择了添加选项", Toast.LENGTH_SHORT).show();
                                        break;
                                }
                                return false;
                            }

                        });
                    }else {
                        PopupMenu popupMenu = new PopupMenu(getContext(), view);
                        popupMenu.inflate(R.menu.other_popup_menu);
                        popupMenu.show();
                        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                            @Override
                            public boolean onMenuItemClick(MenuItem item) {
                                switch (item.getItemId()) {
                                    case R.id.other_add_wish:
                                        Toast.makeText(getContext(), "你选择了添加选项", Toast.LENGTH_SHORT).show();
                                        break;
                                }
                                return false;
                            }

                        });
                    }
                return false;
            }
        });

        return view;
    }


    public class FragmentAdapter extends BaseAdapter {
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
            if (convertView == null) {
                convertView = View.inflate(getContext(), R.layout.wish_list, null);
                new ViewHolder(convertView);
            }
            ViewHolder holder = (ViewHolder) convertView.getTag();
            holder.wishName.setText(wishMessageList.get(position).getWishName());
            holder.wishPrice.setText(wishMessageList.get(position).getWishPrice());
            holder.wishState.setText(wishMessageList.get(position).getWishState());
            holder.startTime.setText(wishMessageList.get(position).getStartTime());
            holder.endTime.setText(wishMessageList.get(position).getEndTime());
            return convertView;
        }

        class ViewHolder {
            TextView wishName;
            TextView wishPrice;
            TextView wishState;
            TextView startTime;
            TextView endTime;

            public ViewHolder(View view) {
                wishName = view.findViewById(R.id.wish_name);
                wishPrice = view.findViewById(R.id.wish_price);
                wishState = view.findViewById(R.id.wish_state);
                startTime = view.findViewById(R.id.wish_start_time);
                endTime = view.findViewById(R.id.wish_end_time);
                view.setTag(this);
            }
        }
    }
}

