package entities;

import util.PriceTransUtil;

public  class ItemEntity{
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
