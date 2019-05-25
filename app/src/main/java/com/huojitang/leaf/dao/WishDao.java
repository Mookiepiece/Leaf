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
    public List<Wish> listAll() {
        return LitePal.findAll(Wish.class);
    }

    private static WishDao instance = new WishDao();

    private WishDao() {}

    public static WishDao getInstance() {
        return instance;
    }
}
