package com.example.longhengyu.qishouduan.Order.Bean;

import java.util.List;

/**
 * Created by longhengyu on 2017/6/7.
 */

public class OrderDetailBean {


    /**
     * order : {"add_time":"2017-06-08 14:21:22","id":"1608031421227675","dispatching":"3","delivery":"2.00","wicket":"二餐厅"}
     * foot : [{"dish_id":"1","dish":""}]
     * user : {"address":"中原万达","phone":"17638135229","name":"李欢"}
     */

    private OrderBean order;
    private UserBean user;
    private List<FootBean> foot;

    public OrderBean getOrder() {
        return order;
    }

    public void setOrder(OrderBean order) {
        this.order = order;
    }

    public UserBean getUser() {
        return user;
    }

    public void setUser(UserBean user) {
        this.user = user;
    }

    public List<FootBean> getFoot() {
        return foot;
    }

    public void setFoot(List<FootBean> foot) {
        this.foot = foot;
    }

    public static class OrderBean {
        /**
         * add_time : 2017-06-08 14:21:22
         * id : 1608031421227675
         * dispatching : 3
         * delivery : 2.00
         * wicket : 二餐厅
         */

        private String add_time;
        private String id;
        private String dispatching;
        private String delivery;
        private String wicket;

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

        public String getDispatching() {
            return dispatching;
        }

        public void setDispatching(String dispatching) {
            this.dispatching = dispatching;
        }

        public String getDelivery() {
            return delivery;
        }

        public void setDelivery(String delivery) {
            this.delivery = delivery;
        }

        public String getWicket() {
            return wicket;
        }

        public void setWicket(String wicket) {
            this.wicket = wicket;
        }
    }

    public static class UserBean {
        /**
         * address : 中原万达
         * phone : 17638135229
         * name : 李欢
         */

        private String address;
        private String phone;
        private String name;

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    public static class FootBean {
        /**
         * dish_id : 1
         * dish :
         */

        private String dish_id;
        private String dish;
        private String num;

        public String getNum() {
            return num;
        }

        public void setNum(String num) {
            this.num = num;
        }

        public String getDish_id() {
            return dish_id;
        }

        public void setDish_id(String dish_id) {
            this.dish_id = dish_id;
        }

        public String getDish() {
            return dish;
        }

        public void setDish(String dish) {
            this.dish = dish;
        }
    }
}
