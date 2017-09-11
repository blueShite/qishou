package com.example.longhengyu.qishouduan.Order.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.longhengyu.qishouduan.Order.Bean.OrderListBean;
import com.example.longhengyu.qishouduan.Order.Interface.OrderListInterface;
import com.example.longhengyu.qishouduan.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by yuanwuji on 2017/6/5.
 */

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder> {

    List<OrderListBean> mList;
    Context mContext;
    OrderListInterface mInterface;


    public OrderAdapter(List<OrderListBean> list, Context context, OrderListInterface orderListInterface) {
        mList = list;
        mContext = context;
        mInterface = orderListInterface;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order_list, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        OrderListBean bean = mList.get(position);
        holder.mTextOrderListItemOrderNum.setText("订单号:" + bean.getId());
        if (bean.getOrderType() == 1) {
            holder.mTextOrderListItemAddress.setText("取餐地址:" + bean.getAddress());
            holder.mTextOrderListItemName.setVisibility(View.VISIBLE);
            holder.mTextOrderListItemName.setText("用户名称:"+bean.getName());
        } else {
            holder.mTextOrderListItemName.setVisibility(View.GONE);
            holder.mTextOrderListItemAddress.setText("送餐地址:" + bean.getAddress());
        }
        holder.mTextOrderListItemFootTime.setText("下单时间:" + bean.getAdd_time());
        holder.mTextOrderListItemOrderTime.setText("用餐时间:" + bean.getDiner_time());
        if (bean.getOrderType() == 2) {
            holder.mTextOrderListItemPrice.setVisibility(View.GONE);

        } else {
            holder.mTextOrderListItemPrice.setVisibility(View.VISIBLE);
            holder.mTextOrderListItemPrice.setText("¥" + bean.getDelivery());
        }

        holder.selfView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mInterface.onClickOrderList(position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.text_orderList_item_orderNum)
        TextView mTextOrderListItemOrderNum;
        @BindView(R.id.text_orderList_item_address)
        TextView mTextOrderListItemAddress;
        @BindView(R.id.text_orderList_item_orderTime)
        TextView mTextOrderListItemOrderTime;
        @BindView(R.id.text_orderList_item_footTime)
        TextView mTextOrderListItemFootTime;
        @BindView(R.id.text_orderList_item_price)
        TextView mTextOrderListItemPrice;
        @BindView(R.id.text_orderList_item_name)
        TextView mTextOrderListItemName;

        View selfView;

        public ViewHolder(View itemView) {
            super(itemView);
            selfView = itemView;
            ButterKnife.bind(this, itemView);
        }
    }
}
