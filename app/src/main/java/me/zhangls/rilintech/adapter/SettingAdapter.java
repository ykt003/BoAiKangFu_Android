package me.zhangls.rilintech.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import me.zhangls.rilintech.R;

/**
 * Created by YANG on 15/12/7.
 */
public class SettingAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<String> list_left;
    private ArrayList<String> list_right;
    private LayoutInflater inflater;

    public SettingAdapter(Context context, ArrayList<String> list_left, ArrayList<String> list_right) {
        this.context = context;
        this.list_left = list_left;
        this.list_right = list_right;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return list_left.size();
    }

    @Override
    public Object getItem(int position) {
        return list_left.get(position);
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
            convertView = inflater.inflate(R.layout.item_setting_listview, null);
            holder.left_content_tv = (TextView) convertView.findViewById(R.id.left_content);
            holder.right_content_tv = (TextView) convertView.findViewById(R.id.right_content);

            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.left_content_tv.setText(list_left.get(position));
        holder.right_content_tv.setText(list_right.get(position));

        return convertView;
    }


    private class ViewHolder {
        private TextView left_content_tv;
        private TextView right_content_tv;
    }
}
