package com.huojitang.leaf;

import android.app.Dialog;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.huojitang.leaf.model.Tag;

/**
 * 添加标签对话框
 * 传入一个TagEntity对象并对其进行修改
 */
public class AddTagDialog extends Dialog {

    //一些从界面里的组件
    private Button cancel;
    private EditText name;
    private EditText limit;
    private Button confirm;

    //TODO MK 是否是合法输入的flag,现在缺乏参数判断
    private boolean properInput=false;

    //TODO MK 构造方法里传入的空的一个实体，但是得想办法给它赋值并返回
    private Tag tag;

    public AddTagDialog(Context context,Tag tag, final ConfirmOnclickListener listener) {
        super(context);
        this.setCancelable(true);
        this.setCanceledOnTouchOutside(true);
        this.tag=tag;

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

        //TODO MK 未完成的文本框输入判断
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

    /*
    TODO MK listener接口，为了每次实例化这个对话框都能必须实现onclick功能
    TODO MK 但是本对话框类的功能很固定，现在在想是否将本对话框弄成EditTagActivity的内部类
    */
    public interface ConfirmOnclickListener {
        public void ConfirmClick();
    }
}
