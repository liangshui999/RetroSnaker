package com.example.asus_cp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.asus_cp.activity.R;
import com.example.asus_cp.model.ScoreRecord;

import java.util.List;

/**
 * Created by asus-cp on 2016-04-14.
 */
public class RecordAdapter extends BaseAdapter {
    private Context context;
    private LayoutInflater inflater;
    private List<ScoreRecord>records;

    public RecordAdapter(Context context,List<ScoreRecord> records) {
        this.context = context;
        inflater=LayoutInflater.from(context);
        this.records = records;
    }

    public RecordAdapter(){}
    @Override
    public int getCount() {
        return records.size();
    }

    @Override
    public Object getItem(int position) {
        return records.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view=convertView;
        ViewHolder viewHolder=null;
        if(view==null){
            view=inflater.inflate(R.layout.record_item_layout,null);
            viewHolder=new ViewHolder();
            viewHolder.scoreTextView= (TextView) view.findViewById(R.id.text_item_score);
            viewHolder.dateTextView= (TextView) view.findViewById(R.id.text_item_date);
            view.setTag(viewHolder);
        }else{
            viewHolder= (ViewHolder) view.getTag();
        }
        viewHolder.scoreTextView.setText(records.get(position).getScore()+"");
        viewHolder.dateTextView.setText(records.get(position).getDate());
        return view;
    }

    class ViewHolder {
        private TextView scoreTextView;
        private TextView dateTextView;
    }

}
