package com.huojitang.leaf;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/*
 * MK_TIP
 * [SQLiteOpenHelper]:是用于创建和返回数据库的类，此处实现它
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

/*
 * MK_TIP
 * DAO:【数据库】在后端编程中数据访问对象（DAO，Data Access Object）是用于执行SQL语句的一种类，通常一个表对应一个DAO
 */
class TagDAO{
    SQLiteDatabase db;

    public TagDAO(Context context) {
        this.db = new LeafSQLiteOpenHelper(context).getWritableDatabase();
    }

    public TagDAO(SQLiteDatabase db){
        this.db =db;
    }

    public ArrayList<TagEntity> getAllTags(){
        return getTags("SELECT * FROM Tag ORDER BY tagIndex ");
    }

    public ArrayList<TagEntity> getTagsWithoutDefault(){
        return getTags("SELECT * FROM Tag WHERE tagMode != -1 ORDER BY tagIndex ");
    }

    /**
     * 从编辑页传入数组，根据在数组的位置更新index,包括fromPosition和toPosition
     * @param arr
     */
    public void updateTagIndexes(List<TagEntity> arr,int fromPosition,int toPosition){
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
        short tagMode = c.getShort(c.getColumnIndex("tagMode"));
        String comment = c.getString(c.getColumnIndex("comment"));
        return new TagEntity(tagName,tagIndex,tagLimit100,color,tagMode,comment);
    }
}

class ListDAO{
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
        String month=c.getString(c.getColumnIndex("month"));
        int itemIndex=c.getInt(c.getColumnIndex("itemIndex"));
        String itemName=c.getString(c.getColumnIndex("itemName"));
        int price100=c.getInt(c.getColumnIndex("price"));
        String tagName=c.getString(c.getColumnIndex("tagName"));
        return new ItemEntity(month,itemIndex,itemName,price100,tagName);
    }

}

class WishDAO{
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

