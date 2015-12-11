package me.zhangls.rilintech.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import me.zhangls.rilintech.R;

/**
 * Created by YANG on 15/9/24.
 */
public class TableRomAdapter extends BaseAdapter {

    private Context context;
    //部位
    private List<String> parts;
    //动作
    private List<String> actions;
    //左侧主动得分
    private List<String> init_lefts;
    //左侧被动得分
    private List<String> passive_lefts;
    //右侧被动得分
    private List<String> passive_rights;
    //右侧主动得分
    private List<String> init_rights;

    public TableRomAdapter(Context context, List<String> parts, List<String> actions,
                           List<String> init_lefts, List<String> passive_lefts,
                           List<String> init_rights, List<String> passive_rights) {
        this.context = context;
        this.parts = parts;
        this.actions = actions;
        this.init_lefts = init_lefts;
        this.passive_lefts = passive_lefts;
        this.init_rights = init_rights;
        this.passive_rights = passive_rights;

    }

    @Override
    public int getCount() {
        return parts.size();
    }

    @Override
    public Object getItem(int position) {
        return parts.get(position);
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
            convertView = View.inflate(context, R.layout.item_table_rom, null);
            holder.part = (TextView) convertView.findViewById(R.id.part);
            holder.action = (TextView) convertView.findViewById(R.id.action);
            holder.init_left = (TextView) convertView.findViewById(R.id.init_left);
            holder.passive_left = (TextView) convertView.findViewById(R.id.passive_left);
            holder.init_right = (TextView) convertView.findViewById(R.id.init_right);
            holder.passive_right = (TextView) convertView.findViewById(R.id.passive_right);
            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }


        holder.part.setText(parts.get(position));
        holder.action.setText(actions.get(position));
        try {
            holder.init_left.setText(init_lefts.get(position));
        } catch (Exception e) {
            e.printStackTrace();
            holder.init_left.setText("");
        }
        try {
            holder.passive_left.setText(passive_lefts.get(position));
        } catch (Exception e) {
            e.printStackTrace();
            holder.passive_left.setText("");
        }
        try {
            holder.init_right.setText(init_rights.get(position));
        } catch (Exception e) {
            e.printStackTrace();
            holder.init_right.setText("");
        }
        try {
            holder.passive_right.setText(passive_rights.get(position));
        } catch (Exception e) {
            e.printStackTrace();
            holder.passive_right.setText("");
        }

        return convertView;
    }


    private static class Holder {
        TextView part;
        TextView action;
        TextView init_left;
        TextView passive_left;
        TextView init_right;
        TextView passive_right;
    }

}
