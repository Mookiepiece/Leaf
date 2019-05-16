package entities;

import util.PriceTransUtil;

public class TagEntity{
    private String tagName;
    private int tagIndex;
    private int tagLimit100;
    private String color;
    private short tagMode;
    private String comment;

    public TagEntity(String tagName, int tagIndex, int limit100, String color, short mode, String comment) {
        this.tagName = tagName;
        this.tagIndex = tagIndex;
        this.tagLimit100 = limit100;
        this.color = color;
        this.tagMode = mode;
        this.comment = comment;
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

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
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
