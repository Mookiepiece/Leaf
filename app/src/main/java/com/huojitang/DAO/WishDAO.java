package com.huojitang.DAO;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.huojitang.leaf.LeafSQLiteOpenHelper;

import java.util.ArrayList;

import com.huojitang.entities.WishEntity;

public class WishDAO{


    private ArrayList<WishEntity> getWishes(String sql){
        if(db==null) return null;
        ArrayList<WishEntity> list=new ArrayList<>();
        Cursor c = db.rawQuery(sql,null);
        if(c.moveToFirst()){
            do{
                list.add(WishEntityFromCursor(c));
            }while(c.moveToNext());
        }
        return list;
    }

    private WishEntity getItem(String sql){
        if(db==null) return null;
        Cursor c = db.rawQuery(sql,null);
        if(!c.moveToFirst())
            return null;
        return WishEntityFromCursor(c);
    }

    private WishEntity WishEntityFromCursor(Cursor c){
        int wishId=c.getInt(c.getColumnIndex("wishId"));
        int wishIndex=c.getInt(c.getColumnIndex("wishIndex"));
        String wishName=c.getString(c.getColumnIndex("wishName"));
        short wishState=c.getShort(c.getColumnIndex("wishState"));
        int price100=c.getInt(c.getColumnIndex("price"));
        String startTime=c.getString(c.getColumnIndex("startTime"));
        String endTime=c.getString(c.getColumnIndex("endTime"));
        String comment=c.getString(c.getColumnIndex("comment"));
        return new WishEntity(wishId,wishIndex,wishName,wishState,price100,startTime,endTime,comment);
    }

    SQLiteDatabase db;
    private static WishDAO instance;

    public static WishDAO getInstance(){
        if(instance==null)
            synchronized(WishDAO.class) {
                if (instance == null) {
                    instance = new WishDAO();
                }
            }
        return instance;
    }

    private WishDAO() {
        this.db = LeafSQLiteOpenHelper.getInstance().getWritableDatabase();
    }
}
