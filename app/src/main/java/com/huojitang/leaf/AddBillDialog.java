package com.huojitang.leaf;

import android.app.Dialog;
import android.content.Context;
import android.text.InputFilter;
import android.text.InputType;
import android.text.Spanned;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.huojitang.leaf.dao.TagDao;
import com.huojitang.leaf.model.Tag;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AddBillDialog extends Dialog {

    private List<Tag> tags;
    private TagDao tagDao = TagDao.getInstance();
    private List<String> data_list;
    private ArrayAdapter<String> arr_adapter;
    private String message;

    //一些从界面里的组件
    private Button cancel;
    private EditText name;
    private Spinner tagName;
    private EditText price;
    private Button confirm;

    public AddBillDialog(Context context, final ConfirmOnclickListener listener) {
        super(context);
        this.setCancelable(true);
        this.setCanceledOnTouchOutside(true);

        this.setContentView(R.layout.bill_dialog_lay);
        this.tagName = findViewById(R.id.tag_spinner);
        this.name = findViewById(R.id.dialog_add_item_name);
        this.price = findViewById(R.id.item_price);
        this.confirm = findViewById(R.id.dialog_add_tag_confirm);
        this.cancel = findViewById(R.id.dialog_add_tag_cancel);

        tags = tagDao.list(false);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddBillDialog.this.dismiss();
            }
        });
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.ConfirmClick();
                AddBillDialog.this.dismiss();
            }
        });
        initDateList();

        initAdapter();

        name.setFilters(new InputFilter[]{new InputFilter.LengthFilter(20)});

        price.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL);
        //默认控制输入9位数，小数点前6位，后2位，小数一位，共9位
        InputFilter[] filters = {new CashierInputFilter(9), new InputFilter.LengthFilter(9)};

        price.setFilters(filters);

    }

    public void initAdapter() {
        //适配器
        arr_adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, data_list);
        //设置样式
        arr_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //加载适配器
        tagName.setAdapter(arr_adapter);

        tagName.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                message = data_list.get(position);
                Toast.makeText(getContext(), "你选择了第" + position + "个", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(getContext(), "nothing", Toast.LENGTH_SHORT).show();

            }
        });
    }

    public void initDateList() {
        data_list = new ArrayList<>();
        for (int i = 0; i < tags.size(); i++) {
            String data = tags.get(i).getName();
            data_list.add(data);
        }
    }

    public String getMessage() {
        return message;
    }

    public EditText getName() {
        return name;
    }

    public EditText getPrice() {
        return price;
    }

    public interface ConfirmOnclickListener {
        public void ConfirmClick();
    }


}
