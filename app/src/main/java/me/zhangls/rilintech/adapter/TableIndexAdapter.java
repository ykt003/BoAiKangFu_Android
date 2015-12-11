package me.zhangls.rilintech.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;
import me.zhangls.rilintech.R;
import me.zhangls.rilintech.model.MedicalHistoryRecord;

/**
 * Created by rilintech on 15/8/4.
 */
public class TableIndexAdapter extends BaseAdapter {

    private Context context;
    private List<MedicalHistoryRecord> list = new ArrayList<>();
    private Holder hold;

    public TableIndexAdapter(Context context, List<MedicalHistoryRecord>list){
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        if (view == null){
            view = View.inflate(context,R.layout.item_patient_info_list, null);
            hold = new Holder(view);
            view.setTag(hold);
        }else{
            hold = (Holder)view.getTag();
        }

        MedicalHistoryRecord mhr = list.get(i);
        hold.txt1.setText(mhr.getMedical_history_date());
        hold.txt2.setText(mhr.getMedical_history_relator());
        hold.txt3.setText(mhr.getMedical_history_reliable());



        return view;
    }

    private static class Holder {
        TextView txt1, txt2, txt3;

        public Holder(View view) {
            txt1 = (TextView) view.findViewById(R.id.hospital_id);
            txt2 = (TextView) view.findViewById(R.id.patient_name);
            txt3 = (TextView) view.findViewById(R.id.patient_idcard);
        }
    }

}
