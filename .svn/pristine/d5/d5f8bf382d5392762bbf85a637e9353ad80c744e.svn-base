package me.zhangls.rilintech.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import java.util.List;
import me.zhangls.rilintech.R;

/**
 * Created by YANG on 15/10/12.
 */
public class TableGmfmAdapter extends BaseAdapter {

    private Context context;
    //动作
    private List<String> actions;
    //得分
    private List<Integer> list_score;

    public TableGmfmAdapter(Context context, List<String> actions, List<Integer> list_score) {
        this.context = context;
        this.actions = actions;
        this.list_score = list_score;

    }

    @Override
    public int getCount() {
        return actions.size();
    }

    @Override
    public Object getItem(int position) {
        return actions.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder holder = null;
        if (convertView == null) {
            holder = new Holder();
            convertView = View.inflate(context, R.layout.item_table_gmfm, null);
            holder.action = (TextView) convertView.findViewById(R.id.project_name);
            holder.tv_score = (TextView) convertView.findViewById(R.id.project_score);
            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }

        holder.action.setText(actions.get(position).trim());
        try {
            holder.tv_score.setText(list_score.get(position)+"");
        } catch (Exception e) {
            e.printStackTrace();
            holder.tv_score.setText("");
        }

        return convertView;
    }


    private static class Holder {
        TextView action;
        TextView tv_score;
    }
}
