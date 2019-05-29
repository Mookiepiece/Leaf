package com.huojitang.leaf;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class BillActivity extends Activity {
    private String[] names = {"书籍","KTV","聚餐","水电费"};
    private String[] prices = {"30.00元","150.00元","50.00元","30.00元"};

    private List<String> stringsName = new ArrayList<>();
    private List<String> stringsPrice = new ArrayList<>();
    private int index = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_2);
        listName();
        listPrice();


        ListView listView = (ListView) findViewById(R.id.LV_3);
        final BillAdapter billAdapter = new BillAdapter();
        listView.setAdapter(billAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog.Builder builder;
                builder = new AlertDialog.Builder(BillActivity.this);
                builder.setTitle("删除？").setNegativeButton(R.string.g_back,null);
                builder.setPositiveButton(R.string.g_confirm, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        stringsName.remove(position);
                        stringsPrice.remove(position);
                    }
                });
                builder.show();
            }
        });


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // allTags.add("sdedfssa");
                // MainActivityBillFragment.this.notifyAll();
                inputTitleDialog();
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
//                listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
//                    @Override
//                    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
//                        Toast.makeText(getActivity(),"sudahiwu",Toast.LENGTH_SHORT).show();
//                        return false;
//                    }
//                });
            }
        });
    }


    public List<String> listName(){
        for(int i=0;i<names.length;i++){
            stringsName.add(names[i]);
        }
        return stringsName;
    }

    public List<String> listPrice(){
        for(int i=0;i<prices.length;i++){
            stringsPrice.add(prices[i]);
        }
        return stringsPrice;
    }

    public class BillAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return names.length;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = getLayoutInflater().inflate(R.layout.item2,parent,false);

            TextView textView2 = view.findViewById(R.id.name_1);
            TextView textView3 = view.findViewById(R.id.price_1);

            textView2.setText(stringsName.get(position));
            textView3.setText(stringsPrice.get(position));

            return view;
        }
    }

    private void inputTitleDialog(){
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.alert_edittext,(ViewGroup) findViewById(R.id.dialog),false);


        final EditText inputServer = layout.findViewById(R.id.Ev_1);
        final EditText inputServer2 = layout.findViewById(R.id.Ev_2);
        inputServer.setFocusable(true);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.app_name)).setIcon(R.drawable.add_new)
                .setView(layout);
        builder.setNegativeButton(getString(R.string.g_back),null);
        builder.setPositiveButton(getString(R.string.g_confirm),new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                String inputName = inputServer.getText().toString();
                String inputprice = inputServer2.getText().toString();
                stringsName.add(inputName);
                stringsPrice.add(inputprice);
            }
        });
        builder.show();

    }

}
