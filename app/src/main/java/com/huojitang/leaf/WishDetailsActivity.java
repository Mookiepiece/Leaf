package com.huojitang.leaf;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.huojitang.leaf.dao.WishDao;
import com.huojitang.leaf.model.Tag;
import com.huojitang.leaf.model.Wish;

import java.util.ArrayList;
import java.util.List;

public class WishDetailsActivity extends AppCompatActivity {
    List<Wish> wishList = new ArrayList<>();
    WishDao wishDao = WishDao.getInstance();
    EditText wishName;
    EditText wishValue;
    TextView wishState;
    TextView wishStartTime;
    TextView wishEndTime;
    Button wishModify;
    Button wishDelete;
    Intent i = getIntent();
    int position = i.getIntExtra("position",0);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wishdetails);


        wishList = wishDao.listAll();
        wishName = findViewById(R.id.get_wish_details_name);
        wishValue = findViewById(R.id.get_wish_details_price);
        wishState = findViewById(R.id.get_wish_details_state);
        wishStartTime = findViewById(R.id.get_wish_details_startTime);
        wishEndTime = findViewById(R.id.get_wish_details_endTime);
        wishModify = findViewById(R.id.detail_modify);
        wishDelete = findViewById(R.id.detail_delete);

        initDetails();

        wishModify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wishList.get(position).setName(wishName.getText().toString());
                wishList.get(position).setValue(Integer.parseInt(wishValue.getText().toString()));
               // Intent intent = new Intent()
            }
        });
        wishDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wishList.remove(position);
            }
        });

    }

    private void initDetails() {
        wishName.setText(wishList.get(position).getName());
        wishValue.setText(wishList.get(position).getValue());
        if(wishList.get(position).getState()==0){
            wishState.setText("未完成");
        }else if(wishList.get(position).getState()==1){
            wishState.setText("已取消");
        }else {
            wishState.setText("已完成");
        }
        wishStartTime.setText(wishList.get(position).getStartTime());
        wishEndTime.setText(wishList.get(position).getFinishedTime());
    }

}
