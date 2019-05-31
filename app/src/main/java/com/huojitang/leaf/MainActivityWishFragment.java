package com.huojitang.leaf;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.appcompat.widget.PopupMenu;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.huojitang.leaf.dao.WishDao;
import com.huojitang.leaf.model.Wish;
import com.huojitang.leaf.util.LeafDateSupport;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MainActivityWishFragment<FragmentAdapter> extends Fragment {
//    private String[] names = {"相机","小米九","平板电脑","内存条","移动硬盘"};
//    private String[] price = {"2000.00元","3199.00元","1700.00元","300.00元","500.00元"};
//    private String[] states = {"已完成","已完成","已取消","未完成","已取消"};
//    private String[] starttime = {"2019-01","2019-01","2019-02","2019-04","2019-04"};
//    private String[] endtime = {"2019-03","2019-04","2019-03","","2019-05"};

    private void initWishMessage() {
        wishList = wishDao.listAll();

    }

    private List<Wish> wishList = new ArrayList<>();//TODO
    private WishDao wishDao = WishDao.getInstance();
    ListView mListView;
    Calendar calendar;
    String year;
    String month;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_wish,container,false);
//        calendar = Calendar.getInstance();
//        calendar.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
//        year = String.valueOf(calendar.get(Calendar.YEAR));
//        if(calendar.get(Calendar.MONTH)<10){
//            month = "0" + String.valueOf(calendar.get(Calendar.MONTH)+1);
//        }else {
//            month = String.valueOf(calendar.get(Calendar.MONTH)+1);
//        }

        mListView = view.findViewById(R.id.LV_2);
        initWishMessage();

        final WishAdapter wishAdapter = new WishAdapter();
        mListView.setAdapter(wishAdapter);

        mListView.setOnItemClickListener (new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                    if(wishList.get(position).getState()==Wish.WISH_TODO){
                        PopupMenu popupMenu = new PopupMenu(getContext(), view);
                        popupMenu.inflate(R.menu.popup_menu);
                        popupMenu.show();
                        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                            @Override
                            public boolean onMenuItemClick(MenuItem item) {
                                switch (item.getItemId()) {
                                    case R.id.wish_finish:
                                        wishList.get(position).setState(Wish.WISH_ACHIEVED);
                                        wishList.get(position).setFinishedTime(LeafDateSupport.getCurrentLocalDate().toString());
                                        wishAdapter.notifyDataSetChanged();
                                        Toast.makeText(getContext(), "你选择了完成选项", Toast.LENGTH_SHORT).show();
                                        break;
                                    case R.id.wish_cancel:
                                        wishList.get(position).setState(Wish.WISH_ACHIEVED);
                                        wishList.get(position).setFinishedTime(LeafDateSupport.getCurrentLocalDate().toString());
                                        wishAdapter.notifyDataSetChanged();
                                        Toast.makeText(getContext(), "你选择了取消选项", Toast.LENGTH_SHORT).show();
                                        break;
                                    case R.id.add_wish:
                                        Toast.makeText(getContext(), "你选择了添加选项", Toast.LENGTH_SHORT).show();
                                        break;
                                }
                                return true;
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
                                return true;
                            }

                        });
                    }
            }
        });

        return view;
    }


    public class WishAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return wishList.size();
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
            holder.name.setText(wishList.get(position).getName());
            holder.value.setText(String.valueOf(wishList.get(position).getValue()));
            holder.state.setText(String.valueOf(wishList.get(position).getState()));
            holder.startTime.setText(wishList.get(position).getStartTime());
            holder.finishedTime.setText(wishList.get(position).getFinishedTime());
            return convertView;
        }

        class ViewHolder {
            TextView name;
            TextView value;
            TextView state;
            TextView startTime;
            TextView finishedTime;

            public ViewHolder(View view) {
                name = view.findViewById(R.id.wish_name);
                value = view.findViewById(R.id.wish_price);
                state = view.findViewById(R.id.wish_state);
                startTime = view.findViewById(R.id.wish_start_time);
                finishedTime = view.findViewById(R.id.wish_end_time);
                view.setTag(this);
            }
        }
    }
}

