package com.huojitang.leaf;

import android.app.Dialog;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;


/**
 * 添加标签对话框
 * 传入一个TagEntity对象并对其进行修改
 */
public class AddTagDialog extends Dialog {

    private Button cancel;
    private EditText name;
    private EditText limit;
    private Button confirm;
    private boolean properInput=false;
    private TagEntity tagEntity;

    public AddTagDialog(Context context,TagEntity tagEntity, final ConfirmOnclickListener listener) {
        super(context);
        this.setCancelable(true);
        this.setCanceledOnTouchOutside(true);
        this.tagEntity=tagEntity;

        this.setContentView(R.layout.dialog_add_tag);
        this.name=findViewById(R.id.dialog_add_tag_name);
        this.limit=findViewById(R.id.dialog_add_tag_limit);
        this.confirm=findViewById(R.id.dialog_add_tag_confirm);
        this.cancel=findViewById(R.id.dialog_add_tag_cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddTagDialog.this.dismiss();
            }
        });
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.ConfirmClick();
                AddTagDialog.this.dismiss();
            }
        });
        name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.length()>20)
                    properInput=false;
                else
                    properInput=true;
            }

        });


    }

    public interface ConfirmOnclickListener {
        public void ConfirmClick();
    }
}
