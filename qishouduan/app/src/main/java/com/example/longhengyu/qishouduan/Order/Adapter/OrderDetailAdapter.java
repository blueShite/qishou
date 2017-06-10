package com.example.longhengyu.qishouduan.Order.Adapter;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.longhengyu.qishouduan.Order.Bean.OrderDetailBean;
import com.example.longhengyu.qishouduan.Order.Interface.OrderDetailInterface;
import com.example.longhengyu.qishouduan.R;

import java.util.List;

/**
 * Created by longhengyu on 2017/6/7.
 */

public class OrderDetailAdapter extends RecyclerView.Adapter<OrderDetailAdapter.ViewHolder> {



    private List<OrderDetailBean.FootBean> mList;
    private OrderDetailBean mDetailBean;
    private Context mContext;
    private OrderDetailInterface mInterface;
    private View headerView;
    private View footView;


    public OrderDetailAdapter(List<OrderDetailBean.FootBean> list, OrderDetailBean detailBean, Context context, OrderDetailInterface detailInterface) {
        mList = list;
        mInterface = detailInterface;
        mDetailBean = detailBean;
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
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        if(position==0){
            if(mDetailBean.getOrder().getDispatching().equals("1")){
                holder.mTextOrderDetailOrderStatus.setText("订单领取中");
            }
            if(mDetailBean.getOrder().getDispatching().equals("2")){
                holder.mTextOrderDetailOrderStatus.setText("订单派送中");
            }
            if(mDetailBean.getOrder().getDispatching().equals("3")){
                holder.mTextOrderDetailOrderStatus.setText("订单已完成");
            }
            holder.mTextOrderDetailOrderNum.setText("订单号:"+mDetailBean.getOrder().getId());
            holder.mTextOrderDetailOrderTime.setText("下单时间:"+mDetailBean.getOrder().getAdd_time());
            holder.mTextOrderDetailOrderFeel.setText("¥"+mDetailBean.getOrder().getDelivery());
            return;
        }
        if(position==mList.size()-1){
            holder.mTextOrderDetailFootAddress.setText(mDetailBean.getUser().getAddress());
            holder.mTextOrderDetailFootPhone.setText(mDetailBean.getUser().getPhone());
            holder.mTextOrderDetailFootName.setText(mDetailBean.getUser().getName());
            if(mDetailBean.getOrder().getDispatching().equals("1")){
                int i=0;
                for (int j=0;j<mDetailBean.getFoot().size();j++){
                    OrderDetailBean.FootBean footBean = mDetailBean.getFoot().get(j);
                    if(footBean.getDish_id().equals("2")){
                        i++;
                    }
                }
                holder.mConLayoutOrderDetailFootGive.setVisibility(View.VISIBLE);
                holder.mButtonOrderDetailFootStartGive.setText("开始配送");
                if(i==mDetailBean.getFoot().size()){
                    holder.mButtonOrderDetailFootStartGive.setSelected(true);
                }else {
                    holder.mButtonOrderDetailFootStartGive.setSelected(false);
                }

            }else if(mDetailBean.getOrder().getDispatching().equals("2")){
                holder.mButtonOrderDetailFootStartGive.setText("确认送达");
                holder.mConLayoutOrderDetailFootGive.setVisibility(View.VISIBLE);
                holder.mButtonOrderDetailFootStartGive.setSelected(true);
            }else {
                holder.mConLayoutOrderDetailFootGive.setVisibility(View.GONE);
                holder.mButtonOrderDetailFootStartGive.setSelected(false);
            }
            if(mDetailBean.getOrder().getDispatching().equals("2")){
                holder.mImageViewOrderFootPhone.setVisibility(View.VISIBLE);
            }else {
                holder.mImageViewOrderFootPhone.setVisibility(View.GONE);
            }
            holder.mButtonOrderDetailFootStartGive.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(holder.mButtonOrderDetailFootStartGive.isSelected()){
                        mInterface.onClickDistributionBtn(mDetailBean.getOrder().getDispatching());
                    }
                }
            });
            holder.mImageViewOrderFootPhone.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mInterface.onClickPhoneBtn(mDetailBean.getUser().getPhone());
                }
            });
            return;
        }

        OrderDetailBean.FootBean bean = mList.get(position);
        holder.mTextOrderDetailFootType.setText(mDetailBean.getOrder().getWicket());
        holder.mTextOrderDetailName.setText(bean.getDish());
        if(bean.getDish_id().equals("2")){
            holder.mButtonOrderDetailItem.setSelected(false);
            holder.mButtonOrderDetailItem.setText("已取餐");
        }else {
            holder.mButtonOrderDetailItem.setSelected(true);
            holder.mButtonOrderDetailItem.setText("待取餐");
        }
        holder.mButtonOrderDetailItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(holder.mButtonOrderDetailItem.isSelected()){
                    mInterface.onClickFootBtn(position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        //headerView
        TextView mTextOrderDetailOrderTime;
        TextView mTextOrderDetailOrderNum;
        TextView mTextOrderDetailOrderFeel;
        TextView mTextOrderDetailOrderStatus;

        //footView
        TextView mTextOrderDetailFootAddress;
        TextView mTextOrderDetailFootPhone;
        TextView mTextOrderDetailFootName;
        Button mButtonOrderDetailFootStartGive;
        ConstraintLayout mConLayoutOrderDetailFootGive;
        ImageView mImageViewOrderFootPhone;

        //itemView
        Button mButtonOrderDetailItem;
        TextView mTextOrderDetailFootType;
        TextView mTextOrderDetailName;

        public ViewHolder(View itemView) {
            super(itemView);
            if(itemView==headerView){
                mTextOrderDetailOrderStatus = (TextView)itemView.findViewById(R.id.text_orderDetail_orderStatus);
                mTextOrderDetailOrderFeel = (TextView)itemView.findViewById(R.id.text_orderDetail_orderFeel);
                mTextOrderDetailOrderNum = (TextView)itemView.findViewById(R.id.text_orderDetail_orderNum);
                mTextOrderDetailOrderTime = (TextView)itemView.findViewById(R.id.text_orderDetail_orderTime);
                return;
            }
            if(itemView==footView){
                mTextOrderDetailFootAddress = (TextView)itemView.findViewById(R.id.text_orderDetail_foot_address);
                mTextOrderDetailFootPhone = (TextView)itemView.findViewById(R.id.text_orderDetail_foot_phone);
                mTextOrderDetailFootName = (TextView)itemView.findViewById(R.id.text_orderDetail_foot_name) ;
                mConLayoutOrderDetailFootGive = (ConstraintLayout)itemView.findViewById(R.id.conLayout_orderDetail_foot_give);
                mButtonOrderDetailFootStartGive = (Button)itemView.findViewById(R.id.button_orderDetail_foot_startGive);
                mImageViewOrderFootPhone = (ImageView)itemView.findViewById(R.id.image_orderFoot_phone);
                return;
            }

            mButtonOrderDetailItem = (Button)itemView.findViewById(R.id.button_orderDetail_item);
            mTextOrderDetailFootType = (TextView)itemView.findViewById(R.id.text_orderDetail_footType);
            mTextOrderDetailName = (TextView)itemView.findViewById(R.id.text_orderDetail_name);
        }
    }
}
