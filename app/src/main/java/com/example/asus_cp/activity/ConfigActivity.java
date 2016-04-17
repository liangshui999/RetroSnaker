package com.example.asus_cp.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 设置界面
 * Created by asus-cp on 2016-04-11.
 */
public class ConfigActivity extends Activity {
    @Bind(R.id.spin_speed)
    Spinner spinSpeed;
    @Bind(R.id.spin_backgrond_music) Spinner spinBackgrondMusic;
    @Bind(R.id.btn_sure_set)Button sureButton;
    @Bind(R.id.btn_cancel_set)Button cancelButton;

    //速度的数据源
    private String[] speeds={"正常","快","慢"};

    //背景音乐选项的数组
    private String[] backgroundMusics={"开","关"};

    //速度的适配器
    private ArrayAdapter speedAdapter;

    //背景音乐选项的适配器
    private ArrayAdapter backgroundMusicAdapter;

    //记录选中的速度选项
    private String selectedSpeed;

    //记录选中的音乐选项
    private String selectedMusic;

    public static final String SPEED_KEY="speedKey";//intent传数据时的键
    public static final String MUSCI_KEY="musicKey";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.set_layout);
        ButterKnife.bind(this);
        init();
    }
    /**
     * 初始化的方法
     */
    public void init(){
        speedAdapter=new ArrayAdapter(this,android.R.layout.simple_spinner_dropdown_item,speeds);
        backgroundMusicAdapter=new ArrayAdapter(this,android.R.layout.simple_spinner_dropdown_item,
                backgroundMusics);
        spinSpeed.setAdapter(speedAdapter);
        spinBackgrondMusic.setAdapter(backgroundMusicAdapter);

        spinSpeed.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedSpeed = speeds[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        spinBackgrondMusic.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedMusic = backgroundMusics[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    //确定按钮的点击事件
    @OnClick(R.id.btn_sure_set) void onSetButtonClick(){
        Intent intent=new Intent();
        intent.putExtra(SPEED_KEY, selectedSpeed);
        intent.putExtra(MUSCI_KEY, selectedMusic);
        setResult(RESULT_OK, intent);//返回数据给上一个活动
        finish();
    }
    //点返回键返回的事件
    @Override
    public void onBackPressed() {
        Intent intent=new Intent();
        intent.putExtra(SPEED_KEY,selectedSpeed);
        intent.putExtra(MUSCI_KEY,selectedMusic);
        setResult(RESULT_OK,intent);//返回数据给上一个活动
        finish();
    }

    //取消按钮的点击事件
    @OnClick(R.id.btn_cancel_set) void cancelButtonOnClick(){
        Intent intent=new Intent();
        intent.putExtra(SPEED_KEY,selectedSpeed);
        intent.putExtra(MUSCI_KEY,selectedMusic);
        setResult(RESULT_OK,intent);//返回数据给上一个活动
        finish();
    }
}
