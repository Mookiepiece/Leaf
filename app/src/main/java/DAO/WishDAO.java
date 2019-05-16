package DAO;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.huojitang.leaf.LeafSQLiteOpenHelper;

import java.util.ArrayList;

import entities.WishEntity;

public class WishDAO{
    SQLiteDatabase db;
    public WishDAO(Context context) {
        this.db = new LeafSQLiteOpenHelper(context).getWritableDatabase();
    }

    public WishDAO(SQLiteDatabase db){
        this.db =db;
    }

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
        int price100=c.getInt(c.getColumnIndex("price"));
        String startTime=c.getString(c.getColumnIndex("startTime"));
        String endTime=c.getString(c.getColumnIndex("endTime"));
        String comment=c.getString(c.getColumnIndex("comment"));
        return new WishEntity(wishId,wishIndex,wishName,price100,startTime,endTime,comment);
    }
}
