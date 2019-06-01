package com.huojitang.leaf.dao;

import android.content.ContentValues;

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

    @Override
    public int count() {
        return LitePal.count(Tag.class);
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

    /** MK 指定是否查询关于系统保留标签 */
    public List<Tag> list(boolean includesDefault) {
        return LitePal.where(includesDefault?"":"reserved = 0").find(Tag.class);
    }

    /**
     * MK 查询系统保留标签,目前就一个未分类
     *
     * @return 包含查询结果的 Tag 实体，或者 null（如果查询不到）
     */
    public Tag getResrvedTag() {
        return LitePal.where("reserved = 1")
                .findFirst(Tag.class);
    }

    /** MK 查询总数 */
    public int getCount(boolean includesDefault){
        return LitePal.where(includesDefault?"":"reserved = 0").count(Tag.class);
    }

    /** MK 拖拽排序更新位置， 包括start 及 end  */
    public void updateTagIndexes(List<Tag> arr,int start,int end){
        ContentValues values;
        for(int i=start;i<=end;i++){
            values= new ContentValues();
            values.put("index", i);
            LitePal.update(Tag.class, values, arr.get(i).getId());
        }
    }

    /** MK 从数据表中删除By id  */
    public void delete(int id) {
        LitePal.delete(Tag.class,id);
    }

    private static TagDao instance = new TagDao();

    private TagDao() {}

    public static TagDao getInstance() {
        return instance;
    }
}
