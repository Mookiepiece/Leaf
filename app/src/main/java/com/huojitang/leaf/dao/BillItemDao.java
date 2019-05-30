package com.huojitang.leaf.dao;

import com.huojitang.leaf.model.BillItem;
import org.litepal.LitePal;

import java.util.List;

/**
 * BillItemDao - 操作 BillItem 的单例
 *
 * @author 刘忠燏
 * @version 1.0
 */
public class BillItemDao extends BaseDao<BillItem> {
    @Override
    public BillItem getById(long id) {
        return LitePal.find(BillItem.class, id);
    }

    @Override
    public List<BillItem> listAll() {
        return LitePal.findAll(BillItem.class);
    }

    @Override
    public int count() {
        return LitePal.count(BillItem.class);
    }

    private static BillItemDao instance = new BillItemDao();

    private BillItemDao() {}

    public static BillItemDao getInstance() {
        return instance;
    }
}
