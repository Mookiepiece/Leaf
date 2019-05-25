package com.huojitang.leaf.dao;

import com.huojitang.leaf.model.MonthlyBill;
import org.litepal.LitePal;

import java.util.List;

/**
 * MonthlyBillDao - 用于操作 MonthlyBill 的单例
 *
 * @author 刘忠燏
 * @version 1.0
 */
public class MonthlyBillDao extends BaseDao<MonthlyBill> {
    @Override
    public MonthlyBill getById(long id) {
        return LitePal.find(MonthlyBill.class, id);
    }

    @Override
    public List<MonthlyBill> listAll() {
        return LitePal.findAll(MonthlyBill.class);
    }

    private static MonthlyBillDao instance = new MonthlyBillDao();

    private MonthlyBillDao() {}

    public static MonthlyBillDao getInstance() {
        return instance;
    }
}
