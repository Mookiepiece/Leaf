package com.huojitang.leaf;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.huojitang.leaf.dao.TagDao;
import com.huojitang.leaf.model.BillItem;
import com.huojitang.leaf.model.Tag;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivityBillFragment extends Fragment {

    //创建进度条
    private int status = 0;
    private ProgressBar bar;

    private RecyclerView recyclerView;
    private RvAdapter mRvAdapter;

    List<Tag> tags;
    TagDao tagDao= TagDao.getInstance();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bill, container, false);
        recyclerView = view.findViewById(R.id.RV_1);
        bar = view.findViewById(R.id.fragment_bill_progressbar);
        status = 20;
        //bar.setProgress(status);

//        ButterKnife.bind(getActivity());

        //数据库填充
        tags=tagDao.list(false);

        initRecyclerView();

        //设置悬浮按钮的点击事件
        FloatingActionButton floatingActionButton =  view.findViewById(R.id.floatingActionButton2);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(getContext(),"你点击了悬浮按钮",Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }

    private void initRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setFocusableInTouchMode(false);

        //headerView的初始化
        View header = LayoutInflater.from(getContext()).inflate(R.layout.fragment_bill_header,recyclerView,false);

        //footerView的初始化
        View footer = LayoutInflater.from(getContext()).inflate(R.layout.fragment_bill_footer,recyclerView,false);
        Button btn1 = footer.findViewById(R.id.note);
        Button btn2 = footer.findViewById(R.id.chart);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(),"您点击了笔记按钮，正在为您跳转",Toast.LENGTH_SHORT).show();
                //TODO
            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(),"您点击了分析报表按钮，正在为您跳转",Toast.LENGTH_SHORT).show();
                //TODO
            }
        });

        //Adapter 需要一个footerView和headerView来初始化
        mRvAdapter = new RvAdapter(getContext(), tags,header,footer);
        recyclerView.setAdapter(mRvAdapter);
        mRvAdapter.notifyDataSetChanged();
    }

    public class RvAdapter extends RecyclerView.Adapter<RvAdapter.ViewHolder> {



        private Context mContext;
        private List<Tag> mList;

        private View footerView;
        private View headerView;

        private RvAdapter(Context context, List<Tag> list, View headerView, View footerView) {
            mContext = context;
            mList = list;
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
            return mList.size()+(headerView !=null?1:0)+(footerView !=null?1:0);
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            TextView tv_title;
            RecyclerView innerRecyclerView;
            private RvvAdapter mRvAdapter;
            private List<BillItem> list = new ArrayList<>();
            ViewHolder(View view) {
                super(view);
                tv_title = view.findViewById(R.id.bill_fragment_tag_title);
                innerRecyclerView = view.findViewById(R.id.rv_item);
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
                holder.tv_title.setText(mList.get(position).getName());
                holder.list.clear();
                holder.list.addAll(mList.get(position).getBillItems());
                if (holder.mRvAdapter == null) {
                    holder.mRvAdapter = new RvvAdapter(mContext, holder.list, position);

                        GridLayoutManager layoutManager = new GridLayoutManager(mContext, 1);
                        holder.innerRecyclerView.addItemDecoration(new GridSpacingItemDecoration(1, 20, false));

                    //LinearLayoutManager layoutManager=new LinearLayoutManager(mContext);
                    //layoutManager.setOrientation(RecyclerView.VERTICAL);
                    holder.innerRecyclerView.setLayoutManager(layoutManager);

                    holder.innerRecyclerView.setAdapter(holder.mRvAdapter);
                } else {
                    holder.mRvAdapter.setPosition(position);
                    holder.mRvAdapter.notifyDataSetChanged();
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
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_detail_option, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            holder.position = position;
            holder.op_name.setText(mList.get(position).getName());
            holder.op_price.setText(String.valueOf(mList.get(position).getValue()));
            holder.lay_option.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //TODO
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
}