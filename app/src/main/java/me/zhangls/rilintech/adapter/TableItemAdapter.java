package me.zhangls.rilintech.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import me.zhangls.rilintech.R;
import me.zhangls.rilintech.model.MedicalRecord;
import me.zhangls.rilintech.model.PatientInfo;

/**
 * Created by zsn on 2015/8/4.
 */
public class TableItemAdapter extends BaseAdapter {

    private List mList;
    private Context mContext;
    private PatientInfo mPatientInfo;
    private MedicalRecord mMedicalRecord;
    private int mTag;
    public TableItemAdapter(Context pContext,List pList,int tag){
        mContext=pContext;
        mList=pList;
        mTag=tag;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int i) {
        return mList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View _view, ViewGroup viewGroup) {
        Holder _holder=null;
        if(_view==null){
            _view=LayoutInflater.from(mContext).inflate(
                    R.layout.item_common_use_info_list, null);
            _holder=new Holder();
            _holder.tv1= (TextView) _view.findViewById(R.id.tv1);
            _holder.tv2= (TextView) _view.findViewById(R.id.tv2);
            _holder.tv3= (TextView) _view.findViewById(R.id.tv3);
            _view.setTag(_holder);
        }else{
            _holder=(Holder)_view.getTag();
        }

        if(mTag==0){
           mPatientInfo= (PatientInfo) mList.get(position);
            Log.i("log", "PatientInfo=" + ((PatientInfo) mList.get(0)).getmP_bah());
            if(mPatientInfo.getmP_bah()==-1){
                _holder.tv1.setText("");
            }else{
                _holder.tv1.setText(""+mPatientInfo.getmP_bah());
            }
            _holder.tv2.setText( mPatientInfo.getmName());
            _holder.tv3.setText( mPatientInfo.getmSex());
        }
        if(mTag==1){
            mMedicalRecord= (MedicalRecord) mList.get(position);
            _holder.tv1.setText(""+mMedicalRecord.getmHospitalRoomRecord());
            _holder.tv2.setText(mMedicalRecord.getmHospitalDepartment());
            _holder.tv3.setText(mMedicalRecord.getmHospitalRecoveryDiagnosis());
        }

        return _view;
    }



    private class Holder{
        TextView tv1,tv2,tv3;
    }
}
