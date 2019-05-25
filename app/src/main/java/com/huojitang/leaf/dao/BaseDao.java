package com.huojitang.leaf.dao;

import org.litepal.crud.LitePalSupport;

import java.util.List;

/**
 * BaseDao - 所有 Dao 的基类，定义一些所有 Dao 的通用操作
 *
 * @author 刘忠燏
 * @version 1.0
 */
public abstract class BaseDao<T extends LitePalSupport> {
    /**
     * 将对象保存到数据库中
     *
     * @param entity 要保存的实体对象
     * @return 如果保存成功，返回 true，如果发生任何异常，返回 false
     */
    public boolean add(T entity) {
        return entity.save();
    }

    /**
     * update() - 更新已保存的实体对象
     *
     * @param entity 要更新的实体对象
     * @return 如果保存成功，返回 true，发生任何异常，返回 false
     */
    public boolean update(T entity) {
        return entity.save();
    }

    /**
     * 从数据表中删除实体
     *
     * @param entity 要删除的实体
     */
    public void delete(T entity) {
        if (entity.isSaved()) {
            entity.delete();
        }
    }

    /**
     * 根据 ID 获取实体，如果没有找到则返回 null
     *
     * @param id 实体的 ID 值
     * @return 包含查询到的数据的实体对象，或者 null
     */
    public abstract T getById(long id);

    /**
     * 列出所有 ID 实体
     *
     * @return 一个包含数据表中所有实体的列表。
     */
    public abstract List<T> listAll();
}
