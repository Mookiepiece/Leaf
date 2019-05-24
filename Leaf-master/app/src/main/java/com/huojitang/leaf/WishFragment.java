package com.huojitang.leaf;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.wifi.WifiConfiguration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class WishFragment<FragmentAdapter> extends Fragment {
    private String[] names = {"相机","小米九","平板电脑","内存条","移动硬盘"};
    private String[] price = {"2000.00元","3199.00元","1700.00元","300.00元","500.00元"};
    private String[] states = {"已完成","已完成","已取消","未完成","已取消"};
    private String[] starttime = {"2019-01","2019-01","2019-02","2019-04","2019-04"};
    private String[] endtime = {"2019-03","2019-04","2019-03","","2019-05"};

    private List<WishMessage> wishMessageList = new ArrayList<>();
    ListView listView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedTnstanceState){
        View view = inflater.inflate(R.layout.fragment_wish,container,false);

        listView = (ListView) view.findViewById(R.id.LV_2);
        initWishMessage();

        final WishFragment.FragmentAdapter fragmentAdapter = new WishFragment.FragmentAdapter();
        listView.setAdapter(fragmentAdapter);
        return view;
    }

    private void initWishMessage() {
        for(int i = 0;i<names.length;i++){
            WishMessage wishMessage = new WishMessage(names[i],price[i],states[i],starttime[i],endtime[i]);
            wishMessageList.add(wishMessage);
        }
    }

    public class FragmentAdapter extends BaseAdapter{
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

            TextView wishName = view.findViewById(R.id.wish_name);
            TextView wishPrice = view.findViewById(R.id.wish_price);
            TextView wishState = view.findViewById(R.id.wish_state);
            TextView startTime = view.findViewById(R.id.wish_start_time);
            TextView endTime = view.findViewById(R.id.wish_end_time);

            wishName.setText(wishMessageList.get(position).getWishName());
            wishPrice.setText(wishMessageList.get(position).getWishPrice());
            wishState.setText(wishMessageList.get(position).getWishState());
            startTime.setText(wishMessageList.get(position).getStartTime());
            endTime.setText(wishMessageList.get(position).getEndTime());

            return view;
        }
    }

}

