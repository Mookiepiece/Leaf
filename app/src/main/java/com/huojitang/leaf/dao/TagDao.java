package com.huojitang.leaf.dao;

import com.huojitang.leaf.model.BillItem;
import com.huojitang.leaf.model.MonthlyBill;
import com.huojitang.leaf.model.Tag;
import org.litepal.LitePal;

import java.util.List;

/**
 * TagDao - 操作 Tag 的单例
 *
 * @author 刘忠燏
 * @version 1.0
 */
public class TagDao extends BaseDao<Tag> {

    @Override
    public Tag getById(long id) {
        return LitePal.find(Tag.class, id);
    }

    @Override
    public List<Tag> listAll() {
        return LitePal.findAll(Tag.class);
    }

    /**
     * 获得给定标签在给定月份下的消费总额
     * @param tag   需要统计的标签
     * @param monthlyBill   需要统计的月份
     * @return 一个整数，表示该标签在该月下的消费总额（实际值为模拟值除以 100）
     */
    public int getConsumption(Tag tag, MonthlyBill monthlyBill) {
        return LitePal.where("tag_id = ? and monthlybill_id = ?", String.valueOf(tag.getId()), String.valueOf(monthlyBill.getId()))
                .sum(BillItem.class, "value", int.class);
    }

    private static TagDao instance = new TagDao();

    private TagDao() {}

    public static TagDao getInstance() {
        return instance;
    }
}
