package com.example.longhengyu.qishouduan.Order.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.longhengyu.qishouduan.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by yuanwuji on 2017/6/5.
 */

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder> {



    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order_list, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
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

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
