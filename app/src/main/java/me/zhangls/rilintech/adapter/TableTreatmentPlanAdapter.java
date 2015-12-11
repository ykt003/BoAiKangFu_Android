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
public class TableTreatmentPlanAdapter extends BaseAdapter {

    private Context context;
    /**
     * 次数
     */
    private List<String> counts;
    /**
     * 干预目的
     */
    private List<String> list_purposes;
    /**
     * 项目名称
     */
    private List<String> list_project_names;

    private int position = 0;


    public TableTreatmentPlanAdapter(Context context,List<String> counts,List<String> list_purposes,List<String> list_project_names){
        this.context = context;
        this.counts = counts;
        this.list_purposes = list_purposes;
        this.list_project_names = list_project_names;
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
            convertView = View.inflate(context, R.layout.item_table_treatment_plan, null);
            holder.rt_counts = (TextView) convertView.findViewById(R.id.rt_counts);
            holder.rt_main_problems = (TextView) convertView.findViewById(R.id.rt_main_problems);
            holder.rt_minor_problems = (TextView) convertView.findViewById(R.id.rt_minor_problems);
            convertView.setTag(holder);
        }else{
            holder = (Holder) convertView.getTag();
        }
        if(counts.size()>0 && list_purposes.size()>0 && list_project_names.size()>0){
            holder.rt_counts.setText(counts.get(position));
            holder.rt_main_problems.setText(list_purposes.get(position));
            holder.rt_minor_problems.setText(list_project_names.get(position));
        }

        return convertView;
    }

    private static class Holder{
        TextView rt_counts;
        TextView rt_main_problems;
        TextView rt_minor_problems;
    }
}
