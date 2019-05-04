package com.huojitang.leaf;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ListFragment extends Fragment {
    private String[] tags = {"学习","娱乐","生活用品","其他"};
    ImageButton imageButton;
    List<String> allTags = new ArrayList<String>();
    ListView listView ;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedTnstanceState){
        View view = inflater.inflate(R.layout.fragment1,container,false);

        list();
        listView = (ListView) view.findViewById(R.id.LV_1);
        final MyAdapter myAdapter = new MyAdapter();
        listView.setAdapter(myAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(),BillActivity.class);
                startActivity(intent);
                Toast.makeText(getActivity(),"sfsdfs",Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

    public List<String>list(){
        for(int i=0;i<tags.length;i++){
            allTags.add(tags[i]);
        }
        return allTags;
    }

    public class MyAdapter extends BaseAdapter{

        @Override
        /*
         * 用于返回listview条目的数量
         */
        public int getCount() {
            return allTags.size();
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
        /*
         * 根据position返回当前这个条目的对应的View对象
         */
        public View getView(final int position, View convertView, ViewGroup parent) {
            View view = getLayoutInflater().inflate(R.layout.listview1_item,null);
            TextView textView1 = (TextView) view.findViewById(R.id.tagtitle);


            // 为textview1和textview2赋值
            textView1.setText(allTags.get(position));

            /*imageButton = (ImageButton) view.findViewById(R.id.deletelistview);
            imageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Snackbar.make(v, "Replace with your own action", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    allTags.remove(position);
                    MyAdapter.this.notifyDataSetChanged();
                }
            });*/



            // 返回这个条目对应的view对象
            return view;
        }
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
    }

}