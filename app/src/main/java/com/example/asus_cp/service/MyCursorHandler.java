package com.example.asus_cp.service;

import android.database.Cursor;

/**
 * 处理查询出来的结果的接口
 * Created by asus-cp on 2016-04-13.
 */
public interface MyCursorHandler {
    public Object handleCursor(Cursor cursor);
}
