package com.huojitang.leaf;

import android.content.Intent;
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

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.huojitang.leaf.dao.WishDao;
import com.huojitang.leaf.model.Wish;
import com.huojitang.util.LeafDateSupport;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

public class MainActivityWishFragment<FragmentAdapter> extends Fragment {

    private void initWishMessage() {
        wishList = wishDao.listAll();

    }

    private List<Wish> wishList = new ArrayList<>();//TODO
    private WishDao wishDao = WishDao.getInstance();
    ListView mListView;
    FloatingActionButton fab;
    AddWishDialog addWishDialog;
    String wishName;
    double wishPrice;
    String wishDetails;
    String year;
    String month;
    String day;
    Calendar calendar;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_wish, container, false);
        calendar = Calendar.getInstance();
        calendar.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
        year = String.valueOf(calendar.get(Calendar.YEAR));
        if (calendar.get(Calendar.MONTH) < 10) {
            month = "0" + String.valueOf(calendar.get(Calendar.MONTH) + 1);
        } else {
            month = String.valueOf(calendar.get(Calendar.MONTH) + 1);
        }

        fab = view.findViewById(R.id.floatingActionButton);

        mListView = view.findViewById(R.id.LV_2);
        initWishMessage();

        final WishAdapter wishAdapter = new WishAdapter();
        mListView.setAdapter(wishAdapter);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                if (wishList.get(position).getState() == Wish.WISH_TODO) {
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
                                case R.id.see_details:
                                    Intent intent = new Intent(getContext(), WishDetailsActivity.class);
                                    intent.putExtra("position", position);
                                    startActivity(intent);
                                    Toast.makeText(getContext(), "你选择了心愿详情选项", Toast.LENGTH_SHORT).show();
                                    break;
                            }
                            return true;
                        }

                    });
                } else {
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

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addWishDialog = new AddWishDialog(getContext(), new AddWishDialog.WishConfirmOnclickListener() {
                    @Override
                    public void ConfirmClick() {
                        if (addWishDialog.getWishName().getText().toString().trim().equals("") || addWishDialog.getWishPrice().trim().equals("")) {
                            Toast.makeText(getContext(), "心愿名为空或者价格为空，无法添加", Toast.LENGTH_SHORT).show();
                        } else {

                            wishName = addWishDialog.getWishName().getText().toString();
                            wishPrice = Double.parseDouble(addWishDialog.getWishPrice());
                            wishDetails = addWishDialog.getWishDetails().getText().toString();
                            Wish wish = new Wish();
                            wish.setName(wishName);
                            wish.setValue(wishPrice);
                            wish.setComment(wishDetails);
                            wish.setStartTime(LeafDateSupport.getCurrentLocalDate());
                            WishDao.getInstance().add(wish);
                            wishList.add(wish);
                            wishAdapter.notifyDataSetChanged();
                            Toast.makeText(getContext(), "悬浮按钮添加心愿", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                addWishDialog.show();
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
            holder.startTime.setText(wishList.get(position).getStartTime());
            holder.finishedTime.setText(wishList.get(position).getFinishedTime());

            if(wishList)
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

