package com.awaitu.easymusic.fragment;


import com.awaitu.easymusic.R;

import android.annotation.SuppressLint;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;


public class MymusicFragment extends Fragment implements OnClickListener {
	private View view;
	private LinearLayout mMymusic;
	private ListmusicFragment listmusicFragment ;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_my, container, false);
		initView();
		setOnclickListener();
		return view;
	}

	private void setOnclickListener() {
		mMymusic.setOnClickListener(this);
	}

	private void initView() {
		mMymusic = (LinearLayout) view.findViewById(R.id.mymusic);
	}

	@SuppressLint("NewApi")
	public void replaceFragment(Fragment fragment) {
		FragmentTransaction transaction = getActivity().getSupportFragmentManager()
				.beginTransaction();
		transaction.replace(R.id.fragment_main, fragment);
		transaction.addToBackStack(null);
		transaction.commit();
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.mymusic:
			listmusicFragment = new ListmusicFragment();
			replaceFragment(listmusicFragment);
			break;

		default:
			break;
		}
	}

}
