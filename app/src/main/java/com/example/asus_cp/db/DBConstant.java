package com.example.asus_cp.db;

/**
 * Created by asus-cp on 2016-04-12.
 */
public interface DBConstant {
    public static final String DB_NAME="myDataBase";//数据库名称
    public static final int VERSION=1;//数据库版本


    //记录表中的常量
    public interface RecordTable{
        //建表语句
        public static final String CREAT_RECORD_TABLE="create table if not exists record(" +
                "id integer primary key autoincrement," +
                "time integer not null," +
                "category text not null," +
                "xvalue integer," +
                "yvalue integer," +
                "oritation integer" +
                ")";
        //contentvalue的键值
        public static final String TIME="time";
        public static final String CATEGORY="category";
        public static final String X="xvalue";
        public static final String Y="yvalue";
        public static final String ORITATION="oritation";

        //记录表的名称
        public static final String RECORD_TABLE_NAME="record";


        //查询蛇头语句
        public static final String SELECT_SNAKE_HEAD="select xvalue yvalue oritation from record where category=snakeHead " +
                "and time=?";

        //查询蛇身的语句
        public static final String SELECT_SNAKE_BODY="select xvalue yvalue from record where category=snakeBody " +
                "and time=?";

        //查询食物的语句
        public static final String SELCET_FOOD="select xvalue yvalue from record where category=food and time=?";

    }



    //分数表
    public interface Score{

        //分数表的名称
        public static final String SCORE_TABLE_NAME="scoreTable";

        public static final String CREATE_SCORE_TABLE="create table if not exists scoreTable(" +
                "id integer primary key autoincrement," +
                "time integer not null unique," +
                "score integer" +
                ")";

        //contentvalue的键值
        public static final String SCORE="score";
        public static final String MAX_TIME="maxTime";

        //查询分数的语句
        public static final String SELECT_SCORE="select score from scoreTable where time=?";
        public static final String SELECT_MAX_TIME="select max(time) as maxTime from scoreTable";
    }

}
