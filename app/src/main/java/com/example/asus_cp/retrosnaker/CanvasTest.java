package com.example.asus_cp.retrosnaker;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by asus-cp on 2016-04-06.
 */
public class CanvasTest extends Activity{
    @Bind(R.id.fram_buf)
    FrameLayout framBuf;
    @Bind(R.id.img_up)
    Button imgUp;
    @Bind(R.id.img_left) Button imgLeft;
    @Bind(R.id.img_right) Button imgRight;
    @Bind(R.id.img_down) Button imgDown;
    private View v;//画布
    private int radios=10;//半径

    //边界值，过了边界就算失败了
    private int xMin;
    private int yMin;
    private int xMax;
    private int yMax;

    //用来存储蛇身上各个点的坐标值的集合(只存储蛇身，不存储蛇头)
    private List<SnakePoint> sPoints;

    //用来存储蛇头坐标
    private SnakePoint snakeHead;

    //用来存储随机生成的食物的坐标点
    private FoodPoint foodPoint;


    //定时发送消息的handler
    private MyHandler myHandler;



    public static final int DING_SHI=1;//定时任务的标记（用于发送消息）
    public static final int ORITATION_CHANGED=2;//方向改变的标记(用于发送消息)
    public static final int FOOD_HAVE_EATED=3;//食物被吃了之后发送的消息的标记（请求重新生成一个食物）



    class MyHandler extends Handler{
        @Override
        public void handleMessage(Message msg) {//此处的定时任务是这个程序的核心，通过handler发送延时消息做定时任务
            switch (msg.what){
                case DING_SHI:
                    //重新绘图
                    framBuf.removeAllViews();
                    v=new CustumView(CanvasTest.this);
                    framBuf.addView(v);
                    //移动蛇头和蛇身的坐标
                    move(snakeHead.getOritation());
                    myHandler.sendEmptyMessageDelayed(DING_SHI,1000);//定时发送延迟的消息
                    break;
                case ORITATION_CHANGED://方向改变
                    //移动蛇头和蛇身的坐标
                    move(snakeHead.getOritation());
                    //重新绘图
                    framBuf.removeAllViews();
                    v=new CustumView(CanvasTest.this);
                    framBuf.addView(v);
                    break;
                case FOOD_HAVE_EATED://食物被吃了，请求重新生成食物
                    produceFoodPosition();
                    //重新绘图
                    framBuf.removeAllViews();
                    v=new CustumView(CanvasTest.this);
                    framBuf.addView(v);
            }
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.custom_layout);
        ButterKnife.bind(this);
        init();
    }

    /**
     * 初始化方法
     */
    public void init(){
        v=new CustumView(this);
        framBuf.addView(v);
        xMin=radios;
        yMin=radios;
        xMax=v.getWidth()-radios;
        yMax=v.getHeight()-radios;
        sPoints=new ArrayList<SnakePoint>();
        snakeHead=new SnakePoint(radios,radios,SnakePoint.RIGHT);

        produceSnakePositon();
        foodPoint=new FoodPoint();
        produceFoodPosition();//随机生成的食物位置
        myHandler=new MyHandler();
        myHandler.sendEmptyMessage(DING_SHI);
    }


    /**
     * 移动蛇头和蛇身的坐标(没有改变方向之前，一直默认按一个方向走,相当于每隔1s中向上走一次)
     * 方向改变之后再调用一次move方法即可
     */
    public void move(int oritation){
        switch (oritation){
            case SnakePoint.UP://向上移动
                SnakePoint temp=new SnakePoint(snakeHead.getX(),snakeHead.getY(),snakeHead.getOritation());
                int y=snakeHead.getY();//上一次的y值
                snakeHead.setY(y-radios);
                if(sPoints.size()>0){
                    sPoints.remove(sPoints.size() - 1);//把蛇身的最后一个移除，蛇身的最前面添加一个
                }
                sPoints.add(0, temp);
                isToSelf(oritation);//咬到自己
                isToWall(oritation);
                isEatFood(oritation);
                break;

            //向下移动
            case SnakePoint.DOWN:
                SnakePoint temp1=new SnakePoint(snakeHead.getX(),snakeHead.getY(),snakeHead.getOritation());
                int y1=snakeHead.getY();
                snakeHead.setY(y1+radios);
                if(sPoints.size()>0){
                    sPoints.remove(sPoints.size() - 1);//把蛇身的最后一个移除，蛇身的最前面添加一个
                }
                sPoints.add(0, temp1);
                isToSelf(oritation);//咬到自己
                isToWall(oritation);
                isEatFood(oritation);
                break;


            case SnakePoint.LEFT:
                SnakePoint temp2=new SnakePoint(snakeHead.getX(),snakeHead.getY(),snakeHead.getOritation());
                int x1=snakeHead.getX();
                snakeHead.setX(x1-radios);
                if(sPoints.size()>0){
                    sPoints.remove(sPoints.size() - 1);//把蛇身的最后一个移除，蛇身的最前面添加一个
                }
                sPoints.add(0, temp2);
                isToSelf(oritation);//咬到自己
                isToWall(oritation);
                isEatFood(oritation);
                break;


            case SnakePoint.RIGHT:
                SnakePoint temp3=new SnakePoint(snakeHead.getX(),snakeHead.getY(),snakeHead.getOritation());
                int x2=snakeHead.getX();
                snakeHead.setX(x2 + radios);
                if(sPoints.size()>0){
                    sPoints.remove(sPoints.size() - 1);//把蛇身的最后一个移除，蛇身的最前面添加一个
                }
                sPoints.add(0, temp3);
                isToSelf(oritation);//咬到自己
                isToWall(oritation);
                isEatFood(oritation);
                break;

        }
    }

