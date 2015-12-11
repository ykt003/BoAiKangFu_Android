package me.zhangls.rilintech.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Toast;

import java.util.List;

import me.zhangls.rilintech.R;
import me.zhangls.rilintech.view.ListItemDelete;

public class DeleteAdapter extends BaseAdapter {

	public static ListItemDelete itemDelete = null;
	private List<String> listDatas;
	private LayoutInflater mInflater;
	private Context context;

	public DeleteAdapter(Context context, List<String> listDatas) {
		mInflater = LayoutInflater.from(context);
		this.listDatas = listDatas;
		this.context = context;
	}

	@Override
	public int getCount() {
		return listDatas == null ? 0 : listDatas.size();
	}

	@Override
	public Object getItem(int position) {
		return listDatas.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = mInflater.inflate(R.layout.item_delete, null);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.btnDelete.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				showInfo("点击删除了");
			}
		});
		return convertView;
	}

	class ViewHolder {
		Button btnDelete;
	}

	private Toast mToast;

	public void showInfo(String text) {
		if (mToast == null) {
			mToast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
		} else {
			mToast.setText(text);
			mToast.setDuration(Toast.LENGTH_SHORT);
		}
		mToast.show();
	}

	public static void ItemDeleteReset() {
		if (itemDelete != null) {
			itemDelete.reSet();
		}
	}
}
