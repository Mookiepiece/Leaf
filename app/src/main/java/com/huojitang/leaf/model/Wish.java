package com.huojitang.leaf.model;

import org.litepal.crud.LitePalSupport;

/**
 * Wish - 心愿单实体类
 *
 * @author 刘忠燏
 * @version 1.0
 */
public class Wish extends LitePalSupport {
    /** id（LitePal 所要求的必须具有的属性）*/
    private int id;

    /** index - 用于心愿单主页的排序功能（还需要做这个功能吗？）*/
    private int index;

    /** 心愿的名称 */
    private String name;

    /** 心愿的状态，其值为 WISH_TODO、WISH_CANCELLED、WISH_ACHIEVED 三个值之一 */
    private int state = WISH_TODO;

    /** 心愿的价值（这里的值为实际值乘以 100，以模拟定点小数）*/
    private int value;

    /** 心愿创建的时间 */
    private String startTime;

    /** 心愿结束的时间（放弃/取消或者实现） */
    private String finishedTime;

    /** 心愿的备注 */
    private String comment;

    /** 心愿待完成状态 */
    public static final int WISH_TODO = 0;

    /** 心愿取消状态 */
    public static final int WISH_CANCELLED = 1;

    /** 心愿已完成状态 */
    public static final int WISH_ACHIEVED = 2;

    /** 无参构造方法 */
    public Wish() {
    }

    /**
     * 简单的初始化方法，初始化 name, value
     */
    public Wish(String name, int value) {
        this.name = name;
        this.value = value;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getFinishedTime() {
        return finishedTime;
    }

    public void setFinishedTime(String finishedTime) {
        this.finishedTime = finishedTime;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
