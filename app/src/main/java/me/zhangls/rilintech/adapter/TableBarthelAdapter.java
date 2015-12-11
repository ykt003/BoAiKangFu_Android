package me.zhangls.rilintech.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import me.zhangls.rilintech.R;

/**
 * Created by YANG on 15/9/16.
 */
public class TableBarthelAdapter extends BaseAdapter{

    private Context context;
    /**
     * 题目集合
     */
    private List<String> titles;
    /**
     * 分数集合
     */
    private List<Integer> scores;

    public TableBarthelAdapter(Context context, List<String> titles, List<Integer> scores){
        this.context = context;
        this.titles = titles;
        this.scores = scores;
    }

    @Override
    public int getCount() {
        return titles.size();
    }

    @Override
    public Object getItem(int position) {
        return titles.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder holder = null;
        if(convertView == null){
            holder = new Holder();
            convertView = View.inflate(context, R.layout.item_table_barthel, null);
            holder.title = (TextView) convertView.findViewById(R.id.project_name);
            holder.score = (TextView) convertView.findViewById(R.id.project_scores);
            convertView.setTag(holder);
        }else{
            holder = (Holder) convertView.getTag();
        }
        if(titles.size()>0 && scores.size()>0){
            holder.title.setText(titles.get(position));
            holder.score.setText(scores.get(position)+"");
        }


        return convertView;
    }


    private static class Holder{
        TextView title;
        TextView score;
    }
}
