package com.huojitang.leaf;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.huojitang.leaf.dao.WishDao;
import com.huojitang.leaf.global.LeafApplication;
import com.huojitang.leaf.model.Wish;
import com.huojitang.leaf.util.PriceTransUtil;

public class WishDetailsActivity extends AppCompatActivity {
    private Wish wish;
    private WishDao wishDao = WishDao.getInstance();
    private EditText wishName;
    private EditText wishValue;
    private TextView wishState;
    private TextView wishStartTime;
    private TextView wishEndTime;
    private EditText wishDetails;
    private Button wishModify;
    private Button wishDelete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wish_details);
        Intent i = getIntent();
        int po = i.getIntExtra(LeafApplication.LEAF_MASSAGE,0);
        wish = wishDao.getById(po);

        wishName = findViewById(R.id.get_wish_details_name);
        wishValue = findViewById(R.id.get_wish_details_price);
        wishState = findViewById(R.id.get_wish_details_state);
        wishStartTime = findViewById(R.id.get_wish_details_startTime);
        wishEndTime = findViewById(R.id.get_wish_details_endTime);
        wishDetails = findViewById(R.id.get_wish_details_detail);
        wishModify = findViewById(R.id.detail_modify);
        wishDelete = findViewById(R.id.detail_delete);


        wishName.setText(wish.getName());
        wishValue.setText(PriceTransUtil.Int2Decimal(wish.getValue()));
        wishDetails.setText(wish.getComment());
        if(wish.getState()==0){
            wishState.setText("未完成");
        }else if(wish.getState()==1){
            wishState.setText("已取消");
        }else {
            wishState.setText("已完成");
        }
        wishStartTime.setText(wish.getStartTime());
        wishEndTime.setText(wish.getFinishedTime());


        wishModify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(wishName.getText().toString().trim().equals("")){
                    Toast.makeText(WishDetailsActivity.this,"修改后的物品名不能为空", Toast.LENGTH_SHORT).show();
                }else {
                    wish.setName(wishName.getText().toString().trim());
                    wish.setValue(wishValue.getText().length() == 0?0:Double.parseDouble(wishValue.getText().toString()));
                    wish.setComment(wishDetails.getText().toString().trim());
                    wishDao.update(wish);
                    finish();
                }
            }
        });
        wishDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wishDao.delete(wish);
                finish();
            }
        });
        findViewById(R.id.detail_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        wishName.setFilters(new InputFilter[]{new InputFilter.LengthFilter(20)});
        wishValue.setInputType(InputType.TYPE_CLASS_NUMBER|InputType.TYPE_NUMBER_FLAG_DECIMAL);
        //默认控制输入9位数，小数点前6位，后2位，小数一位，共9位
        InputFilter[] filters={new CashierInputFilter(9),new InputFilter.LengthFilter(9)};
        wishValue.setFilters(filters);


    }
}
