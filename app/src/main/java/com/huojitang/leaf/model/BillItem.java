package com.huojitang.leaf.model;

import org.litepal.crud.LitePalSupport;

/**
 * BillItem - 记录一条记账信息
 *
 * @author 刘忠燏
 * @version 1.0
 */
public class BillItem extends LitePalSupport {
    /** （LitePal 必需）主键 */
    private int id;

    /** 消费的金额（这里的值为实际值乘以 100，模拟定点小数） */
    private int value;

    /** 消费的内容（e.g. 游玩、吃喝、购买 xxxx 等） */
    private String name;

    /** 消费的日期（值在 1~31 之间，其与 MonthlyBill 中的 date 共同构成消费日期） */
    private int day;

    /** 消费记录所对应的种类的标签 */
    private Tag tag;

    /** 消费记录所对应的月度账单 */
    private MonthlyBill monthlyBill;

    public BillItem() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Tag getTag() {
        return tag;
    }

    public void setTag(Tag tag) {
        this.tag = tag;
    }

    public MonthlyBill getMonthlyBill() {
        return monthlyBill;
    }

    public void setMonthlyBill(MonthlyBill monthlyBill) {
        this.monthlyBill = monthlyBill;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }
}
