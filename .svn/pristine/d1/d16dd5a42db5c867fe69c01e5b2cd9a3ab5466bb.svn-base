package me.zhangls.rilintech.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import me.zhangls.rilintech.R;
import me.zhangls.rilintech.model.NormalTableFinalModel;
import me.zhangls.rilintech.utils.L;

/**
 * Created by YANG on 15/11/23.
 */
public class TableIndexNormalAdapter extends BaseAdapter {

    private Context mContext;
    private LayoutInflater inflater;
    private ArrayList<NormalTableFinalModel>list_final_model;

    public TableIndexNormalAdapter(Context context,ArrayList<NormalTableFinalModel>list_final_model){
        this.mContext = context;
        this.list_final_model = list_final_model;
        this.inflater = LayoutInflater.from(context);

    }

    @Override
    public int getCount() {
        return list_final_model.size();
    }

    @Override
    public Object getItem(int position) {
        return list_final_model.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder = null;
        if (convertView == null){
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.item_app_index_listview_layout,null);
            holder.tv_value1 = (TextView) convertView.findViewById(R.id.value1);
            holder.tv_value2 = (TextView) convertView.findViewById(R.id.value2);
            holder.tv_value3 = (TextView) convertView.findViewById(R.id.value3);

            convertView.setTag(holder);
        }else {

            holder = (ViewHolder) convertView.getTag();

        }

        holder.tv_value1.setText(nullToStr(
                list_final_model.get(position).getList_model().get(0).getMiddleText()));
        holder.tv_value2.setText(nullToStr(
                list_final_model.get(position).getList_model().get(1).getMiddleText()));
        holder.tv_value3.setText(nullToStr(
                list_final_model.get(position).getList_model().get(2).getMiddleText()));

        return convertView;
    }


    class ViewHolder{
        TextView tv_value1;
        TextView tv_value2;
        TextView tv_value3;
    }

    /**
     * 将字符串 “null”转化为空字符串“”
     * @param str
     * @return
     */
    private String nullToStr(String str){
        String s = "";
        if (str.equals("null")){
            s = "";
        }else {
            s = str;
        }
        return  s;
    }
}
