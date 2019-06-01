package com.huojitang.leaf;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.huojitang.leaf.dao.BillItemDao;
import com.huojitang.leaf.dao.TagDao;
import com.huojitang.leaf.global.LeafApplication;
import com.huojitang.leaf.model.BillItem;
import com.huojitang.leaf.model.Tag;

import java.util.ArrayList;
import java.util.List;

import static com.huojitang.leaf.global.LeafApplication.getContext;

public class BillDetailsActivity extends AppCompatActivity {
    private EditText detailBillName;
    private EditText detailBillPrice;
    private TextView detailBillTime;
    private Spinner detailBillTag;
    private Button billModify;
    private Button billDelete;
    private List<String> tagName;
    private List<Tag> tags;
    private TagDao tagDao = TagDao.getInstance();
    private BillItem billItem ;
    private BillItemDao billItemDao = BillItemDao.getInstance();
    private ArrayAdapter<String> tagAdapter;
    private int tagPosition;
    //private int []tagIdArr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_billdetails);

        tags = tagDao.list(false);
        initData();

        Intent i = getIntent();
        int po = i.getIntExtra(LeafApplication.LEAF_MASSAGE,0);
        billItem = billItemDao.getById(po);

        detailBillName = findViewById(R.id.get_bill_details_name);
        detailBillPrice = findViewById(R.id.get_bill_details_price);
        detailBillTime = findViewById(R.id.get_bill_details_time);
        detailBillTag = findViewById(R.id.get_bill_details_tag);
        billModify = findViewById(R.id.bill_detail_modify);
        billDelete = findViewById(R.id.bill_detail_delete);

        Tag t=billItem.getTag();
        detailBillName.setText(billItem.getName());
        detailBillPrice.setText(String.valueOf((double)billItem.getValue()/100));
        detailBillTime.setText(""+billItem.getDay());

        initAdapter();


        billModify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                billItem = billItemDao.getById(po);
                billItem.setName(detailBillName.getText().toString());
                billItem.setValue(Double.parseDouble(detailBillPrice.getText().toString()));
                billItem.setTag(tags.get(tagPosition));
                billItemDao.update(billItem);
                finish();
            }
        });
        billDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                billItemDao.delete(billItem);
                finish();
            }
        });
    }

    private void initAdapter() {
        tagAdapter = new ArrayAdapter<String>(getContext(),android.R.layout.simple_spinner_item,tagName);

        tagAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        detailBillTag.setAdapter(tagAdapter);

        detailBillTag.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                tagPosition = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void initData(){
        String str;
        //tagIdArr = new int[tags.size()];
        tagName = new ArrayList<>();
        for(int i =0;i<tags.size();i++){
            str = tags.get(i).getName();
            tagName.add(str);
        }
    }
}
