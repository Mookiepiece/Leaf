package com.huojitang.leaf;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;

import com.huojitang.leaf.activity.ChartActivity;
import com.huojitang.leaf.activity.ListTagActivity;
import com.huojitang.leaf.dao.MonthlyBillDao;
import com.huojitang.leaf.dao.TagDao;
import com.huojitang.leaf.global.LeafApplication;
import com.huojitang.leaf.model.BillItem;
import com.huojitang.leaf.model.MonthlyBill;
import com.huojitang.leaf.model.Tag;
import com.huojitang.leaf.util.LeafDateSupport;
import com.huojitang.leaf.util.PriceTransUtil;
import com.huojitang.leaf.view.TagIconResultView;

import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.jetbrains.annotations.NotNull;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivityBillFragment extends Fragment {

    //创建进度条
    private int status = 0;
    private ProgressBar bar;

    private RecyclerView recyclerView;
    private RvAdapter mRvAdapter;
    private AddBillDialog addBillDialog;
    /** 首页显示当前日期 */
    private TextView headerDateTextView;
    /** 显示当月消费总量 */
    private TextView totalConsumptionTextView;

    List<Tag> tags;
    TagDao tagDao= TagDao.getInstance();
    private MonthlyBillDao monthlyBillDao = MonthlyBillDao.getInstance();

    private MonthlyBill currentMonthlyBill = MonthlyBillDao.getInstance().getByYearMonth(LeafDateSupport.getCurrentYearMonth());

    private boolean containResevrerdTag = false;

    private void reread(){
        if(tagDao.getReservedTag().getBillItems(currentMonthlyBill).size()>0){
            containResevrerdTag=true;
            tags = tagDao.list(true);
        }
        else{
            containResevrerdTag=false;
            tags = tagDao.list(false);
        }
        mRvAdapter.notifyDataSetChanged();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bill, container, false);
        recyclerView = view.findViewById(R.id.bill_recycler_view_1);
        status = 20;

        initRecyclerView();
        reread();

        //设置跳转报表界面
        view.findViewById(R.id.button_to_bill_chart_activity).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext() , ChartActivity.class));
            }
        });
        //设置悬浮按钮的点击事件
        view.findViewById(R.id.button_add_bill).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(LeafApplication.getContext(),""+containResevrerdTag,Toast.LENGTH_SHORT).show();

                if(tags.size()==0||(tags.size()==1&&containResevrerdTag)){
                    Toast.makeText(LeafApplication.getContext(),"未检测到标签，加入你的第一个标签吧",Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getContext() , ListTagActivity.class));
                    return;
                }

                List<Tag> selectableTags=tags;
                if(containResevrerdTag){
                    Collections.copy(selectableTags, tags);
                    for(int i=0;i<selectableTags.size();i++){
                        if(selectableTags.get(i).isReserved()){
                            selectableTags.remove(i);
                        }
                    }
                }

                addBillDialog = new AddBillDialog(getActivity(),selectableTags, new AddBillDialog.ConfirmOnclickListener() {
                    @Override
                    public void ConfirmClick() {
                        reread();
                        updateHeader();
                    }
                });
                addBillDialog.show();
            }
        });
        return view;
    }

    private void updateHeader() {
        LocalDate localDate = LeafDateSupport.getCurrentLocalDate();
        headerDateTextView.setText(String.format(
                Locale.getDefault(),
                getResources().getString(R.string.bill_header_date_text_view_text),
                localDate.toString()
        ));
        bar.setMax(localDate.lengthOfMonth());
        bar.setProgress(localDate.getDayOfMonth());
        totalConsumptionTextView.setText(String.format(
                Locale.getDefault(),
                getResources().getString(R.string.bill_header_consumption_info_text_view_text),
                monthlyBillDao.getTotalConsumption(
                        monthlyBillDao.getByDate(LeafDateSupport.getCurrentYearMonth().toString())
                ) / 100.0
        ));
    }

    private void initRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setFocusableInTouchMode(false);

        //headerView的初始化
        View header = LayoutInflater.from(getContext()).inflate(R.layout.fragment_bill_header,recyclerView,false);
        headerDateTextView = header.findViewById(R.id.billHeaderDateTextView);
        totalConsumptionTextView = header.findViewById(R.id.billHeaderConsumptionInfoTextView);
        bar = header.findViewById(R.id.fragment_bill_progressbar);

        // 设置头部信息
        updateHeader();

        //footerView的初始化
        View footer = LayoutInflater.from(getContext()).inflate(R.layout.fragment_bill_footer,recyclerView,false);
        EditText noteEditText = footer.findViewById(R.id.note);
        noteEditText.setText(currentMonthlyBill.getComment());
        noteEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                currentMonthlyBill.setComment(s.toString());
                MonthlyBillDao.getInstance().update(currentMonthlyBill);
            }
        });

                //Adapter 需要一个footerView和headerView来初始化
        mRvAdapter = new RvAdapter(getContext(), tags,header,footer);
        recyclerView.setAdapter(mRvAdapter);
        mRvAdapter.notifyDataSetChanged();
    }

    public class RvAdapter extends RecyclerView.Adapter<RvAdapter.ViewHolder> {

        private Context mContext;

        private View footerView;
        private View headerView;

        private RvAdapter(Context context, List<Tag> list, View headerView, View footerView) {
            mContext = context;
            tags = list;
            if(headerView!=null)
                this.headerView = headerView;
            if(headerView!=null)
                this.footerView = footerView;
        }

        //ItemViewType辅助ViewHolder的使用，因为ViewHolder不负责footer和header
        @Override
        public int getItemViewType(int position) {
            if ( position == 0 && headerView !=null)
                return TYPE_HEADER;
            if( position == getItemCount() - 1 && headerView !=null)
                return TYPE_FOOTER;
            return ITEM_TYPE;
        }
        //新增itemType,用于viewHolder判断
        private static final int ITEM_TYPE = 100;
        private static final int TYPE_HEADER = 0; //说明带有header
        private static final int TYPE_FOOTER = 1; //说明带有footer

        @Override
        public int getItemCount() {
            return tags.size()+(headerView !=null?1:0)+(footerView !=null?1:0);
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            TextView tv_title;
            TagIconResultView tagIconResultView;
            ImageView br;
            RecyclerView innerRecyclerView;
            private RvvAdapter mRvvAdapter;
            private List<BillItem> list = new ArrayList<>();
            ViewHolder(View view) {
                super(view);
                tv_title = view.findViewById(R.id.bill_fragment_tag_title);
                innerRecyclerView = view.findViewById(R.id.rv_bill_item);
                tagIconResultView= view.findViewById(R.id.bill_fragment_tag_icon);
                br= view.findViewById(R.id.bill_fragment_br);
            }
        }

        @NotNull
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            if(viewType == TYPE_HEADER)  //header和footer不需要设定布局和赋值
                return new ViewHolder(headerView);
            if(viewType ==TYPE_FOOTER)
                return new ViewHolder(footerView);

            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_detaillist, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            if(getItemViewType(position) == ITEM_TYPE) {    //header和footer不需要设定布局和赋值
                if(headerView!=null)
                    position-=1;

                holder.tagIconResultView.setBgColor(tags.get(position).getColor());
                holder.tagIconResultView.setFgIcon(tags.get(position).getIcon());
                int color= ResourcesCompat.getColor(getResources(), TagResManager.getTagColorResId(tags.get(position).getColor()), null);
                holder.tv_title.setTextColor(color);
                holder.tv_title.setText(tags.get(position).getName());
                holder.br.setBackgroundColor(color);

                holder.list.clear();
                holder.list.addAll(tags.get(position).getBillItems(currentMonthlyBill));

                if (holder.mRvvAdapter == null) {
                    holder.mRvvAdapter = new RvvAdapter(mContext, holder.list, position);

                    LinearLayoutManager layoutManager=new LinearLayoutManager(mContext);
                    layoutManager.setOrientation(RecyclerView.VERTICAL);
                    holder.innerRecyclerView.setLayoutManager(layoutManager);

                    holder.innerRecyclerView.setAdapter(holder.mRvvAdapter);
                } else {
                    holder.mRvvAdapter.setPosition(position);
                    holder.mRvvAdapter.notifyDataSetChanged();
                }
            }
        }
    }

    public class RvvAdapter extends RecyclerView.Adapter<RvvAdapter.ViewHolder> {
        private Context mContext;
        private List<BillItem> mList;
        private int mPosition;

        RvvAdapter(Context context, List<BillItem> list, int position) {
            mContext = context;
            mList = list;
            mPosition = position;
        }

        void setPosition(int position) {
            this.mPosition = position;
        }

        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_bill, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            holder.position = position;
            holder.op_name.setText(mList.get(position).getName());
            holder.op_price.setText(PriceTransUtil.Int2Decimal((mList.get(position).getValue())));
            holder.lay_option.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getContext(),BillDetailsActivity.class);
                    intent.putExtra(LeafApplication.LEAF_MASSAGE,mList.get(position).getId());
                    startActivity(intent);
                }
            });
        }

        @Override
        public int getItemCount() {
            return mList == null ? 0 : mList.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            @BindView(R.id.lay_option)
            LinearLayout lay_option;
            @BindView(R.id.OP_name)
            TextView op_name;
            @BindView(R.id.OP_price)
            TextView op_price;
            int position;

            ViewHolder(View view) {
                super(view);
                ButterKnife.bind(this, view);
            }
        }
    }
    @Override
    public void onResume() {
        super.onResume();
        reread();
        updateHeader();
    }
}