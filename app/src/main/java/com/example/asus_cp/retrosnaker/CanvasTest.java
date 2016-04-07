package com.example.asus_cp.retrosnaker;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import java.util.List;

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
    ImageView imgUp;
    @Bind(R.id.img_left) ImageView imgLeft;
    @Bind(R.id.img_right) ImageView imgRight;
    @Bind(R.id.img_down) ImageView imgDown;
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.custom_layout);
        ButterKnife.bind(this);
        v=new CustumView(this);
        framBuf.addView(v);
        xMin=radios;
        yMin=radios;
        xMax=v.getWidth()-radios;
        yMax=v.getHeight()-radios;


    }

    @OnClick(R.id.img_up) void onImgUpClick() {
        //TODO implement
        int y=snakeHead.getY();//上一次的y值
        int oritation=snakeHead.getOritation();//上一次的方向
        if(oritation!=SnakePoint.DOWN){//如果上一次向下，则本次不能向上
            snakeHead.setY(y-radios);
            snakeHead.setOritation(SnakePoint.UP);//把方向设置为向上

            for(SnakePoint snakePoint:sPoints){
                if(snakePoint.getX()==snakeHead.getX() && snakeHead.getY()<=snakePoint.getY()){//撞到自己的身体了
                    return;
                }
            }
            if(snakeHead.getY()<=yMin){//碰到墙了
                return;
            }

            //吃到食物了
            if(snakeHead.getX()==foodPoint.getX() && snakeHead.getY()==foodPoint.getY()){
                SnakePoint firstPoint=new SnakePoint();
                firstPoint.setX(snakeHead.getX());
                firstPoint.setY(snakeHead.getY());
                sPoints.add(firstPoint);
                int temp=snakeHead.getY();
                snakeHead.setY(temp-radios);

            }

            //重新绘图
            framBuf.removeAllViews();
            v=new CustumView(this);
            framBuf.addView(v);
        }

    }



    @OnClick(R.id.img_left) void onImgLeftClick() {
        //TODO implement
        int x=snakeHead.getX();//上一次的x值
        int oritation=snakeHead.getOritation();//上一次的方向
        if(oritation!=SnakePoint.RIGHT){
            snakeHead.setX(x-radios);
            snakeHead.setOritation(SnakePoint.LEFT);//把方向设置成向左

            for(SnakePoint snakePoint:sPoints){
                if(snakePoint.getY()==snakeHead.getY() && snakeHead.getX()<=snakePoint.getX()){//撞到自己的身体了
                    return;
                }
            }
            if(snakeHead.getX()<=xMin){//碰到墙了
                return;
            }

            //吃到食物了
            if(snakeHead.getX()==foodPoint.getX() && snakeHead.getY()==foodPoint.getY()){
                SnakePoint firstPoint=new SnakePoint();
                firstPoint.setX(snakeHead.getX());
                firstPoint.setY(snakeHead.getY());
                sPoints.add(firstPoint);
                int temp=snakeHead.getX();
                snakeHead.setY(temp-radios);

            }
        }

    }



    @OnClick(R.id.img_right) void onImgRightClick() {
        //TODO implement
        int x=snakeHead.getX();
        int oritation=snakeHead.getOritation();//上一次的方向
        if(oritation!=SnakePoint.LEFT){
            snakeHead.setX(x+radios);
            snakeHead.setOritation(SnakePoint.RIGHT);//把方向设置成向右



            for(SnakePoint snakePoint:sPoints){
                if(snakePoint.getY()==snakeHead.getY() && snakeHead.getX()>=snakePoint.getX()){//撞到自己的身体了
                    return;
                }
            }
            if(snakeHead.getX()>=xMax){//碰到墙了
                return;
            }

            //吃到食物了
            if(snakeHead.getX()==foodPoint.getX() && snakeHead.getY()==foodPoint.getY()){
                SnakePoint firstPoint=new SnakePoint();
                firstPoint.setX(snakeHead.getX());
                firstPoint.setY(snakeHead.getY());
                sPoints.add(firstPoint);
                int temp=snakeHead.getX();
                snakeHead.setY(temp+radios);

            }
        }

    }



    @OnClick(R.id.img_down) void onImgDownClick() {
        //TODO implement
        int y=snakeHead.getY();
        int oritation=snakeHead.getOritation();//上一次的方向
        if(oritation!=SnakePoint.UP){
            snakeHead.setY(y+radios);
            snakeHead.setOritation(SnakePoint.DOWN);

            for(SnakePoint snakePoint:sPoints){
                if(snakePoint.getX()==snakeHead.getX() && snakeHead.getY()>=snakePoint.getY()){//撞到自己的身体了
                    return;
                }
            }
            if(snakeHead.getY()>=yMax){//撞到墙了
                return;
            }

            //吃到食物了
            if(snakeHead.getX()==foodPoint.getX() && snakeHead.getY()==foodPoint.getY()){
                SnakePoint firstPoint=new SnakePoint();
                firstPoint.setX(snakeHead.getX());
                firstPoint.setY(snakeHead.getY());
                sPoints.add(firstPoint);
                int temp=snakeHead.getY();
                snakeHead.setY(temp+radios);

            }
        }

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
            canvas.drawCircle(radios, radios, radios, paint);
            canvas.drawCircle(v.getWidth()-radios,v.getHeight()-radios,radios,paint);
            canvas.drawCircle(radios,v.getHeight()-radios,radios,paint);
            canvas.drawCircle(v.getWidth()-radios,radios,radios,paint);

        }
    }
}
