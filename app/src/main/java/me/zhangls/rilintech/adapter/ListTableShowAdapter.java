package me.zhangls.rilintech.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import me.zhangls.rilintech.R;
import me.zhangls.rilintech.model.TableListViewShowModel;

/**
 * Created by YANG on 15/12/1.
 */
public class ListTableShowAdapter extends BaseAdapter {

    private Context mContext;
    private ArrayList<TableListViewShowModel> list;
    private LayoutInflater inflater;

    public ListTableShowAdapter(Context context, ArrayList<TableListViewShowModel> list) {
        this.mContext = context;
        this.list = list;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder = null;

        if (convertView == null) {

            holder = new ViewHolder();

            convertView = inflater.inflate(R.layout.item_table_listview_show, null);

            holder.textView = (TextView) convertView.findViewById(R.id.tv_content);
            holder.imageView = (ImageView) convertView.findViewById(R.id.iv_yes);

            convertView.setTag(holder);


        } else {

            holder = (ViewHolder) convertView.getTag();
        }

        holder.textView.setText(list.get(position).getName());
        if (1 == list.get(position).getFlag()) {//有数据
            holder.imageView.setVisibility(View.VISIBLE);
        } else {//无数据
            holder.imageView.setVisibility(View.INVISIBLE);
        }

        return convertView;
    }


    private class ViewHolder {
        TextView textView;
        ImageView imageView;
    }
}
