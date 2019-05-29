package com.huojitang.leaf;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
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

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ListFragment extends Fragment {

    private int[] data = new int[100];
    private int hasData = 0;

    //创建进度条
    int status = 0;
    private ProgressBar bar;

    private Bean mBean;
    RecyclerView c_RecyclerView;
    private RvAdapter mRvAdapter;


    private class MyHandler extends Handler {
        private WeakReference<ListFragment> activity;
        MyHandler(WeakReference<ListFragment> activity){
            this.activity = activity;
        }
        @Override
        public void handleMessage(Message msg){
            if(msg.what == 0x111){
                activity.get().bar.setProgress(activity.get().status);
            }
        }
    }
    final MyHandler mHandler = new MyHandler(new WeakReference<ListFragment>(this));

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedTnstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);

        //list();
        c_RecyclerView = (RecyclerView) view.findViewById(R.id.RV_1);

        bar = view.findViewById(R.id.bar);
        status = 20;
        new Thread() {
            @Override
            public void run() {
                status = 20;
                mHandler.sendEmptyMessage(0x111);
            }
        }.start();

        ButterKnife.bind(getActivity());

        initdata();

        //设置悬浮按钮的点击事件
        FloatingActionButton floatingActionButton = (FloatingActionButton) view.findViewById(R.id.floatingActionButton2);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(),"你点击了悬浮按钮",Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

    private int doWork() {
        data[hasData++] = (int) (Math.random()*100);
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return hasData;
    }

    private void initdata() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        c_RecyclerView.setLayoutManager(layoutManager);
        c_RecyclerView.setFocusableInTouchMode(false);

        mBean = new Bean();
        List<Bean.DatasBean> datas = new ArrayList<>();

        //模拟一些数据
        for (int i = 0; i < 4; i++) {
            Bean.DatasBean datasBean = new Bean.DatasBean();
            List<Bean.DatasBean.Option> option = new ArrayList<>();

            Bean.DatasBean.Option optionBean = new Bean.DatasBean.Option();
            optionBean.setmDate("2018-03-01");
            optionBean.setmName("早餐");
            optionBean.setmPrice("4.00元");
            option.add(optionBean);

            Bean.DatasBean.Option optionBean1 = new Bean.DatasBean.Option();
            optionBean1.setmDate("2018-03-01");
            optionBean1.setmName("水费");
            optionBean1.setmPrice("20.00元");
            option.add(optionBean1);

            Bean.DatasBean.Option optionBean2 = new Bean.DatasBean.Option();
            optionBean2.setmDate("2018-03-02");
            optionBean2.setmName("电费");
            optionBean2.setmPrice("300.00元");
            option.add(optionBean2);

            Bean.DatasBean.Option optionBean3 = new Bean.DatasBean.Option();
            optionBean3.setmDate("2018-03-03");
            optionBean3.setmName("话费");
            optionBean3.setmPrice("100.00元");
            option.add(optionBean3);

            datasBean.setOptions(option);
            datasBean.setTitle("标签"+i);
            datas.add(datasBean);
        }

        mBean.setDatas(datas);

        mRvAdapter = new RvAdapter(getContext(), mBean.getDatas());
        c_RecyclerView.setAdapter(mRvAdapter);
        mRvAdapter.notifyDataSetChanged();

        setFooterView(c_RecyclerView);
//        setHeaderView(c_RecyclerView);
    }

