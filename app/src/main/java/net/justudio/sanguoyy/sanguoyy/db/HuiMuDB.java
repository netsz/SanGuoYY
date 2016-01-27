package net.justudio.sanguoyy.sanguoyy.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import net.justudio.sanguoyy.sanguoyy.model.HuiMu;

import java.util.ArrayList;
import java.util.List;

/**
 * 回目DB
 */
public class HuiMuDB extends SQLiteOpenHelper {

    private static final String TAG="HuiMu DB";
    private static final String DATABASE_NAME="huimu.db";
    private static final int DATABASE_VERSION=1;
    private static final String TABLES_NAME="huimuTable";

    public HuiMuDB(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE " + TABLES_NAME + "(" + "_ID"
                        + " INTEGER PRIMARY KEY," + "title" + " TEXT,"
                        + "link" + " TEXT" + ");"
        );

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS huimuTable");
        onCreate(db);

    }

    public void insert(List<HuiMu> list){
        SQLiteDatabase db=this.getWritableDatabase();
        for (HuiMu huiMu:list){
            ContentValues values=new ContentValues();
            values.put("title", huiMu.getName());
            values.put("link", huiMu.getLink());
            db.insert("huimuTable", null, values);
        }
    }

    public List<HuiMu> query(){
        List<HuiMu> list= new ArrayList<HuiMu>();
        SQLiteDatabase db=this.getReadableDatabase();
        String sql="select * from huimuTable";
        Cursor cursor =db.rawQuery(sql,null);
        cursor.moveToFirst();
        if (cursor.getCount()>0){
            do{
                HuiMu huiMu=new HuiMu();
                huiMu.setName(cursor.getString(1));
                huiMu.setLink(cursor.getString(2));

                list.add(huiMu);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return list;

    }
}
