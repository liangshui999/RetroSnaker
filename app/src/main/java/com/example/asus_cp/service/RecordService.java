package com.example.asus_cp.service;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

import com.example.asus_cp.db.DBConstant;
import com.example.asus_cp.db.DBCreatHelper;
import com.example.asus_cp.model.FoodPoint;
import com.example.asus_cp.model.SnakePoint;

import java.util.ArrayList;
import java.util.List;

/**
 * 主要用于向数据库中插入数据和查询数据
 * Created by asus-cp on 2016-04-12.
 */
public class RecordService {
    private String tag="RecordService";
    private DBCreatHelper helper;

    //蛇头的类别
    public static final String SANKE_HEAD="snakeHead";

    //蛇身体的类别
    public static final String SANKE_BODY="snakeBody";

    //食物的类别
    public static final String FOOD="food";


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
            contentValues.put(DBConstant.RecordTable.CATEGORY,SANKE_HEAD);
            contentValues.put(DBConstant.RecordTable.X,point.getX());
            contentValues.put(DBConstant.RecordTable.Y,point.getY());
            contentValues.put(DBConstant.RecordTable.ORITATION,point.getOritation());
            db.insert(DBConstant.RecordTable.RECORD_TABLE_NAME,null,contentValues);
        }catch (SQLiteException e){
            Log.d(tag,e.toString());
        }finally {
            if(db!=null){
                db.close();
            }

        }
    }

    /**
     * 向数据库中插入蛇身数据
     * @param time 记录次数
     */
    public void insertSnakeBody(List<SnakePoint>points,int time){
        SQLiteDatabase db=null;
        try{
            db=helper.getWritableDatabase();
            ContentValues contentValues=new ContentValues();
            db.beginTransaction();//开启事务
            for(SnakePoint point:points){
                contentValues.put(DBConstant.RecordTable.TIME,time);
                contentValues.put(DBConstant.RecordTable.CATEGORY,SANKE_BODY);
                contentValues.put(DBConstant.RecordTable.X,point.getX());
                contentValues.put(DBConstant.RecordTable.Y,point.getY());
                db.insert(DBConstant.RecordTable.RECORD_TABLE_NAME,null,contentValues);
                contentValues.clear();//清除contentvalues
            }
            db.setTransactionSuccessful();//标志事务成功

        }catch (SQLiteException e){
            Log.d(tag,e.toString());
        }finally {
            if(db!=null){
                db.endTransaction();//结束事务
                db.close();
            }
        }
    }

    /**
     * 向数据库中插入食物数据
     * @param time 记录次数
     */
    public void insertFood(FoodPoint point,int time){
        SQLiteDatabase db=null;
        try{
           db=helper.getWritableDatabase();
            ContentValues contentValues=new ContentValues();
            contentValues.put(DBConstant.RecordTable.TIME,time);
            contentValues.put(DBConstant.RecordTable.CATEGORY,FOOD);
            contentValues.put(DBConstant.RecordTable.X,point.getX());
            contentValues.put(DBConstant.RecordTable.Y, point.getY());
            db.insert(DBConstant.RecordTable.RECORD_TABLE_NAME, null, contentValues);
        }catch (SQLiteException e){
            Log.d(tag,e.toString());
        }finally {
            if(db!=null){
                db.close();
            }
        }
    }

    /**
     * 向数据库中插入分数的数据
     * @param score 分数
     * @param time 记录次数
     */
    public void insertScore(int time,int score){
        SQLiteDatabase db=null;
        try{
            db=helper.getWritableDatabase();
            ContentValues contentValues=new ContentValues();
            contentValues.put(DBConstant.RecordTable.TIME,time);
            contentValues.put(DBConstant.Score.SCORE,score);
            db.insert(DBConstant.Score.SCORE_TABLE_NAME,null,contentValues);
        }catch (SQLiteException e){
            Log.d(tag,e.toString());
        }finally {
            if(db!=null){
                db.close();
            }
        }
    }

    /**
     * 查询的通用方法
     * @param args sql语句中通配符对应的值
     *@param handler 处理cursor的接口
     */
    public Object query(String sql,String[] args,MyCursorHandler handler){
        SQLiteDatabase db=null;
        Cursor cursor=null;
        try{
            db=helper.getReadableDatabase();
            cursor=db.rawQuery(sql, args);
            return handler.handleCursor(cursor);
        }catch (SQLiteException e){
            Log.d(tag,e.toString());
        }finally {
            if(cursor!=null){
                cursor.close();
            }
            if(db!=null){
               db.close();
            }

        }
        return null;
    }

    /**
     * 根据记录次数查询蛇头数据
     */
    public SnakePoint querySnakeHead(int time){
        String sql=DBConstant.RecordTable.SELECT_SNAKE_HEAD;
        String[] args=new String[]{time+""};
        SnakePoint snakePoint= (SnakePoint) query(sql, args, new MyCursorHandler() {
            @Override
            public Object handleCursor(Cursor cursor) {
                SnakePoint snakePoint1=null;
                if(cursor.moveToNext()){
                    int x=cursor.getInt(cursor.getColumnIndex(DBConstant.RecordTable.X));
                    int y=cursor.getInt(cursor.getColumnIndex(DBConstant.RecordTable.Y));
                    int oritation=cursor.getInt(cursor.getColumnIndex(DBConstant.RecordTable.ORITATION));
                    snakePoint1=new SnakePoint(x,y,oritation);
                }
                return snakePoint1;
            }
        });
        return snakePoint;
    }

    /**
     * 根据记录次数查询蛇身数据
     */
    public List<SnakePoint> querySnakeBody(int time){
        String sql=DBConstant.RecordTable.SELECT_SNAKE_BODY;
        String[] args=new String[]{time+""};
        List<SnakePoint> snakePoints= (List<SnakePoint>) query(sql, args, new MyCursorHandler() {
            @Override
            public Object handleCursor(Cursor cursor) {
                SnakePoint snakePoint1=null;
                List<SnakePoint> points1=new ArrayList<SnakePoint>();
                while(cursor.moveToNext()){
                    int x=cursor.getInt(cursor.getColumnIndex(DBConstant.RecordTable.X));
                    int y=cursor.getInt(cursor.getColumnIndex(DBConstant.RecordTable.Y));
                    snakePoint1=new SnakePoint(x,y,0);
                    points1.add(snakePoint1);
                }
                return points1;
            }
        });
        return snakePoints;
    }

    /**
     * 根据记录次数查询食物数据
     * @param time 记录次数
     */
    public FoodPoint queryFood(int time){
        String sql=DBConstant.Score.SELECT_SCORE;
        String[] args=new String[]{time+""};
        FoodPoint foodPoint= (FoodPoint) query(sql, args, new MyCursorHandler() {
            @Override
            public Object handleCursor(Cursor cursor) {
                FoodPoint point=null;
                if(cursor.moveToNext()){
                    int x=cursor.getInt(cursor.getColumnIndex(DBConstant.RecordTable.X));
                    int y=cursor.getInt(cursor.getColumnIndex(DBConstant.RecordTable.Y));
                    point=new FoodPoint(x,y);
                }
                return point;
            }
        });
        return foodPoint;
    }

    /**
     * 根据记录次数查询分数
     * @param time 记录次数
     */
    public int queryScore(int time){
        String sql=DBConstant.Score.SELECT_SCORE;
        String[] args=new String[]{time+""};
        int scores= (int) query(sql, args, new MyCursorHandler() {
            @Override
            public Object handleCursor(Cursor cursor) {
                int score=-1;
                if(cursor.moveToNext()){
                    score=cursor.getInt(cursor.getColumnIndex(DBConstant.Score.SCORE));
                }
                return score;
            }
        });
        return scores;
    }

    /**
     * 查询最大的记录数（最后一次存进去的）
     */
    public int queryMaxTime(){
        String sql=DBConstant.Score.SELECT_MAX_TIME;
        int maxTime= (int) query(sql, null, new MyCursorHandler() {
            @Override
            public Object handleCursor(Cursor cursor) {
                int maxTime1=0;//没有数据时返回0
                if(cursor.moveToNext()){
                    maxTime1=cursor.getInt(cursor.getColumnIndex(DBConstant.Score.MAX_TIME));
                }
                return maxTime1;
            }
        });
        return maxTime;
    }

}
