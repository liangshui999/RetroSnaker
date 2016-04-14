package com.example.asus_cp.model;

/**
 * 分数记录的实体类
 * Created by asus-cp on 2016-04-14.
 */
public class ScoreRecord {
    private int time;//记录次数
    private int score;//记录分数
    private String date;//记录时间

    public ScoreRecord(int time, int score, String date) {
        this.time = time;
        this.score = score;
        this.date = date;
    }
    public ScoreRecord(){}

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
