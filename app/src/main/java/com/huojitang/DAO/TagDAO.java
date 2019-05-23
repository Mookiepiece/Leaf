package com.huojitang.DAO;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.huojitang.leaf.LeafSQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import com.huojitang.entities.TagEntity;

public class TagDAO{

    //tag的唯一标识有tagName和tagIndex（显示顺序）
    //执行sql前均无参数校检

    /**
     * 取得包括 未分类 的所有标签 按tagIndex排序
     */
    public ArrayList<TagEntity> getAllTags(){
        return queryTags("SELECT * FROM Tag ORDER BY tagIndex ");
    }

    /**
     * 取得除了 未分类 的其他标签 按tagIndex排序
     */
    public ArrayList<TagEntity> getAllTagsWithoutDefault(){
        return queryTags("SELECT * FROM Tag WHERE tagMode != -1 ORDER BY tagIndex ");
    }

    /**
     *
     * //从编辑页传入数组，根据在数组的位置更新index
     * @param arr
     * @param fromPosition 起始点(包括)
     * @param toPosition  端点(包括)
     */
    public void updateTagIndexes(List<TagEntity> arr, int fromPosition, int toPosition){
        for(int i=fromPosition;i<=toPosition;i++){
            db.execSQL("UPDATE Tag SET tagIndex=? WHERE tagName=?",new String[]{String.valueOf(i),arr.get(i).getTagName()});
        }
    }

    /**
     * 添加一个标签
     * @param tagName 名称
     * @param tagLimit 额度
     * @param color 颜色（int值）
     * @param img 图片（对应系统给出的图片的id）
     * @param comment 评论
     * @return
     */
    public void insertTag(String tagName, String tagLimit, int color,int img, String comment){
        int tagIndex=this.getCountWithoutDefault()+1;
        db.execSQL("INSERT INTO Tag VALUES (?,?,?,?,?,1,?)",new String[]{
                tagName,String.valueOf(tagIndex),String.valueOf(tagLimit),String.valueOf(color),String.valueOf(img),comment
        });
    }

    /**
     * 删除指定tag
     * @param tagIndex
     */
    public void deleteTag(int tagIndex){
        db.execSQL("DELETE FROM Tag WHERE tagIndex=? ",new String[]{String.valueOf(tagIndex)});
    }
    public void deleteTag(String tagName){
        db.execSQL("DELETE FROM Tag WHERE tagName=? ",new String[]{tagName});
    }

    /**
     * 更新指定tag
     */
    public void updateTag(String oldTagName, TagEntity entity){
        db.execSQL("UPDATE Tag SET tagName=?, tagIndex=?, tagLimit=?, color=?, img=?, tagMode=?, comment=? WHERE tagName=?",
                new String[]{
                        entity.getTagName(),
                        String.valueOf(entity.getTagIndex()),
                        String.valueOf(entity.getTagLimit100()),
                        String.valueOf(entity.getColor()),
                        String.valueOf(entity.getImg()),
                        String.valueOf(entity.getTagMode()),
                        String.valueOf(entity.getComment()),
                        oldTagName
                });
    }


    /**
     * 获取指定tag
     */
    public TagEntity getTag(int tagIndex){
        return queryTag("SELECT FORM Tag WHERE tagIndex = "+ tagIndex);
    }
    public TagEntity getTag(String tagName){
        return queryTag("SELECT FORM Tag WHERE tagName = "+ tagName);
    }

    //以下为私有方法

    private ArrayList<TagEntity> queryTags(String sql){
        if(db==null) return null;
        ArrayList<TagEntity> list=new ArrayList<>();
        Cursor c = db.rawQuery(sql,null);
        if(c.moveToFirst()){
            do{
                list.add(TagEntityFromCursor(c));
            }while(c.moveToNext());
        }
        return list;
    }

    private TagEntity queryTag(String sql){
        if(db==null) return null;
        Cursor c = db.rawQuery(sql,null);
        if(!c.moveToFirst())
            return null;
        return TagEntityFromCursor(c);
    }

    public int getCountWithoutDefault(){
        Cursor c = db.rawQuery("SELECT Count(tagName) FROM Tag WHERE tagMode != -1 ORDER BY tagIndex",null);
        c.moveToFirst();
        return c.getInt(0);
    }

    private TagEntity TagEntityFromCursor(Cursor c){
        String tagName = c.getString(c.getColumnIndex("tagName"));
        int tagIndex = c.getInt(c.getColumnIndex("tagIndex"));
        int tagLimit100 = c.getInt(c.getColumnIndex("tagLimit"));
        int color = c.getInt(c.getColumnIndex("color"));
        int img = c.getInt(c.getColumnIndex("img"));
        short tagMode = c.getShort(c.getColumnIndex("tagMode"));
        String comment = c.getString(c.getColumnIndex("comment"));
        return new TagEntity(tagName,tagIndex,tagLimit100,color,img,tagMode,comment);
    }

    private SQLiteDatabase db;
    private static TagDAO instance;
    public static TagDAO getInstance(){
        if(instance==null)
            synchronized(TagDAO.class) {
                if (instance == null) {
                    instance = new TagDAO();
                }
            }
        return instance;
    }

    public TagDAO() {
        this.db = LeafSQLiteOpenHelper.getInstance().getWritableDatabase();
    }
}


/*

    private void execTransaction(final String[] sql){
        if(db==null) return;
        db.beginTransaction();
        try{
            for(String s:sql)
                db.execSQL(s,null);
            db.setTransactionSuccessful();
        }
        finally{
            db.endTransaction();
        }
    }

*/