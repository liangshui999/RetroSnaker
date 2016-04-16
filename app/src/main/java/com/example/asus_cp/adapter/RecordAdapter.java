package com.example.asus_cp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.asus_cp.activity.R;
import com.example.asus_cp.model.ScoreRecord;
import com.example.asus_cp.service.RecordService;

import java.util.List;

/**
 * Created by asus-cp on 2016-04-14.
 */
public class RecordAdapter extends BaseAdapter {
    private Context context;
    private LayoutInflater inflater;
    private List<ScoreRecord>records;
    private boolean isVisble;


    public RecordAdapter(Context context,List<ScoreRecord> records,boolean isVisble) {
        this.context = context;
        inflater=LayoutInflater.from(context);
        this.records = records;
        this.isVisble=isVisble;
    }
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
    public View getView(final int position, final View convertView, final ViewGroup parent) {
        View view=convertView;
        ViewHolder viewHolder=null;
        if(view==null){
            view=inflater.inflate(R.layout.record_item_layout,null);
            viewHolder=new ViewHolder();
            viewHolder.scoreTextView= (TextView) view.findViewById(R.id.text_item_score);
            viewHolder.dateTextView= (TextView) view.findViewById(R.id.text_item_date);
            viewHolder.deleteImageView= (ImageView) view.findViewById(R.id.img_delete);
            view.setTag(viewHolder);
        }else{
            viewHolder= (ViewHolder) view.getTag();
        }
        if(records.size()>0){
            viewHolder.scoreTextView.setText(records.get(position).getScore()+"");
            viewHolder.dateTextView.setText(records.get(position).getDate());
        }
        //是否让删除按钮显示出来
        if(isVisble){
            viewHolder.deleteImageView.setVisibility(View.VISIBLE);
            viewHolder.deleteImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(records.size()>0){
                        //将数据库中对应的记录删除
                        int time=records.get(position).getTime();
                        RecordService recordService=new RecordService();
                        recordService.deleteScore(time);
                        recordService.deleteRecord(time);
                        records.remove(position);
                        //getView(position,convertView,parent);//删除数据之后重新调用一次
                        notifyDataSetChanged();//删除数据之后要通知更新ui
                    }

                }
            });
        }
        return view;
    }

    class ViewHolder {
        private TextView scoreTextView;
        private TextView dateTextView;
        private ImageView deleteImageView;
    }

}
