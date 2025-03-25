package org.example.itheima.pojo;

public enum ReviewState {
    已通过("已通过"),
    待审核("待审核"),
    已驳回("已驳回");

    private final String description;

    ReviewState(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
