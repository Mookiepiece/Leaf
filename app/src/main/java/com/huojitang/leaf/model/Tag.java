package com.huojitang.leaf.model;

import org.litepal.LitePal;
import org.litepal.crud.LitePalSupport;

import java.util.ArrayList;
import java.util.List;

/**
 * Tag - 标签实体类
 *
 * @author 刘忠燏
 * @version 1.0
 */
public class Tag extends LitePalSupport {
    /**（LitePal 必需）主键 */
    private int id;

    /** 标签的名称 */
    private String name;

    /** 预算额（这里的值为实际值乘以 100，模拟定点小数） */
    private int budget;

    /** 标签的颜色值，存储 32 位 ARGB 值 */
    private int color;

    /** 该标签是否为系统预留标签，默认为 false */
    private boolean reserved = false;

    /** index，用于界面对标签进行排序（不太懂原理所在） */
    private int index;

    /** 标签对应的图标（我不清楚这里怎么存储的，求解释） */
    private int icon;

    /** 备注 */
    private String comment;

    /** 该标签下对应的记录，与 BillItem 之间形成 N-1 关联 */
    private List<BillItem> billItems = new ArrayList<>();

    /** 无参构造方法 */
    public Tag() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getBudget() {
        return budget;
    }

    public void setBudget(int budget) {
        this.budget = budget;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public boolean isReserved() {
        return reserved;
    }

    public void setReserved(boolean reserved) {
        this.reserved = reserved;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public List<BillItem> getBillItems() {
        return LitePal.where("tag_id = ?", String.valueOf(id))
                .find(BillItem.class);
    }

    public List<BillItem> getBillItems(MonthlyBill monthlyBill) {
        return LitePal.where("tag_id = ? and monthlybill_id = ?", String.valueOf(id), String.valueOf(monthlyBill.getId()))
                .find(BillItem.class);
    }

    public void setBillItems(List<BillItem> billItems) {
        this.billItems = billItems;
    }
}
