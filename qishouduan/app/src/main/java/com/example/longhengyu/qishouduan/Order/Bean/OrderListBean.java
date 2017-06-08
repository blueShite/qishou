package com.example.longhengyu.qishouduan.Order.Bean;

/**
 * Created by longhengyu on 2017/6/6.
 */

public class OrderListBean {


    /**
     * address : 中原万达
     * add_time : 2017-06-08 13:15:12
     * id : 1608031315124954
     * diner_time : 12:00
     */

    private String address;
    private String add_time;
    private String id;
    private String diner_time;
    private int orderType;
    private String delivery;

    public String getDelivery() {
        return delivery;
    }

    public void setDelivery(String delivery) {
        this.delivery = delivery;
    }

    public int getOrderType() {
        return orderType;
    }

    public void setOrderType(int orderType) {
        this.orderType = orderType;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAdd_time() {
        return add_time;
    }

    public void setAdd_time(String add_time) {
        this.add_time = add_time;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDiner_time() {
        return diner_time;
    }

    public void setDiner_time(String diner_time) {
        this.diner_time = diner_time;
    }
}
