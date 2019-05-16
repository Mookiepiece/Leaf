package com.huojitang.entities;


public class WishEntity{
    private int wishId;
    private int wishIndex;
    private String wishName;
    private int price100;
    private String startTime;
    private String endTime;
    private String comment;

    public WishEntity() {
    }

    public WishEntity(int wishId, int wishIndex, String wishName, int price100, String startTime, String endTime, String comment) {
        this.wishId = wishId;
        this.wishIndex = wishIndex;
        this.wishName = wishName;
        this.price100 = price100;
        this.startTime = startTime;
        this.endTime = endTime;
        this.comment = comment;
    }

    public int getWishId() {
        return wishId;
    }

    public void setWishId(int wishId) {
        this.wishId = wishId;
    }

    public int getWishIndex() {
        return wishIndex;
    }

    public void setWishIndex(int wishIndex) {
        this.wishIndex = wishIndex;
    }

    public String getWishName() {
        return wishName;
    }

    public void setWishName(String wishName) {
        this.wishName = wishName;
    }

    public int getPrice100() {
        return price100;
    }

    public void setPrice100(int price100) {
        this.price100 = price100;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

}
