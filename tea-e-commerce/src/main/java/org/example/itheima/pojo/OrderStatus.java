package org.example.itheima.pojo;

public enum OrderStatus {
    已付款("已付款"),
    已发货("已发货"),
    已退货("已退货");

    private final String description;

    OrderStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
