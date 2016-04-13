package com.example.asus_cp.util;

import android.app.Application;
import android.content.Context;

/**
 * Created by asus-cp on 2016-04-12.
 */
public class MyAppliacation extends Application {
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context=getApplicationContext();
    }

    //不能再构造方法里面获取context,必须在oncreate里面
    public MyAppliacation(){
       // context=getContext();
    }
    public static Context getContext(){
        return context;
    }
}
