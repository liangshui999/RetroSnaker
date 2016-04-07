package com.example.asus_cp.retrosnaker;

/**
 * 用来存储蛇身上各个点的坐标
 * Created by asus-cp on 2016-04-06.
 */
public class SnakePoint {
    private int x;
    private int y;
    private int oritation;//方向
    public static final int UP=1;//方向向上
    public static final int DOWN=2;
    public static final int LEFT=3;
    public static final int RIGHT=4;

    public SnakePoint(int x, int y,int oritation) {
        this.x = x;
        this.y = y;
        this.oritation=oritation;
    }
    public SnakePoint(){}

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

    public int getOritation() {
        return oritation;
    }

    public void setOritation(int oritation) {
        this.oritation = oritation;
    }
}
