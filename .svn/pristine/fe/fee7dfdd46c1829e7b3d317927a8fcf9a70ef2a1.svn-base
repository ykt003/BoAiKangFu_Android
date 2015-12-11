package me.zhangls.rilintech.adapter;


import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import me.zhangls.rilintech.R;

public class TableMoreAdapter extends BaseAdapter {

	private Context context;
	//private String[] text_list;
	private List<String> text_list;
	private int position = 0;
	Holder hold;

	//public TableMoreAdapter(Context context, String[] text_list) {
	public TableMoreAdapter(Context context, List<String> text_list) {
		this.context = context;
		this.text_list = text_list;
	}

	public int getCount() {
		return text_list.size();
		//return text_list.length;
	}

	public Object getItem(int position) {
		return text_list.get(position);
		//return text_list[position];
	}

	public long getItemId(int position) {

		return position;
	}

	public View getView(int arg0, View view, ViewGroup viewGroup) {

		if (view == null) {
			view = View.inflate(context, R.layout.item_table_morelist, null);
			hold = new Holder(view);
			view.setTag(hold);
		} else {
			hold = (Holder) view.getTag();
		}
		//hold.txt.setText(text_list[arg0]);
		hold.txt.setText(text_list.get(arg0));
		hold.txt.setTextColor(0xFF666666);
		if (arg0 == position) {
			hold.txt.setTextColor(0xFF56abe4);//56abe4   0xFF666666
		}
		return view;
	}

	public void setSelectItem(int position) {

		this.position = position;
	}

	private static class Holder {
		TextView txt;

		public Holder(View view) {
			txt = (TextView) view.findViewById(R.id.moreitem_txt);
		}
	}
}
