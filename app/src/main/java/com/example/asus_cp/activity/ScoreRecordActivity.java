package com.example.asus_cp.activity;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
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

    //页面索引
    private int pageIndex;

    //每页条数
    private int num;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        num=20;
        listView=getListView();
        recordService=new RecordService();
        records=recordService.queryPageRecords(pageIndex,num);
        adapter=new RecordAdapter(this,records);
        setListAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent();
                intent.putExtra(TIME_KEY, records.get(position).getTime());
                setResult(RESULT_OK, intent);//返回数据给上一个活动
                finish();
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
