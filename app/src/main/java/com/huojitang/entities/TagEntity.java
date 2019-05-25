package com.huojitang.entities;

import com.huojitang.util.PriceTransUtil;

public class TagEntity{
    private String tagName;
    private int tagIndex;
    private int tagLimit100;
    private int color;
    private int img;
    private short tagMode;
    private String comment;


    public TagEntity(String tagName, int tagIndex, int tagLimit100, int color, int img, short tagMode, String comment) {
        this.tagName = tagName;
        this.tagIndex = tagIndex;
        this.tagLimit100 = tagLimit100;
        this.color = color;
        this.img = img;
        this.tagMode = tagMode;
        this.comment = comment;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }


    public TagEntity() {

    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public int getTagIndex() {
        return tagIndex;
    }

    public void setTagIndex(int tagIndex) {
        this.tagIndex = tagIndex;
    }

    public int getTagLimit100() {
        return tagLimit100;
    }

    public String  getTagLimitDecimal() {
        return PriceTransUtil.Int2Decimal(tagLimit100);
    }

    public void setTagLimit100(int tagLimit100) {
        this.tagLimit100 = tagLimit100;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public short getTagMode() {
        return tagMode;
    }

    public void setTagMode(short mode) {
        this.tagMode = mode;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
