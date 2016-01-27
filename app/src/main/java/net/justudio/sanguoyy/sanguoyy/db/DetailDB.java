package net.justudio.sanguoyy.sanguoyy.db;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import net.justudio.sanguoyy.sanguoyy.model.Detail;

import java.util.ArrayList;
import java.util.List;

/**
 * 内容DB
 */
public class DetailDB extends SQLiteOpenHelper {
    private static final String TAG="Detail DB";
    private static final String DATABASE_NAME="detail.db";
    private static final int DATABASE_VERSION=1;
    private static final String TABLES_NAME="detailTable";

    public DetailDB(Context context){
        super(context, DATABASE_NAME, null ,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE " + TABLES_NAME + "(" + "_ID"
                        + " INTEGER PRIMARY KEY,"
                        + "content" + " TEXT" + ");"
        );

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS detailTable");
        onCreate(db);
    }

    public void insert(List<Detail> list){
        SQLiteDatabase db=this.getWritableDatabase();
        for (Detail detail:list){
            ContentValues values=new ContentValues();
            values.put("content", detail.getContent());
            db.insert("detailTable", null, values);
        }
    }


    public List<Detail> query(){
        List<Detail> list= new ArrayList<Detail>();
        SQLiteDatabase db=this.getReadableDatabase();
        String sql="select * from detailTable";
        Cursor cursor =db.rawQuery(sql,null);
        cursor.moveToFirst();
        if (cursor.getCount()>0){
            do{
                Detail detail=new Detail();
                detail.setContent(cursor.getString(1));

                list.add(detail);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return list;

    }
}
