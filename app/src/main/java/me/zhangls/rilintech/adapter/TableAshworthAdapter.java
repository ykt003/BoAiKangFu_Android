package me.zhangls.rilintech.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import java.util.List;

import me.zhangls.rilintech.R;


/**
 * Created by YANG on 15/9/22.
 */
public class TableAshworthAdapter extends BaseAdapter{

    private Context context;
    /**
     * 部位
     */
    private List<String> parts;
    /**
     * 左侧得分
     */
    private List<String> lefts;
    /**
     * 右侧得分
     */
    private List<String> rights;

    public TableAshworthAdapter (Context context,List<String> parts,List<String> lefts,List<String> rights){
        this.context = context;
        this.parts = parts;
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
            convertView = View.inflate(context, R.layout.item_table_ashworth, null);
            holder.part = (TextView) convertView.findViewById(R.id.part);
            holder.left = (TextView) convertView.findViewById(R.id.left);
            holder.right = (TextView) convertView.findViewById(R.id.right);
            convertView.setTag(holder);
        }else{
            holder = (Holder) convertView.getTag();
        }
        try {
            if(parts.size()>0 && lefts.size()>0 && rights.size()>0){
                holder.part.setText(parts.get(position));
                holder.left.setText(lefts.get(position));
                holder.right.setText(rights.get(position));
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        return convertView;
    }

    private static class Holder{
        TextView part;
        TextView left;
        TextView right;
    }

}
