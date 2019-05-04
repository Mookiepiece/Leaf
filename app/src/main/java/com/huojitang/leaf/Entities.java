package com.huojitang.leaf;


import android.database.Cursor;

class PriceTransUtil{
    static String Int2Decimal(int price100){
        return price100/100+"."+price100%100;
    };
}

class TagEntity{
    private String tagName;
    private int tagIndex;
    private int tagLimit100;
    private String color;
    private short tagMode;
    private String comment;

    public TagEntity(String tagName, int tagIndex, int limit100, String color, short mode, String comment) {
        this.tagName = tagName;
        this.tagIndex = tagIndex;
        this.tagLimit100 = limit100;
        this.color = color;
        this.tagMode = mode;
        this.comment = comment;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public int getTagIndex() {
        return tagIndex;
    }

    public void setTagIndex(int tagIndex) {
        this.tagIndex = tagIndex;
    }

    public int getTagLimit100() {
        return tagLimit100;
    }

    public String  getTagLimitDecimal() {
        return PriceTransUtil.Int2Decimal(tagLimit100);
    }

    public void setTagLimit100(int tagLimit100) {
        this.tagLimit100 = tagLimit100;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public short getTagMode() {
        return tagMode;
    }

    public void setTagMode(short mode) {
        this.tagMode = mode;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}

class ListEntity{
    private String month;
    private int balance100;
    private String note;

    public ListEntity() {
    }

    public ListEntity(String month, int balance100, String note) {
        this.month = month;
        this.balance100 = balance100;
        this.note = note;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public int getBalance100() {
        return balance100;
    }
    public String  getBalanceDecimal() {
        return PriceTransUtil.Int2Decimal(balance100);
    }

    public void setBalance100(int balance100) {
        this.balance100 = balance100;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}

class ItemEntity{
    private String month;
    private int itemIndex;
    private String itemName;
    private int price100;
    private String tagName;

    public ItemEntity() {
    }

    public ItemEntity(String month, int itemIndex, String itemName, int price100, String tagName) {
        this.month = month;
        this.itemIndex = itemIndex;
        this.itemName = itemName;
        this.price100 = price100;
        this.tagName = tagName;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public int getItemIndex() {
        return itemIndex;
    }

    public void setItemIndex(int itemIndex) {
        this.itemIndex = itemIndex;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public int getPrice100() {
        return price100;
    }
    public String  getPriceDecimal() {
        return PriceTransUtil.Int2Decimal(price100);
    }

    public void setPrice100(int price100) {
        this.price100 = price100;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }
}

class WishEntity{
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
