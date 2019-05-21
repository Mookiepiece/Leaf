package com.huojitang.entities;

import com.huojitang.util.PriceTransUtil;

public  class ItemEntity{
    private int id;
    private String month;
    private short day;
    private String itemName;
    private int price100;
    private String tagName;

    public ItemEntity(int id, String month, short day, String itemName, int price100, String tagName) {
        this.id = id;
        this.month = month;
        this.day = day;
        this.itemName = itemName;
        this.price100 = price100;
        this.tagName = tagName;
    }

    public ItemEntity() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public short getDay() {
        return day;
    }

    public void setDay(short day) {
        this.day = day;
    }
    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
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
