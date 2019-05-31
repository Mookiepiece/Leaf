package com.huojitang.leaf.dao;

import com.huojitang.leaf.model.Wish;
import org.litepal.LitePal;

import java.util.List;

/**
 * WishDao - 用于操作 Wish 表的单例
 *
 * @author 刘忠燏
 * @version 1.0
 */
public class WishDao extends BaseDao<Wish> {
    @Override
    public Wish getById(long id) {
        return LitePal.find(Wish.class, id);
    }

    @Override
    public boolean add(Wish entity) {
        return entity.save();
    }

    @Override
    public int count() {
        return LitePal.count(Wish.class);
    }

    /**
     * MK
     * 根据开始时间获取部分心愿
     * @param startTime 输入年月 2222-02
     */
    public List<Wish> listByStartTime(String startTime){
        return LitePal.where("startTime LIKE ?",startTime+"%").order("startTime asc").find(Wish.class);
    }

    /**
     * MK
     * 根据结束时间获取部分心愿，未完成的心愿是没有结束时间的
     * @param endTime 输入年月 2222-02
     */
    public List<Wish> listByFinishedTime(String endTime){
        return LitePal.where("finishedTime LIKE ? ",endTime+"%").order("finishedTime asc").find(Wish.class);
    }

    @Override
    public List<Wish> listAll() {
        return LitePal.findAll(Wish.class);
    }

    /**
     * 返回所有心愿里的最早开始时间
     * @return 一个字符串（yyyy-MM-DD），表示心愿单中所有心愿的最早开始时间
     */
    public String getEarliestStartTime() {
        return LitePal.min(Wish.class, "startTime", String.class);
    }

    /**
     * 返回所有心愿里的最晚结束时间
     * @return 一个字符串（yyyy-MM-DD）表示心愿单中所有心愿的最晚结束时间
     */
    public String getLatestFinishedTime() {
        return LitePal.max(Wish.class, "finishedTime", String.class);
    }

    private static WishDao instance = new WishDao();

    private WishDao() {}

    public static WishDao getInstance() {
        return instance;
    }

}
