package com.huojitang.DAO;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.huojitang.leaf.LeafSQLiteOpenHelper;

import java.util.ArrayList;

import com.huojitang.entities.ItemEntity;
import com.huojitang.entities.ListEntity;


/**
 * 关于各月份账单（List） 及旗下的账目（Item）
 */
public class ListDAO{

    /**
     * 获取所有的月
     */
    public ArrayList<ListEntity> getList(){
        return queryLists("SELECT * FROM List");
    }

    /**
     * 获取月份是否存在
     */
    public boolean hasList(String month){
        return queryList("SELECT * FROM List WHERE month ="+month)==null;
    }

    /**
     * 获取某月的账目
     */
    public ArrayList<ItemEntity> getItem(ListEntity entity){
        return queryItems("SELECT * FROM Item WHERE month = "+entity.getMonth());
    }

    /**
     * 获取某月的账目
     */
    public ArrayList<ItemEntity> getItem(String month){
        return queryItems("SELECT * FROM Item WHERE month = "+month);
    }



    //以下为私有方法

    private ArrayList<ListEntity> queryLists(String sql){
        if(db==null) return null;
        ArrayList<ListEntity> list=new ArrayList<>();
        Cursor c = db.rawQuery(sql,null);
        if(c.moveToFirst()){
            do{
                list.add(ListEntityFromCursor(c));
            }while(c.moveToNext());
        }
        return list;
    }

    private ArrayList<ItemEntity> queryItems(String sql){
        if(db==null) return null;
        ArrayList<ItemEntity> list=new ArrayList<>();
        Cursor c = db.rawQuery(sql,null);
        if(c.moveToFirst()){
            do{
                list.add(ItemEntityFromCursor(c));
            }while(c.moveToNext());
        }
        return list;
    }

    private ListEntity queryList(String sql){
        if(db==null) return null;
        Cursor c = db.rawQuery(sql,null);
        if(!c.moveToFirst())
            return null;
        return ListEntityFromCursor(c);
    }

    private ItemEntity queryItem(String sql){
        if(db==null) return null;
        Cursor c = db.rawQuery(sql,null);
        if(!c.moveToFirst())
            return null;
        return ItemEntityFromCursor(c);
    }

    private ListEntity ListEntityFromCursor(Cursor c){
        String month=c.getString(c.getColumnIndex("month"));
        int balance100=c.getInt(c.getColumnIndex("balance"));
        String note=c.getString(c.getColumnIndex("note"));
        return new ListEntity(month,balance100,note);
    }

    private ItemEntity ItemEntityFromCursor(Cursor c){
        int itemId=c.getInt(c.getColumnIndex("itemId"));
        String month=c.getString(c.getColumnIndex("month"));
        short day=c.getShort(c.getColumnIndex("day"));
        String itemName=c.getString(c.getColumnIndex("itemName"));
        int price100=c.getInt(c.getColumnIndex("price"));
        String tagName=c.getString(c.getColumnIndex("tagName"));
        return new ItemEntity(itemId,month,day,itemName,price100,tagName);
    }

    SQLiteDatabase db;
    private static ListDAO instance;

    public static ListDAO getInstance(){
        if(instance==null)
            synchronized(ListDAO.class) {
                if (instance == null) {
                    instance = new ListDAO();
                }
            }
        return instance;
    }
    private ListDAO() {
        this.db = LeafSQLiteOpenHelper.getInstance().getWritableDatabase();
    }
}