    /**
     * 判断是否撞到墙了
     */
    public void isToWall(int oritation){
        switch (oritation){
            case SnakePoint.UP:
                if(snakeHead.getY()==yMin){//碰到墙了
                    showDialog("撞到墙了");
                }
                break;

            case SnakePoint.DOWN:
                if(snakeHead.getY()==yMax){//碰到墙了
                   showDialog("撞到墙了");
                }
                break;

            case SnakePoint.LEFT:
                if(snakeHead.getX()==xMin){//碰到墙了
                    showDialog("撞到墙了");
                }
                break;


            case SnakePoint.RIGHT:
                if(snakeHead.getX()==xMax){//碰到墙了
                    showDialog("撞到墙了");
                }
                break;
        }

    }


    /**
     * 判断是否撞到自己的身体了
     */
    public void isToSelf(int oritation){
        switch (oritation){
            case SnakePoint.UP:
                for(SnakePoint snakePoint:sPoints){
                    if(snakePoint.getX()==snakeHead.getX() && snakeHead.getY()==snakePoint.getY()){//撞到自己的身体了
                        showDialog("咬到自己了");
                    }
                }
                break;
            case SnakePoint.DOWN:
                for(SnakePoint snakePoint:sPoints){
                    if(snakePoint.getX()==snakeHead.getX() && snakeHead.getY()==snakePoint.getY()){//撞到自己的身体了
                        showDialog("咬到自己了");
                    }
                }
                break;
            case SnakePoint.LEFT:
                for(SnakePoint snakePoint:sPoints){
                    if(snakePoint.getY()==snakeHead.getY() && snakeHead.getX()==snakePoint.getX()){//撞到自己的身体了
                        showDialog("咬到自己了");
                    }
                }
                break;
            case SnakePoint.RIGHT:
                for(SnakePoint snakePoint:sPoints){
                    if(snakePoint.getY()==snakeHead.getY() && snakeHead.getX()==snakePoint.getX()){//撞到自己的身体了
                        showDialog("咬到自己了");
                    }
                }
                break;
        }
    }


    /**
     * 判断是否吃到食物了
     */

    public void isEatFood(int oritation){
        switch(oritation){
            case SnakePoint.UP:
                if(snakeHead.getX()==foodPoint.getX() && snakeHead.getY()==foodPoint.getY()){
                    SnakePoint firstPoint=new SnakePoint();
                    firstPoint.setX(snakeHead.getX());
                    firstPoint.setY(snakeHead.getY());
                    firstPoint.setOritation(snakeHead.getOritation());
                    sPoints.add(0,firstPoint);//在集合的头部添加一个点
                    int temp=snakeHead.getY();
                    snakeHead.setY(temp-radios);
                    myHandler.sendEmptyMessage(FOOD_HAVE_EATED);//食物别吃了之后发送的消息
                }
                break;
            case SnakePoint.DOWN:
                if(snakeHead.getX()==foodPoint.getX() && snakeHead.getY()==foodPoint.getY()){
                    SnakePoint firstPoint=new SnakePoint();
                    firstPoint.setX(snakeHead.getX());
                    firstPoint.setY(snakeHead.getY());
                    firstPoint.setOritation(snakeHead.getOritation());
                    sPoints.add(0,firstPoint);//在集合的头部添加一个点
                    int temp=snakeHead.getY();
                    snakeHead.setY(temp+radios);
                    myHandler.sendEmptyMessage(FOOD_HAVE_EATED);
                }
                break;
            case SnakePoint.LEFT:
                if(snakeHead.getX()==foodPoint.getX() && snakeHead.getY()==foodPoint.getY()){
                    SnakePoint firstPoint=new SnakePoint();
                    firstPoint.setX(snakeHead.getX());
                    firstPoint.setY(snakeHead.getY());
                    firstPoint.setOritation(snakeHead.getOritation());
                    sPoints.add(0,firstPoint);//在集合的头部添加一个点
                    int temp=snakeHead.getX();
                    snakeHead.setY(temp-radios);
                    myHandler.sendEmptyMessage(FOOD_HAVE_EATED);
                }
                break;
            case SnakePoint.RIGHT:
                if(snakeHead.getX()==foodPoint.getX() && snakeHead.getY()==foodPoint.getY()){
                    SnakePoint firstPoint=new SnakePoint();
                    firstPoint.setX(snakeHead.getX());
                    firstPoint.setY(snakeHead.getY());
                    firstPoint.setOritation(snakeHead.getOritation());
                    sPoints.add(0,firstPoint);//在集合的头部添加一个点
                    int temp=snakeHead.getX();
                    snakeHead.setY(temp+radios);
                    myHandler.sendEmptyMessage(FOOD_HAVE_EATED);
                }
                break;

        }
    }

