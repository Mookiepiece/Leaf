package com.huojitang.leaf.model;

import org.litepal.LitePal;
import org.litepal.crud.LitePalSupport;

import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

/**
 * MonthlyBill - 月度账单
 *
 * @author 刘忠燏
 * @version 1.0
 */
public class MonthlyBill extends LitePalSupport {
    /**（LitePal 必需）主键 */
    private int id;

    /** 账单对应的月份 */
    private String date;

    /** 月度账单预算（这里的值为实际的值乘以 100，模拟定点小数）*/
    private int budget;

    /** 备注 */
    private String comment;

    /** 账单下的所有消费记录 */
    private List<BillItem> billItems = new ArrayList<>();

    public MonthlyBill() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(YearMonth date) {
        this.date = date.toString();
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getBudget() {
        return budget;
    }

    public void setBudget(int budget) {
        this.budget = budget;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public List<BillItem> getBillItems() {
        // 按照库作者的说法，推荐把多对一关联中的多端部分进行延迟加载，把加载的代码移动到这里
        // 但是这样做的话我需要知道这库
        return LitePal.where("monthlybill_id = ?", String.valueOf(id))
                .find(BillItem.class);
    }

    public void setBillItems(List<BillItem> billItems) {
        this.billItems = billItems;
    }
}
