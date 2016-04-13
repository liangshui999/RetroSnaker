package com.example.asus_cp.service;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

import com.example.asus_cp.db.DBConstant;
import com.example.asus_cp.db.DBCreatHelper;
import com.example.asus_cp.model.SnakePoint;

/**
 * 主要用于向数据库中插入数据和查询数据
 * Created by asus-cp on 2016-04-12.
 */
public class RecordService {
    private String tag="RecordService";
    private DBCreatHelper helper;
    public RecordService(){
        helper=DBCreatHelper.getHelper();
    }

    /**
     * 向数据库中插入蛇头数据
     * @param time 记录次数
     */
    public void insertSnakeHead(SnakePoint point,int time){
        SQLiteDatabase db=null;
        try{
            db=helper.getWritableDatabase();
            ContentValues contentValues=new ContentValues();
            contentValues.put(DBConstant.RecordTable.TIME,time);
            contentValues.put(DBConstant.RecordTable.X,point.getX());
            contentValues.put(DBConstant.RecordTable.Y,point.getY());
        }catch (SQLiteException e){

        }finally {

        }


    }

}
