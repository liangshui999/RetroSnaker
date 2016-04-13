package com.example.asus_cp.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.asus_cp.util.MyAppliacation;

/**
 * Created by asus-cp on 2016-04-12.
 */
public class DBCreatHelper extends SQLiteOpenHelper {
    private static Context context= MyAppliacation.getContext();
    private static DBCreatHelper helper=new DBCreatHelper(context,DBConstant.DB_NAME,null,DBConstant.VERSION);
    private DBCreatHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public static DBCreatHelper getHelper() {
        return helper;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DBConstant.RecordTable.CREAT_RECORD_TABLE);
        db.execSQL(DBConstant.Score.CREATE_SCORE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
