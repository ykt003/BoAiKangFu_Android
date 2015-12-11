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
import me.zhangls.rilintech.utils.L;

/**
 * Created by YANG on 15/9/24.
 */
public class TableRom2Adapter extends BaseAdapter {

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
    //定义一个HashMap，用来存放EditText的值，Key是position
    private static HashMap<Integer, String> init_lefts_hashMap = new HashMap<Integer, String>();
    private static HashMap<Integer, String> passive_lefts_hashMap = new HashMap<Integer, String>();
    private static HashMap<Integer, String> init_rights_hashMap = new HashMap<Integer, String>();
    private static HashMap<Integer, String> passive_rights_hashMap = new HashMap<Integer, String>();


    public TableRom2Adapter(Context context, List<String> parts, List<String> actions,
                            List<String> init_lefts, List<String> passive_lefts,
                            List<String> init_rights, List<String> passive_rights) {
        this.context = context;
        this.parts = parts;
        this.actions = actions;
        this.init_lefts = init_lefts;
        this.passive_lefts = passive_lefts;
        this.init_rights = init_rights;
        this.passive_rights = passive_rights;

        init();

    }

    private void init() {
        init_lefts_hashMap.clear();
        passive_lefts_hashMap.clear();
        init_rights_hashMap.clear();
        passive_rights_hashMap.clear();

        for (int i = 0; i < init_lefts.size(); i++) {
            init_lefts_hashMap.put(i, init_lefts.get(i));
        }
        for (int i = 0; i < passive_lefts.size(); i++) {
            passive_lefts_hashMap.put(i, passive_lefts.get(i));
        }
        for (int i = 0; i < init_rights.size(); i++) {
            init_rights_hashMap.put(i, init_rights.get(i));
        }
        for (int i = 0; i < passive_rights.size(); i++) {
            passive_rights_hashMap.put(i, passive_rights.get(i));
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
        convertView = View.inflate(context, R.layout.item_table_rom2, null);
        holder.part = (TextView) convertView.findViewById(R.id.part);
        holder.action = (TextView) convertView.findViewById(R.id.action);
        holder.init_left = (EditText) convertView.findViewById(R.id.init_left);
        holder.passive_left = (EditText) convertView.findViewById(R.id.passive_left);
        holder.init_right = (EditText) convertView.findViewById(R.id.init_right);
        holder.passive_right = (EditText) convertView.findViewById(R.id.passive_right);

        //   convertView.setTag(holder);
        // } else {
        //   holder = (Holder) convertView.getTag();
        // }

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

        holder.init_left.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                //将editText中改变的值设置的HashMap中
                init_lefts_hashMap.put(position, s.toString());

            }
        });
        holder.passive_left.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                //将editText中改变的值设置的HashMap中
                passive_lefts_hashMap.put(position, s.toString());

            }
        });
        holder.init_right.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                //将editText中改变的值设置的HashMap中
                init_rights_hashMap.put(position, s.toString());

            }
        });
        holder.passive_right.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                //将editText中改变的值设置的HashMap中
                passive_rights_hashMap.put(position, s.toString());

            }
        });

        //如果hashMap不为空，就设置的editText
        if (init_lefts_hashMap.get(position) != null) {
            holder.init_left.setText(init_lefts_hashMap.get(position));
        }
        if (passive_lefts_hashMap.get(position) != null) {
            holder.passive_left.setText(passive_lefts_hashMap.get(position));
        }
        if (init_rights_hashMap.get(position) != null) {
            holder.init_right.setText(init_rights_hashMap.get(position));
        }
        if (passive_rights_hashMap.get(position) != null) {
            holder.passive_right.setText(passive_rights_hashMap.get(position));
        }


        return convertView;
    }

    public static ArrayList<HashMap<Integer, String>> getMap() {
        ArrayList<HashMap<Integer, String>> list = new ArrayList<>();
        list.add(init_lefts_hashMap);
        list.add(passive_lefts_hashMap);
        list.add(init_rights_hashMap);
        list.add(passive_rights_hashMap);

        return list;
    }


    private static class Holder {
        TextView part;
        TextView action;
        EditText init_left;
        EditText passive_left;
        EditText init_right;
        EditText passive_right;
    }

}
