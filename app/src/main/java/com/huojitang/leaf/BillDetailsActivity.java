package com.huojitang.leaf;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.huojitang.leaf.dao.BillItemDao;
import com.huojitang.leaf.global.LeafApplication;
import com.huojitang.leaf.model.BillItem;

public class BillDetailsActivity extends AppCompatActivity {
    private EditText detailBillName;
    private EditText detailBillPrice;
    private TextView detailBillTime;
    private TextView detailBillTag;
    private Button billModify;
    private Button billDelete;
    private BillItem billItem =new BillItem();
    private BillItemDao billItemDao = BillItemDao.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_billdetails);
        Intent i = getIntent();
        int po = i.getIntExtra(LeafApplication.LEAF_MASSAGE,0);
        billItem = billItemDao.getById(po);

        detailBillName = findViewById(R.id.get_bill_details_name);
        detailBillPrice = findViewById(R.id.get_bill_details_price);
        detailBillTime = findViewById(R.id.get_bill_details_time);
        detailBillTag = findViewById(R.id.get_bill_details_tag);
        billModify = findViewById(R.id.bill_detail_modify);
        billDelete = findViewById(R.id.bill_detail_delete);

        detailBillName.setText(billItem.getName());
        detailBillPrice.setText(String.valueOf((double)billItem.getValue()/100));
        detailBillTime.setText(""+billItem.getDay());
        detailBillTag.setText("吃");

        billModify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                billItem.setName(detailBillName.getText().toString());
                billItem.setValue(Double.parseDouble(detailBillPrice.getText().toString()));
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
}
