package com.huojitang.leaf;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class WishFragment<FragmentAdapter> extends Fragment {
    private String[] names = {"相机","小米九","平板电脑","内存条","移动硬盘"};
    private String[] price = {"2000.00元","3199.00元","1700.00元","300.00元","500.00元"};
    private String[] states = {"已完成","已完成","已取消","未完成","已取消"};
    private String[] starttime = {"2019-1","2019-1","2019-2","2019-4","2019-4"};
    private String[] endtime = {"2019-3","2019-4","2019-3","","2019-5"};
    ListView listView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedTnstanceState){
        View view = inflater.inflate(R.layout.fragment_wish,container,false);

        listView = (ListView) view.findViewById(R.id.LV_2);

        WishFragment.FragmentAdapter fragmentAdapter = new WishFragment.FragmentAdapter();
        listView.setAdapter(fragmentAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                AlertDialog.Builder builder;
                builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("删除？").setNegativeButton(R.string.g_back,null);
                builder.setPositiveButton(R.string.g_confirm, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        getActivity().notifyAll();
                    }
                });
                builder.show();
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                AlertDialog.Builder builder;
                builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("添加？").setNegativeButton(R.string.g_back,null);
                builder.setPositiveButton(R.string.g_confirm, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        ;
                    }
                });
                builder.show();
                return false;
            }
        });
        return view;
    }

    public class FragmentAdapter extends BaseAdapter{
        @Override
        public int getCount() {
            return names.length;
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

            TextView textView1 = (TextView) view.findViewById(R.id.wish_name);
            TextView textView2 = (TextView) view.findViewById(R.id.wish_price);
            TextView textView3 = (TextView) view.findViewById(R.id.wish_state);
            TextView textView4 = (TextView) view.findViewById(R.id.wish_start_time);
            TextView textView5 = (TextView) view.findViewById(R.id.wish_end_time);

            textView1.setText(names[position]);
            textView2.setText(price[position]);
            textView3.setText(states[position]);
            textView4.setText(starttime[position]);
            textView5.setText(endtime[position]);
            return view;
        }
    }
}

