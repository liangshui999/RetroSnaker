package com.example.asus_cp.model;

/**
 * 专门用来存储食物坐标的
 * Created by asus-cp on 2016-04-07.
 */
public class FoodPoint {
    private int x;
    private int y;

    public FoodPoint(int x, int y) {
        this.x = x;
        this.y = y;
    }
    public FoodPoint(){}

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}
