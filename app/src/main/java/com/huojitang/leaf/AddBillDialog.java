package com.huojitang.leaf;

import android.app.Dialog;
import android.content.Context;
import android.text.InputFilter;
import android.text.InputType;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.huojitang.leaf.dao.BillItemDao;
import com.huojitang.leaf.dao.MonthlyBillDao;
import com.huojitang.leaf.dao.TagDao;
import com.huojitang.leaf.global.LeafApplication;
import com.huojitang.leaf.model.BillItem;
import com.huojitang.leaf.model.MonthlyBill;
import com.huojitang.leaf.model.Tag;
import com.huojitang.leaf.util.LeafDateSupport;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

public class AddBillDialog extends Dialog {

    private List<Tag> tags;
    private TagDao tagDao = TagDao.getInstance();
    private List<String> data_list;
    private ArrayAdapter<String> arr_adapter;
    private int tagId;

    //一些从界面里的组件
    private Button cancel;
    private EditText name;
    private Spinner tagName;
    private EditText price;
    private Button confirm;

    public AddBillDialog(Context context,List<Tag> tags, final ConfirmOnclickListener listener) {
        super(context);
        this.setCancelable(true);
        this.setCanceledOnTouchOutside(true);

        this.setContentView(R.layout.bill_dialog_lay);
        this.tagName = findViewById(R.id.tag_spinner);
        this.name = findViewById(R.id.dialog_add_item_name);
        this.price = findViewById(R.id.item_price);
        this.confirm = findViewById(R.id.dialog_add_tag_confirm);
        this.cancel = findViewById(R.id.dialog_add_tag_cancel);

        this.tags=tags;

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddBillDialog.this.dismiss();
            }
        });
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(getName().equals("")){
                    Toast.makeText(getContext(),"请输入物品名",Toast.LENGTH_SHORT).show();
                }
                else{
                    //时间
                    LocalDate localDate = LeafDateSupport.getCurrentLocalDate();
                    YearMonth currentYearMonth = LeafDateSupport.getCurrentYearMonth();
                    MonthlyBill currentMonthlyBill = MonthlyBillDao.getInstance().getByDate(currentYearMonth.toString());

                    //插入数据
                    BillItemDao billItemDao = BillItemDao.getInstance();
                    BillItem billItem = new BillItem();
                    billItem.setName(getName());
                    billItem.setValue(getPrice());
                    billItem.setTag(tagDao.getById(getId()));
                    billItem.setMonthlyBill(currentMonthlyBill);
                    billItem.setDay(localDate.getDayOfMonth());
                    billItemDao.add(billItem);
                }

                listener.ConfirmClick();
                AddBillDialog.this.dismiss();
            }
        });
        initDateList();

        initAdapter();

        name.setFilters(new InputFilter[]{new InputFilter.LengthFilter(20)});

        price.setInputType(InputType.TYPE_CLASS_NUMBER|InputType.TYPE_NUMBER_FLAG_DECIMAL);

        //默认控制输入9位数，小数点前6位，后2位，小数一位，共9位
        InputFilter[] filters = {new CashierInputFilter(9), new InputFilter.LengthFilter(9)};

        price.setFilters(filters);

    }
    private void initDateList() {
        data_list = new ArrayList<>();
        for (int i = 0; i < tags.size(); i++) {
            String data = tags.get(i).getName();
            data_list.add(data);
        }
    }

    private void initAdapter() {
        //适配器
        arr_adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, data_list);
        //设置样式
        arr_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //加载适配器
        tagName.setAdapter(arr_adapter);

        tagName.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                tagId = tags.get(position).getId();
                Toast.makeText(getContext(), "你选择了第" + position + "个", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(getContext(), "nothing", Toast.LENGTH_SHORT).show();

            }
        });
    }

    private int getId() {
        return tagId;
    }

    private String getName() {
        return name.getText().toString().trim();
    }

    private double getPrice() {
        return price.getText().length()==0?0:Double.parseDouble(price.getText().toString().trim());
    }

    public interface ConfirmOnclickListener {
        public void ConfirmClick();
    }




}