    @OnClick(R.id.img_up) void onImgUpClick() {
        //TODO implement
        if(snakeHead.getOritation()!=SnakePoint.DOWN){
            snakeHead.setOritation(SnakePoint.UP);//把方向设置为向上
            myHandler.sendEmptyMessage(ORITATION_CHANGED);
        }


    }

    @OnClick(R.id.img_down) void onImgDownClick() {
        //TODO implement
        if(snakeHead.getOritation()!=SnakePoint.UP){
            snakeHead.setOritation(SnakePoint.DOWN);//把方向设置为向下
            myHandler.sendEmptyMessage(ORITATION_CHANGED);
        }


    }

    @OnClick(R.id.img_left) void onImgLeftClick() {
        //TODO implement
        if(snakeHead.getOritation()!=SnakePoint.RIGHT){
            snakeHead.setOritation(SnakePoint.LEFT);//把方向设置为向左
            myHandler.sendEmptyMessage(ORITATION_CHANGED);
        }

    }


    @OnClick(R.id.img_right) void onImgRightClick() {
        //TODO implement
        if(snakeHead.getOritation()!=SnakePoint.LEFT){
            snakeHead.setOritation(SnakePoint.RIGHT);//把方向设置为向右
            myHandler.sendEmptyMessage(ORITATION_CHANGED);
        }

    }

    /**
     * 弹出一个对话框，提示内容不同，比如撞到墙了，咬到自己的身体了
     */
    public void showDialog(String tishi){
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        AlertDialog dialog=builder.create();
        dialog.setTitle("游戏失败");
        dialog.setMessage(tishi);
        dialog.show();
    }

    /**
     * 随机生成符合条件的x
     */
    public int generate(){
        Random ra =new Random();
        int x=ra.nextInt(30);
        x=x*radios;
        //int x= (int) (xMax*Math.random());
        if(x>xMin && x<300){
            return x;
        }else {
            generate();
        }
        return -1;
    }
    /**
     * 随机生成食物的坐标
     */
    public void produceFoodPosition(){
        foodPoint.setX(generate());
        foodPoint.setY(generate());
    }
    /**
     * 随机生成蛇头的初始坐标
     */
    public void produceSnakePositon(){
        snakeHead.setX(generate());
        snakeHead.setY(generate());
    }





    /**
     * 自定义的view
     */
    class CustumView extends View{
        Paint paint;
        public CustumView(Context context) {
            super(context);//必然会访问父类的构造方法，但父类没有空参构造方法，所以必须指定一个
            paint=new Paint();
            paint.setColor(Color.YELLOW);
            paint.setStrokeJoin(Paint.Join.ROUND);
            paint.setStrokeCap(Paint.Cap.ROUND);
            paint.setStrokeWidth(3);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            canvas.drawCircle(snakeHead.getX(), snakeHead.getY(), radios, paint);//绘制蛇头
            paint.setColor(Color.GREEN);
            for(SnakePoint point:sPoints){
                canvas.drawCircle(point.getX(),point.getY(),radios,paint);//绘制蛇身
            }
            paint.setColor(Color.RED);
            canvas.drawCircle(foodPoint.getX(),foodPoint.getY(),radios,paint);//绘制食物
//            canvas.drawCircle(xx, yy, radios, paint);
//            canvas.drawCircle(v.getWidth()-radios,v.getHeight()-radios,radios,paint);
//            canvas.drawCircle(radios,v.getHeight()-radios,radios,paint);
//            canvas.drawCircle(v.getWidth()-radios,radios,radios,paint);

        }
    }
}
