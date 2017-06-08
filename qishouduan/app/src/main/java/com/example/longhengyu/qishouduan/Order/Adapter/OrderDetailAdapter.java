package com.example.longhengyu.qishouduan.Order.Adapter;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.longhengyu.qishouduan.Order.Bean.OrderDetailBean;
import com.example.longhengyu.qishouduan.R;

import java.util.List;

/**
 * Created by longhengyu on 2017/6/7.
 */

public class OrderDetailAdapter extends RecyclerView.Adapter<OrderDetailAdapter.ViewHolder> {



    private List<OrderDetailBean> mList;
    private Context mContext;
    private View headerView;
    private View footView;


    public OrderDetailAdapter(List<OrderDetailBean> list, Context context) {
        mList = list;
        mContext = context;
    }

    @Override
    public int getItemViewType(int position) {

        if (position == 0) {
            return 0;
        }
        if (position == mList.size() - 1) {
            return 2;
        }
        return 1;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (viewType == 0) {
            headerView = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_order_detail_header, parent, false);
            ViewHolder viewHolder = new ViewHolder(headerView);
            return viewHolder;
        }

        if (viewType == 2) {
            footView = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_order_detail_foot, parent, false);
            ViewHolder viewHolder = new ViewHolder(footView);
            return viewHolder;
        }

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order_detail, parent, false);
        ViewHolder viewHolder = new ViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if(position==0){
            return;
        }
        if(position==mList.size()-1){
            holder.mConLayoutOrderDetailFootGive.setVisibility(View.VISIBLE);
            holder.mButtonOrderDetailFootStartGive.setSelected(true);
            return;
        }

        OrderDetailBean bean = mList.get(position);
        holder.mTextOrderDetailFootType.setText(bean.getFootType());
        holder.mTextOrderDetailName.setText(bean.getFoot());
        holder.mButtonOrderDetailItem.setSelected(bean.getBtnType());
        if(bean.getBtnType()){
            holder.mButtonOrderDetailItem.setText("待取餐");
        }else {
            holder.mButtonOrderDetailItem.setText("已取餐");
        }
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        /*//headerView
        @BindView(R.id.text_orderDetail_orderTime)
        TextView mTextOrderDetailOrderTime;
        @BindView(R.id.text_orderDetail_orderNum)
        TextView mTextOrderDetailOrderNum;
        @BindView(R.id.text_orderDetail_orderFeel)
        TextView mTextOrderDetailOrderFeel;
        @BindView(R.id.text_orderDetail_orderStatus)
        TextView mTextOrderDetailOrderStatus;

        //footView
        @BindView(R.id.text_orderDetail_foot_address)
        TextView mTextOrderDetailFootAddress;
        @BindView(R.id.text_orderDetail_foot_phone)
        TextView mTextOrderDetailFootPhone;
        @BindView(R.id.text_orderDetail_foot_name)
        TextView mTextOrderDetailFootName;*/
        Button mButtonOrderDetailFootStartGive;
        ConstraintLayout mConLayoutOrderDetailFootGive;

        //itemView
        Button mButtonOrderDetailItem;
        TextView mTextOrderDetailFootType;
        TextView mTextOrderDetailName;

        public ViewHolder(View itemView) {
            super(itemView);
            if(itemView==headerView){
                return;
            }
            if(itemView==footView){
                mConLayoutOrderDetailFootGive = (ConstraintLayout)itemView.findViewById(R.id.conLayout_orderDetail_foot_give);
                mButtonOrderDetailFootStartGive = (Button)itemView.findViewById(R.id.button_orderDetail_foot_startGive);
                return;
            }

            mButtonOrderDetailItem = (Button)itemView.findViewById(R.id.button_orderDetail_item);
            mTextOrderDetailFootType = (TextView)itemView.findViewById(R.id.text_orderDetail_footType);
            mTextOrderDetailName = (TextView)itemView.findViewById(R.id.text_orderDetail_name);

        }
    }
}