//    private void setHeaderView(RecyclerView view) {
//
//        View header = LayoutInflater.from(getContext()).inflate(R.layout.item_header,view,false);
//        mRvAdapter.setHeaderView(header);
//    }

    private void setFooterView(RecyclerView view){
        View footer = LayoutInflater.from(getContext()).inflate(R.layout.item_footer,view,false);
        mRvAdapter.setFooterView(footer);
    }

    public class RvAdapter extends RecyclerView.Adapter<RvAdapter.ViewHolder> {
        //新增itemType
        public static final int ITEM_TYPE = 100;
//        private static final int TYPE_HEADER = 0; //说明带有header
        private static final int TYPE_FOOTER = 1; //说明带有footer
//    private static final int TYPE_NORMAL = 2; //说明不带有header和footer

        private Context mContext;
        private List<Bean.DatasBean> mList;

        private View mFooterView;
//        private View mHeaderView;

        public RvAdapter(Context context, List<Bean.DatasBean> list) {
            mContext = context;
            mList = list;
        }

        //重写改方法，设置ItemViewType
        @Override
        public int getItemViewType(int position) {
//        if(mHeaderView == null && mFooterView ==null){
//            return TYPE_NORMAL;
//        }
//        if (position == 0){
//            return TYPE_HEADER;
//        }
            if(position == getItemCount()-1){
                return TYPE_FOOTER;
            }
            //返回值与使用时设置的值需保持一致
            return ITEM_TYPE;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        if(mHeaderView != null && viewType == TYPE_HEADER){
//            return new ViewHolder(mHeaderView);
//        }
            if(mFooterView != null && viewType ==TYPE_FOOTER){
                return new ViewHolder(mFooterView);
            }

            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_detaillist, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
//        holder.tv_title.setText(mList.get(position).getTitle());
//
//        /*
//         1.把内部RecyclerView的adapter和集合数据通过holder缓存
//         2.使内部RecyclerView的adapter提供设置position的方法
//         */
//        holder.list.clear();
//        holder.list.addAll(mList.get(position).getOptions());
//        if (holder.mRvAdapter == null) {
//            holder.mRvAdapter = new RvvAdapter(mContext, holder.list, position);
//            GridLayoutManager layoutManage = new GridLayoutManager(mContext, 1);
//            holder.rvItemItem.setLayoutManager(layoutManage);
//            holder.rvItemItem.addItemDecoration(new GridSpacingItemDecoration(1, 20, false));
//            holder.rvItemItem.setAdapter(holder.mRvAdapter);
//        } else {
//            holder.mRvAdapter.setPosition(position);
//            holder.mRvAdapter.notifyDataSetChanged();
//        }

            if(getItemViewType(position) == ITEM_TYPE){
                if(holder instanceof ViewHolder){
                    ((ViewHolder) holder).tv_title.setText(mList.get(position).getTitle());
                    holder.list.clear();
                    holder.list.addAll(mList.get(position).getOptions());
                    if (holder.mRvAdapter == null) {
                        holder.mRvAdapter = new RvvAdapter(mContext, holder.list, position);
                        GridLayoutManager layoutManage = new GridLayoutManager(mContext, 1);
                        holder.rvItemItem.setLayoutManager(layoutManage);
                        holder.rvItemItem.addItemDecoration(new GridSpacingItemDecoration(1, 20, false));
                        holder.rvItemItem.setAdapter(holder.mRvAdapter);
                    } else {
                        holder.mRvAdapter.setPosition(position);
                        holder.mRvAdapter.notifyDataSetChanged();
                    }
                    return;
                }
                return;
            } else {
                return;
            }
        }

        @Override
        public int getItemCount() {
//            if(mFooterView == null&&mHeaderView == null){
//                return mList.size();
//            } else if(mFooterView != null && mHeaderView == null){
//                return mList.size()+1;
//            } else if(mFooterView == null && mHeaderView != null){
//                return mList.size()+1;
//            }
            if(mFooterView == null){
                return mList.size();
            }
            return mList.size()+1;
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            TextView tv_title;
            RecyclerView rvItemItem;

            private RvvAdapter mRvAdapter;
            private List<Bean.DatasBean.Option> list = new ArrayList<>();

            ViewHolder(View view) {
                super(view);
//            if(rvItemItem == mHeaderView){
//                return;
//            }
                if(rvItemItem == mFooterView)
                {
                    return;
                }
                tv_title = (TextView) view.findViewById(R.id.tv_title);
                rvItemItem = (RecyclerView) view.findViewById(R.id.rv_item);
            }
        }

//    public View getHeaderView(){
//        return mHeaderView;
//    }
//
//    public void setHeaderView(View headerView){
//        mHeaderView = headerView;
//        notifyItemInserted(0);
//    }

        public View getFooterView(){
            return mFooterView;
        }

        public void setFooterView(View footerView){
            mFooterView = footerView;
            Button btn1 = footerView.findViewById(R.id.note);
            Button btn2 = footerView.findViewById(R.id.chart);

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
                    Intent intent = new Intent(getActivity(), ChartActivity.class);
                    startActivity(intent);
                }
            });
            notifyItemInserted(getItemCount()-1);
        }
    }


    public class RvvAdapter extends RecyclerView.Adapter<RvvAdapter.ViewHolder> {
        private Context mContext;
        private List<Bean.DatasBean.Option> mList;

        private int mPosition;

        public RvvAdapter(Context context, List<Bean.DatasBean.Option> list, int position) {
            mContext = context;
            mList = list;
            mPosition = position;
        }

        /**
         * 新增方法
         *
         * @param position 位置
         */
        public void setPosition(int position) {
            this.mPosition = position;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_detail_option, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            holder.lay_option.setTag(position);

            holder.op_date.setText(mList.get(position).getmDate());
            holder.op_name.setText(mList.get(position).getmName());
            holder.op_price.setText(mList.get(position).getmPrice());

            if (mList.get(position).isSelect()) {
                holder.lay_option.setBackgroundResource(R.drawable.background_grid_unselect);
            } else {
                holder.lay_option.setBackgroundResource(R.drawable.background_grid_select);
            }

        }

        @Override
        public int getItemCount() {
            return mList == null ? 0 : mList.size();
        }


        class ViewHolder extends RecyclerView.ViewHolder {
            @BindView(R.id.lay_option)
            LinearLayout lay_option;
            @BindView(R.id.OP_date)
            TextView op_date;
            @BindView(R.id.OP_name)
            TextView op_name;
            @BindView(R.id.OP_price)
            TextView op_price;

            ViewHolder(View view) {
                super(view);
                ButterKnife.bind(this, view);

                //这里设置一个tag,作为被嵌套的recycleview item点击事件的 position
                lay_option.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mList.get((int) v.getTag()).setSelect(true);
                        int position =mPosition;
                        int tag = (int) v.getTag();
                        final List<Bean.DatasBean> datasBeans = mBean.getDatas();
                        for (int i = 0; i < datasBeans.size(); i++) {
                            if (i == position) {
                                List<Bean.DatasBean.Option> option = datasBeans.get(i).getOptions();
                                for (int j = 0; j < option.size(); j++) {
                                    if (j == tag) {
                                        option.get(j).setSelect(true);
                                    } else {
                                        option.get(j).setSelect(false);
                                    }
                                }
                                Toast.makeText(getContext(),
                                        datasBeans.get(position).getTitle() + "-" + option.get(tag).getmName(),
                                        Toast.LENGTH_SHORT).show();

                            } else {
                                //这里让之前选中的效果还原成未选中
                                List<Bean.DatasBean.Option> option = datasBeans.get(i).getOptions();
                                for (int j = 0; j < option.size(); j++) {
                                    option.get(j).setSelect(false);
                                }
                            }
                        }
                        mRvAdapter.notifyDataSetChanged();
                    }
                });


            }
        }
    }
}