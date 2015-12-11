package me.zhangls.rilintech.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import me.zhangls.rilintech.R;
import me.zhangls.rilintech.utils.L;

/**
 * Created by YANG on 15/11/27.
 */
public class TableNervousSystemIndexAdapter extends BaseAdapter {

    private Context context;
    //项目
    private List<String> projects;
    //部位
    private List<String> parts;
    //左侧主动得分
    private List<String> init_lefts;
    //右侧主动得分
    private List<String> init_rights;

    public TableNervousSystemIndexAdapter(Context context, List<String> projects, List<String> parts,
                                          List<String> init_lefts, List<String> init_rights) {
        this.context = context;
        this.projects = projects;
        this.parts = parts;
        this.init_lefts = init_lefts;
        this.init_rights = init_rights;

    }

    @Override
    public int getCount() {
        return projects.size();
    }

    @Override
    public Object getItem(int position) {
        return projects.get(position);
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
            convertView = View.inflate(context, R.layout.item_listview_nervous_system_index, null);
            holder.project = (TextView) convertView.findViewById(R.id.project);
            holder.part = (TextView) convertView.findViewById(R.id.part);
            holder.init_left = (TextView) convertView.findViewById(R.id.init_left);
            holder.init_right = (TextView) convertView.findViewById(R.id.init_right);
            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }

        holder.part.setText(parts.get(position));
        holder.project.setText(projects.get(position));
        try {
            holder.init_left.setText(init_lefts.get(position));
        } catch (Exception e) {
            e.printStackTrace();
            holder.init_left.setText("");
        }
        try {
            holder.init_right.setText(init_rights.get(position));
        } catch (Exception e) {
            e.printStackTrace();
            holder.init_right.setText("");
        }

        return convertView;
    }


    private static class Holder {
        TextView part;
        TextView project;
        TextView init_left;
        TextView init_right;
    }
}
