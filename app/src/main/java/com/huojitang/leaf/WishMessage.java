package com.huojitang.leaf;

public class WishMessage {
    private String wishName;
    private String wishPrice;
    private String wishState;
    private String startTime;
    private String endTime;

    public WishMessage(String name,String price,String state,String starttime,String endtime){
        this.wishName = name;
        this.wishPrice = price;
        this.wishState = state;
        this.startTime = starttime;
        this.endTime = endtime;
    }

    public void setWishName(String wishName) { this.wishName = wishName; }
    public void setWishPrice(String wishPrice) { this.wishPrice = wishPrice; }
    public void setWishState(String wishState) { this.wishState = wishState; }
    public void setStartTime(String startTime) { this.startTime = startTime; }
    public void setEndTime(String endTime) { this.endTime = endTime; }
    public String getWishName(){
        return wishName;
    }
    public String getWishPrice(){
        return wishPrice;
    }
    public String getWishState(){
        return wishState;
    }
    public String getStartTime(){
        return startTime;
    }
    public String getEndTime(){
        return endTime;
    }
}
