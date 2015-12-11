package me.zhangls.rilintech.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import me.zhangls.rilintech.R;

/**
 * Created by YANG on 15/9/8.
 */
public class TableMmtAdapter extends BaseAdapter {
    private Context context;
    /**
     * 部位
     */
    private List<String> parts;
    /**
     * 动作
     */
    private List<String> actions;
    /**
     * 左侧得分
     */
    private List<String> lefts;
    /**
     * 右侧得分
     */
    private List<String> rights;

    public TableMmtAdapter (Context context,List<String> parts,List<String> actions,List<String> lefts,List<String> rights){
        this.context = context;
        this.parts = parts;
        this.actions = actions;
        this.lefts = lefts;
        this.rights = rights;

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
        if(convertView == null){
            holder = new Holder();
            convertView = View.inflate(context, R.layout.item_table_mmt, null);
            holder.part = (TextView) convertView.findViewById(R.id.part);
            holder.action = (TextView) convertView.findViewById(R.id.action);
            holder.left = (TextView) convertView.findViewById(R.id.left);
            holder.right = (TextView) convertView.findViewById(R.id.right);
            convertView.setTag(holder);
        }else{
            holder = (Holder) convertView.getTag();
        }
        if(parts.size()>0 && actions.size()>0 && lefts.size()>0 && rights.size()>0){
            holder.part.setText(parts.get(position));
            holder.action.setText(actions.get(position));
            holder.left.setText(lefts.get(position));
            holder.right.setText(rights.get(position));
        }


        return convertView;
    }



    private static class Holder{
        TextView part;
        TextView action;
        TextView left;
        TextView right;
    }
}
