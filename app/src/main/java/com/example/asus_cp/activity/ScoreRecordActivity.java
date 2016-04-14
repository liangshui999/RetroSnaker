package com.example.asus_cp.activity;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.asus_cp.adapter.RecordAdapter;
import com.example.asus_cp.model.ScoreRecord;
import com.example.asus_cp.service.RecordService;

import java.util.List;

/**
 * Created by asus-cp on 2016-04-14.
 */
public class ScoreRecordActivity extends ListActivity {
    private RecordService recordService;
    private List<ScoreRecord>records;
    private RecordAdapter adapter;
    private ListView listView;

    //position的key
    public static final String TIME_KEY ="positionKey";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        listView=getListView();
        recordService=new RecordService();
        records=recordService.queryAllRecords();
        adapter=new RecordAdapter(this,records);
        setListAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent();
                intent.putExtra(TIME_KEY,records.get(position).getTime());
                setResult(RESULT_OK, intent);//返回数据给上一个活动
                finish();
            }
        });
    }

}
