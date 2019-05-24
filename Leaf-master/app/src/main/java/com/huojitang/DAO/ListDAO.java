package com.huojitang.DAO;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.huojitang.leaf.LeafSQLiteOpenHelper;

import java.util.ArrayList;

import com.huojitang.entities.ItemEntity;
import com.huojitang.entities.ListEntity;

public class ListDAO{
    SQLiteDatabase db;

    public ArrayList<ListEntity> getList(){
        return getLists("SELECT * FROM List ORDER BY tagIndex WHERE tagMode != -1");
    }

    public ListDAO(Context context) {
        this.db = new LeafSQLiteOpenHelper(context).getWritableDatabase();
    }

    public ListDAO(SQLiteDatabase db){
        this.db =db;
    }

    private ArrayList<ListEntity> getLists(String sql){
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

    private ArrayList<ItemEntity> getItems(String sql){
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

    private ListEntity getList(String sql){
        if(db==null) return null;
        Cursor c = db.rawQuery(sql,null);
        if(!c.moveToFirst())
            return null;
        return ListEntityFromCursor(c);
    }

    private ItemEntity getItem(String sql){
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

}
