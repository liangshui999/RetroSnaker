package com.example.asus_cp.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.asus_cp.adapter.RecordAdapter;
import com.example.asus_cp.model.ScoreRecord;
import com.example.asus_cp.service.RecordService;

import java.util.List;

/**
 * Created by asus-cp on 2016-04-14.
 */
public class ScoreRecordActivity extends Activity {
    private RecordService recordService;
    private List<ScoreRecord>records;
    private RecordAdapter adapter;
    private ListView listView;
    private Toolbar toolbar;
    private ImageView deleteImageView;

    //position的key
    public static final String TIME_KEY ="positionKey";

    //页面索引
    private int pageIndex;

    //每页条数
    private int num;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.score_record_layout);
        num=20;
        listView= (ListView) findViewById(R.id.list_score_record);
//        deleteImageView= (ImageView) LayoutInflater.from(this).inflate(R.layout.record_item_layout,null)
//                .findViewById(R.id.img_delete);
        toolbar= (Toolbar) findViewById(R.id.bar_score);
        //toolbar.setLogo(R.mipmap.tubiao);
        toolbar.setTitle("游戏记录");
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setOverflowIcon(getResources().getDrawable(R.mipmap.ic_menu_more_overflow));
        toolbar.inflateMenu(R.menu.record_menu);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menu_delete:
                        //Toast.makeText(ScoreRecordActivity.this, "点击了删除按钮", Toast.LENGTH_SHORT).show();
                        adapter=new RecordAdapter(ScoreRecordActivity.this,records,true);
                        listView.setAdapter(adapter);
                        break;
                    case R.id.menu_clear:
                        //Toast.makeText(ScoreRecordActivity.this, "点击了清空按钮", Toast.LENGTH_SHORT).show();
                        recordService.deleteAllRecord();
                        recordService.deleteAllScore();
                        records.clear();
                        adapter=new RecordAdapter(ScoreRecordActivity.this,records);
                        listView.setAdapter(adapter);
                        break;
                }
                return true;
            }
        });

        recordService=new RecordService();
        records=recordService.queryPageRecords(pageIndex,num);
        adapter=new RecordAdapter(this,records);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent();
                intent.putExtra(TIME_KEY, records.get(position).getTime());
                setResult(RESULT_OK, intent);//返回数据给上一个活动
                finish();
            }
        });
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
//                ImageView imageView= (ImageView) view.findViewById(R.id.img_delete);
//                imageView.setVisibility(View.VISIBLE);//将隐藏的删除图标显示出来
//                imageView.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        Toast.makeText(ScoreRecordActivity.this, "点击了删除按钮", Toast.LENGTH_SHORT).show();
//                    }
//                });
                adapter=new RecordAdapter(ScoreRecordActivity.this,records,true);
                listView.setAdapter(adapter);
                return true;
            }
        });
        LayoutInflater inflater=LayoutInflater.from(this);
        View v=inflater.inflate(R.layout.foot_view_layout,null);
        listView.addFooterView(v);//给listview加个"加载更多"
        Button moreButton= (Button) v.findViewById(R.id.text_more);
        moreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                records.addAll(recordService.queryPageRecords(++pageIndex, num));
                adapter.notifyDataSetChanged();
            }
        });
    }

}
