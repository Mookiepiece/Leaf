package com.huojitang.leaf;

import android.app.Dialog;
import android.content.Context;
import android.text.InputFilter;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class AddWishDialog extends Dialog {
    private Button cancel;
    private EditText wishName;
    private EditText wishPrice;
    private Button confirm;
    private EditText wishDetails;

    public AddWishDialog(Context context, WishConfirmOnclickListener listener) {
        super(context);
        this.setCancelable(true);
        this.setCanceledOnTouchOutside(true);

        this.setContentView(R.layout.dialog_add_wish);
        this.wishName = findViewById(R.id.wish_add_item_name);
        this.wishPrice = findViewById(R.id.add_wish_price);
        this.wishDetails = findViewById(R.id.wish_details);
        this.confirm = findViewById(R.id.wish_dialog_add_confirm);
        this.cancel = findViewById(R.id.wish_dialog_add_cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddWishDialog.this.dismiss();
            }
        });
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.ConfirmClick();
                AddWishDialog.this.dismiss();
            }
        });

        wishName.setFilters(new InputFilter[]{new InputFilter.LengthFilter(20)});

        wishDetails.setFilters(new InputFilter[]{new InputFilter.LengthFilter(100)});

        wishPrice.setInputType(InputType.TYPE_CLASS_NUMBER|InputType.TYPE_NUMBER_FLAG_DECIMAL);

        InputFilter[] filters={new CashierInputFilter(9),new InputFilter.LengthFilter(9)};

        wishPrice.setFilters(filters);
    }

    public EditText getWishName() {
        return wishName;
    }

    public String getWishPrice() {
        return wishPrice.getText().toString().trim();
    }

    public EditText getWishDetails() { return wishDetails; }

    public interface WishConfirmOnclickListener {
        public void ConfirmClick();
    }
}
