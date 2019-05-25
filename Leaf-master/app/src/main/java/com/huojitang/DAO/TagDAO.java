package com.huojitang.DAO;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.huojitang.leaf.LeafSQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import com.huojitang.entities.TagEntity;

/*
 * MK_TIP
 * DAO:【数据库】在后端编程中数据访问对象（DAO，Data Access Object）是用于执行SQL语句的一种类，通常一个表对应一个DAO
 */
public class TagDAO{
    SQLiteDatabase db;

    public TagDAO(Context context) {
        this.db = new LeafSQLiteOpenHelper(context).getWritableDatabase();
    }

    public TagDAO(SQLiteDatabase db){
        this.db =db;
    }

    /**
     * 取得包括 未分类 的所有标签
     */
    public ArrayList<TagEntity> getAllTags(){
        return getTags("SELECT * FROM Tag ORDER BY tagIndex ");
    }

    /**
     * 取得除了 未分类 的其他标签
     */
    public ArrayList<TagEntity> getTagsWithoutDefault(){
        return getTags("SELECT * FROM Tag WHERE tagMode != -1 ORDER BY tagIndex ");
    }

    /**
     * 从编辑页传入数组，根据在数组的位置更新index,更新index的包括fromPosition和toPosition两端
     */
    public void updateTagIndexes(List<TagEntity> arr, int fromPosition, int toPosition){
        for(int i=fromPosition;i<=toPosition;i++){
            db.execSQL("UPDATE Tag SET tagIndex=? WHERE tagName=?",new String[]{String.valueOf(i),arr.get(i).getTagName()});
        }
    }

    /**
     * 不判断参数直接插入tag
     * @param tagName
     * @param tagLimit
     * @param color
     * @param comment
     * @return
     */
    public boolean insertTag(String tagName, String tagLimit, String color, String comment){
        int tagIndex=this.getCountWithoutDefault()+1;
        db.execSQL("INSERT INTO Tag VALUES (?,?,?,?,1,?)",new String[]{tagName,String.valueOf(tagIndex),String.valueOf(tagIndex),color,comment});
        return true;
    }

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

    private ArrayList<TagEntity> getTags(String sql){
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

    private TagEntity getTag(String sql){
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
        String color = c.getString(c.getColumnIndex("color"));
        int img = c.getInt(c.getColumnIndex("img"));
        short tagMode = c.getShort(c.getColumnIndex("tagMode"));
        String comment = c.getString(c.getColumnIndex("comment"));
        return new TagEntity(tagName,tagIndex,tagLimit100,color,img,tagMode,comment);
    }
}
