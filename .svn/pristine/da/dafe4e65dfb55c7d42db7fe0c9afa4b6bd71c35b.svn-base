package me.zhangls.rilintech.adapter;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import me.zhangls.rilintech.R;

/**
 * Created by YANG on 15/11/27.
 */
public class TableNervousSystemInfoAdapter extends BaseAdapter {

    private Context context;
    //部位
    private List<String> projects;
    //动作
    private List<String> parts;
    //左侧主动得分
    private List<String> init_lefts;
    //右侧主动得分
    private List<String> init_rights;
    //定义一个HashMap，用来存放EditText的值，Key是position
    private static HashMap<Integer, String> init_lefts_hashMap = new HashMap<Integer, String>();
    private static HashMap<Integer, String> init_rights_hashMap = new HashMap<Integer, String>();


    public TableNervousSystemInfoAdapter(Context context, List<String> projects, List<String> parts,
                                         List<String> init_lefts, List<String> init_rights) {
        this.context = context;
        this.projects = projects;
        this.parts = parts;
        this.init_lefts = init_lefts;
        this.init_rights = init_rights;

        init();

    }

    private void init() {
        init_lefts_hashMap.clear();
        init_rights_hashMap.clear();

        for (int i = 0; i < init_lefts.size(); i++) {
            init_lefts_hashMap.put(i, init_lefts.get(i));
        }
        for (int i = 0; i < init_rights.size(); i++) {
            init_rights_hashMap.put(i, init_rights.get(i));
        }

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
    public View getView(final int position, View convertView, ViewGroup parent) {
        Holder holder = null;
        //if (convertView == null) {
        holder = new Holder();
        convertView = View.inflate(context, R.layout.item_listview_nervous_system_info, null);
        holder.project = (TextView) convertView.findViewById(R.id.project);
        holder.part = (TextView) convertView.findViewById(R.id.part);
        holder.init_left = (EditText) convertView.findViewById(R.id.init_left);
        holder.init_left.requestFocus();
        holder.init_right = (EditText) convertView.findViewById(R.id.init_right);
        holder.init_right.requestFocus();

        /*    convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }*/

        holder.project.setText(projects.get(position));
        holder.part.setText(parts.get(position));

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

        holder.init_left.addTextChangedListener(new MyTextWatcher(1, position));
        holder.init_right.addTextChangedListener(new MyTextWatcher(2, position));

        //如果hashMap不为空，就设置的editText
        if (init_lefts_hashMap.get(position) != null) {
            holder.init_left.setText(init_lefts_hashMap.get(position));
        }
        if (init_rights_hashMap.get(position) != null) {
            holder.init_right.setText(init_rights_hashMap.get(position));
        }

        return convertView;
    }

    public static ArrayList<HashMap<Integer, String>> getMap() {
        ArrayList<HashMap<Integer, String>> list = new ArrayList<>();
        list.add(init_lefts_hashMap);
        list.add(init_rights_hashMap);

        return list;
    }

    private static class Holder {
        TextView project;
        TextView part;
        EditText init_left;
        EditText init_right;
    }

    /**
     * 监听
     */
    private class MyTextWatcher implements TextWatcher {

        private int i;
        private int position;

        public MyTextWatcher(int i, int position) {
            this.i = i;
            this.position = position;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (i == 1) {
                init_lefts_hashMap.put(position, s.toString());
            } else {
                init_rights_hashMap.put(position, s.toString());
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    }
}
