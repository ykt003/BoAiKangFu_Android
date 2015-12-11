package me.zhangls.rilintech.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import me.zhangls.rilintech.R;

public class NewsFragment extends Fragment {

	private View parentView;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		parentView = inflater.inflate(R.layout.fragment_news, container, false);

		return parentView;
	}

}
