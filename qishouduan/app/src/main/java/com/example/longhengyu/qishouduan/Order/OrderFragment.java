package com.example.longhengyu.qishouduan.Order;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckedTextView;

import com.example.longhengyu.qishouduan.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.yokeyword.fragmentation.SupportFragment;

/**
 * Created by yuanwuji on 2017/6/5.
 */

public class OrderFragment extends SupportFragment {

    @BindView(R.id.checkText_order_take)
    CheckedTextView checkTextOrderTake;
    @BindView(R.id.checkText_order_give)
    CheckedTextView checkTextOrderGive;
    @BindView(R.id.checkText_order_success)
    CheckedTextView checkTextOrderSuccess;
    @BindView(R.id.order_recycler)
    RecyclerView orderRecycler;
    @BindView(R.id.take_line)
    View takeLine;
    @BindView(R.id.give_line)
    View giveLine;
    @BindView(R.id.success_line)
    View successLine;

    public static OrderFragment newInstance() {

        Bundle args = new Bundle();
        OrderFragment fragment = new OrderFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_order, container, false);
        ButterKnife.bind(this, view);
        checkTextOrderTake.setChecked(true);
        takeLine.setVisibility(View.VISIBLE);
        return view;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @OnClick({R.id.checkText_order_take, R.id.checkText_order_give, R.id.checkText_order_success})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.checkText_order_take:
                checkTextOrderTake.setChecked(true);
                checkTextOrderGive.setChecked(false);
                checkTextOrderSuccess.setChecked(false);
                takeLine.setVisibility(View.VISIBLE);
                giveLine.setVisibility(View.INVISIBLE);
                successLine.setVisibility(View.INVISIBLE);
                break;
            case R.id.checkText_order_give:
                checkTextOrderTake.setChecked(false);
                checkTextOrderGive.setChecked(true);
                checkTextOrderSuccess.setChecked(false);
                takeLine.setVisibility(View.INVISIBLE);
                giveLine.setVisibility(View.VISIBLE);
                successLine.setVisibility(View.INVISIBLE);
                break;
            case R.id.checkText_order_success:
                checkTextOrderTake.setChecked(false);
                checkTextOrderGive.setChecked(false);
                checkTextOrderSuccess.setChecked(true);
                takeLine.setVisibility(View.INVISIBLE);
                giveLine.setVisibility(View.INVISIBLE);
                successLine.setVisibility(View.VISIBLE);
                break;
        }
    }
}
