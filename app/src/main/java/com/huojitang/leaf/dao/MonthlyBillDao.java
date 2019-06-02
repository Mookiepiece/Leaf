package com.huojitang.leaf.dao;

import com.huojitang.leaf.model.BillItem;
import com.huojitang.leaf.model.MonthlyBill;
import com.huojitang.leaf.util.LeafDateSupport;
import org.litepal.LitePal;

import java.time.YearMonth;
import java.util.List;

/**
 * MonthlyBillDao - 用于操作 MonthlyBill 的单例
 *
 * @author 刘忠燏
 * @version 1.0
 */
public class MonthlyBillDao extends BaseDao<MonthlyBill> {
    /**
     * 根据日期获取 MonthlyBill 记录
     * 传入 isEager 为 true 可查询关联实体
     *
     * @param date 要查询的月度账单的信息，格式为（YYYY-MM）TODO：日期格式需要协商
     * @return 包含查询结果的实体，或者 null
     */
    public MonthlyBill getByDate(String date) {
        return LitePal.where("date = ?", date)
                .findFirst(MonthlyBill.class);
    }

    /**
     * 基本功能与 getByDate(String) 相同，但是传入的参数变为 YearMonth 对象
     * @param yearMonth 月度账单的年月
     * @return 包含查询结果的 MonthlyBill 对象，或者 null
     */
    public MonthlyBill getByYearMonth(YearMonth yearMonth) {
        return LitePal.where("date = ?", yearMonth.toString())
                .findFirst(MonthlyBill.class, true);
    }

    @Override
    public MonthlyBill getById(long id) {
        return LitePal.find(MonthlyBill.class, id);
    }

    @Override
    public List<MonthlyBill> listAll() {
        return LitePal.findAll(MonthlyBill.class);
    }

    @Override
    public int count() {
        return LitePal.count(MonthlyBill.class);
    }

    public YearMonth getEarliestYearMonth() {
        String dateStr = LitePal.min(MonthlyBill.class, "date", String.class);
        return LeafDateSupport.parseFromShortDate(dateStr);
    }

    public YearMonth getLatestYearMonth() {
        String dateStr = LitePal.max(MonthlyBill.class, "date", String.class);
        return LeafDateSupport.parseFromShortDate(dateStr);
    }

    /**
     * 获取特定月份的消费总额（未经转换，需要手动转换）
     * @param entity 特定月的 MonthlyBill
     * @return 返回该月的消费总额
     */
    public int getTotalConsumption(MonthlyBill entity) {
        return LitePal.where("monthlybill_id = ?", String.valueOf(entity.getId()))
                .sum(BillItem.class, "value", int.class);
    }

    private static MonthlyBillDao instance = new MonthlyBillDao();

    private MonthlyBillDao() {}

    public static MonthlyBillDao getInstance() {
        return instance;
    }
}
