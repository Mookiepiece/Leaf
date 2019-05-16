package com.huojitang.leaf;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/*
 * MK_TIP
 * 是用于创建和返回数据库的类，此处实现它
 */
public class LeafSQLiteOpenHelper extends SQLiteOpenHelper {

    @Override
    public void onCreate(SQLiteDatabase db) {

        //创建基本的4个表
        db.execSQL("CREATE TABLE Tag (tagName TEXT PRIMARY KEY, tagIndex INTEGER NOT NULL, tagLimit INTEGER, color TEXT NOT NULL, tagMode INTEGER NOT NULL, comment TEXT)");
        db.execSQL("CREATE TABLE List (month TEXT PRIMARY KEY, balance INTEGER NOT NULL,note TEXT)");
        db.execSQL("CREATE TABLE Item (month TEXT, itemIndex INTEGER, itemName TEXT, price INTEGER, tagName TEXT," +
                " FOREIGN KEY(month) REFERENCES List(month),FOREIGN KEY(tagName) REFERENCES Tag(tagName)," +
                "PRIMARY KEY(month,itemIndex))");
        db.execSQL("CREATE TABLE Wish (wishId INTEGER PRIMARY KEY AUTOINCREMENT,wishName TEXT NOT NULL, wishIndex INTEGER NOT NULL, price INTEGER, startTime TEXT, endTime TEXT, comment TEXT)");

        //创建‘未分类’标签，排在第100位
        db.execSQL("INSERT INTO Tag VALUES('default',100,NULL,'ddd',-1,NULL)");

        //创建‘测试’标签
        db.execSQL("INSERT INTO Tag VALUES('mytag1',1,60000,'860',0,'commtentfsdf')");
        db.execSQL("INSERT INTO Tag VALUES('mytag2',2,40000,'068',0,'commtentf565sdf')");
        db.execSQL("INSERT INTO Tag VALUES('标签的名字',0,40000,'a0a',0,'标签的描述描述描述描述描述描述')");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //TODO MK
    }

    public LeafSQLiteOpenHelper(Context context) {
        super(context, "Leaf.db", null, 1);
    }
    public LeafSQLiteOpenHelper(Context context,int version) {
        super(context, "Leaf.db", null, version);
    }
}

