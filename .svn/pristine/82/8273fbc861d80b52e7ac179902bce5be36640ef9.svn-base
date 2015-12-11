/*
 * Copyright (c) 2014, 青岛司通科技有限公司 All rights reserved.
 * File Name：OtherAdapter.java
 * Version：V1.0
 * Author：zhaokaiqiang
 * Date：2015-1-4
 */

package com.zhangls.zlistview;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhangls.zlistview.adapter.BaseSwipeAdapter;
import com.zhangls.zlistview.enums.DragEdge;
import com.zhangls.zlistview.enums.ShowMode;
import com.zhangls.zlistview.listener.SimpleSwipeListener;
import com.zhangls.zlistview.widget.ZSwipeItem;

import java.util.List;

import me.zhangls.rilintech.R;
import me.zhangls.rilintech.activity.PatientInfoAddActivity;
import me.zhangls.rilintech.manager.PatientInfoManager;
import me.zhangls.rilintech.model.MedicalRecord;
import me.zhangls.rilintech.model.PatientInfo;

public class ListViewAdapter extends BaseSwipeAdapter {

	protected static final String TAG = "ListViewAdapter";

	private Activity context;
	private List mList;
	private int mTag;
	private PatientInfo mPatientInfo;
	private MedicalRecord mMedicalRecord;
	private PatientInfoManager patientInfoManager;

	public ListViewAdapter(Activity context,List list,int tag) {
		this.context = context;
		mList=list;
		mTag=tag;
	}

	@Override
	public int getCount() {
		return mList.size();
	}

	@Override
	public Object getItem(int position) {
		return mList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public int getSwipeLayoutResourceId(int position) {
		return R.id.swipe_item;
	}

	@Override
	public View generateView(int position, ViewGroup parent) {

		return context.getLayoutInflater().inflate(R.layout.item_listview,
				parent, false);
	}

	@Override
	public void fillValues(final int position, final View convertView) {
		final ZSwipeItem swipeItem = (ZSwipeItem) convertView
				.findViewById(R.id.swipe_item);
		TextView text_open = (TextView) convertView.findViewById(R.id.swipe_item_textview);
		ImageView image_delete = (ImageView) convertView.findViewById(R.id.swipe_item_imageview);

		TextView tv1 = (TextView) convertView.findViewById(R.id.tv1);
		TextView tv2= (TextView) convertView.findViewById(R.id.tv2);
		TextView tv3= (TextView) convertView.findViewById(R.id.tv3);
		if(mTag==0){
			mPatientInfo= (PatientInfo) mList.get(position);
			if(mPatientInfo.getmP_bah()==-1){
			    tv1.setText("");
			}else{
				tv1.setText(""+mPatientInfo.getmP_bah());
			}
			tv2.setText( mPatientInfo.getmName());
			tv3.setText( mPatientInfo.getmSex());
		}
		if(mTag==1){
			mMedicalRecord= (MedicalRecord) mList.get(position);
			tv1.setText(""+mMedicalRecord.getmHospitalRoomRecord());
			tv2.setText(mMedicalRecord.getmHospitalDepartment());
			tv3.setText(mMedicalRecord.getmHospitalRecoveryDiagnosis());
		}


		if (position % 4 == 1) {
			swipeItem.setShowMode(ShowMode.PullOut);
			swipeItem.setDragEdge(DragEdge.Right);
		} else if (position % 4 == 2) {
			swipeItem.setShowMode(ShowMode.LayDown);
			swipeItem.setDragEdge(DragEdge.Right);
		} else if (position % 4 == 3) {
			swipeItem.setShowMode(ShowMode.PullOut);
			//swipeItem.setDragEdge(DragEdge.Left);
			swipeItem.setDragEdge(DragEdge.Right);
		} else if (position % 4 == 0) {
			swipeItem.setShowMode(ShowMode.LayDown);
			//swipeItem.setDragEdge(DragEdge.Left);
			swipeItem.setDragEdge(DragEdge.Right);
		}

		swipeItem.addSwipeListener(new SimpleSwipeListener() {
			@Override
			public void onOpen(ZSwipeItem layout) {
				Log.d(TAG, "打开:" + position);
			}

			@Override
			public void onClose(ZSwipeItem layout) {
				Log.d(TAG, "关闭:" + position);
			}

			@Override
			public void onStartOpen(ZSwipeItem layout) {
				Log.d(TAG, "准备打开:" + position);
			}

			@Override
			public void onStartClose(ZSwipeItem layout) {
				Log.d(TAG, "准备关闭:" + position);
			}

			@Override
			public void onHandRelease(ZSwipeItem layout, float xvel, float yvel) {
				Log.d(TAG, "手势释放");
			}

			@Override
			public void onUpdate(ZSwipeItem layout, int leftOffset,
								 int topOffset) {
				Log.d(TAG, "位置更新");
			}
		});

		image_delete .setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (mTag == 0) {
					PatientInfo patientInfo = (PatientInfo) mList.get(position);
					delete(patientInfo);
					//mPatientInfoListAdpter.remove(position);
					mList.remove(position);
					notifyDataSetChanged();
					swipeItem.close();
				}


			}
		});
		text_open .setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				if (mTag == 0) {
					swipeItem.close();
					PatientInfo patientInfo = (PatientInfo) mList.get(position);
							try {
								Thread.sleep(300);
								open(patientInfo);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
				}

			}
		});
	}
	private void delete(PatientInfo patientInfo) {
		patientInfoManager = new PatientInfoManager(context);
		patientInfoManager.openDataBase();
		patientInfoManager.deletePatientInfoFromUUID(patientInfo.getmPatient_uuid());
		patientInfoManager.closeDataBase();

	}
	private void open(PatientInfo patientInfo) {

		String patient_uuid = patientInfo.getmPatient_uuid();
		Intent intent = new Intent(context, PatientInfoAddActivity.class);
		intent.putExtra("patient_uuid", patient_uuid);
		context.startActivity(intent);
		//context.finish();
	}


}
