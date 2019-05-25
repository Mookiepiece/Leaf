package com.huojitang.leaf.dao;

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

    private static TagDao instance = new TagDao();

    private TagDao() {}

    public static TagDao getInstance() {
        return instance;
    }
}
