package me.zhangls.rilintech.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import me.zhangls.rilintech.R;
import me.zhangls.rilintech.activity.TableRehabilitationGoalIndexActivity;

/**
 * Created by YANG on 15/9/10.
 */
public class TableRehabilitationGoalAdapter extends BaseAdapter {

    private Context context;
    /**
     * 次数
     */
    private List<String> counts;
    /**
     * 主要问题
     */
    private List<String> main_problems;
    /**
     * 次要问题
     */
    private List<String> minor_problems;


    public TableRehabilitationGoalAdapter(Context context,List<String> counts,List<String> main_problems,List<String> minor_problems){
        this.context = context;
        this.counts = counts;
        this.main_problems = main_problems;
        this.minor_problems = minor_problems;
    }


    @Override
    public int getCount() {
        return counts.size();
    }

    @Override
    public Object getItem(int position) {
        return counts.get(position);
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
            convertView = View.inflate(context, R.layout.item_table_rehabilitation_goal, null);
            holder.rt_counts = (TextView) convertView.findViewById(R.id.rt_counts);
            holder.rt_main_problems = (TextView) convertView.findViewById(R.id.rt_main_problems);
            holder.rt_minor_problems = (TextView) convertView.findViewById(R.id.rt_minor_problems);
            convertView.setTag(holder);
        }else{
            holder = (Holder) convertView.getTag();
        }
        if(counts.size()>0 && main_problems.size()>0 && minor_problems.size()>0){
            holder.rt_counts.setText(counts.get(position));
            holder.rt_main_problems.setText(main_problems.get(position));
            holder.rt_minor_problems.setText(minor_problems.get(position));
        }
        return convertView;
}

    private static class Holder{
        TextView rt_counts;
        TextView rt_main_problems;
        TextView rt_minor_problems;
    }
}
