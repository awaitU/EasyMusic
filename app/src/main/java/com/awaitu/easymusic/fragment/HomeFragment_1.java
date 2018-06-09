package com.awaitu.easymusic.fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.awaitu.easymusic.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class HomeFragment_1 extends Fragment implements View.OnClickListener {
	private HtmlPlayAudioFragment htmlplayaudiofragment;
	private ImageView test1;
	private View view;
	private ImageView[] imageViews = null;
	private ImageView imageView = null;
	private ViewPager advPager = null;
	private ViewGroup group = null;
	private int index = 0;
	private boolean isContinue = true;
	private String[] itemTexts = new String[] { "私人FM", "每日推荐", "歌单", "排行榜" };
	private int[] itemImages = new int[] { R.mipmap.privatefm, R.mipmap.recommendeddaily, R.mipmap.playlist,
				R.mipmap.list,};
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_home_1, container,false);
		test1 = (ImageView)view.findViewById(R.id.test1);
		imageView = (ImageView)view.findViewById(R.id.imageView);
		group = (ViewGroup)view.findViewById(R.id.viewGroup);
		advPager = (ViewPager)view.findViewById(R.id.adv_pager);
		GridView gridView = (GridView)view.findViewById(R.id.gridView);
		gridView.setAdapter(getAdapter());
		initViewPager();
		test1.setOnClickListener(this);
		return view;
	}
	public void onClick(View view){
		switch (view.getId()){
			case R.id.test1:
				getNetInfor();
		}
	}

	public void replaceFragment(Fragment fragment) {
		FragmentTransaction transaction = getActivity().getSupportFragmentManager()
				.beginTransaction();
		transaction.replace(R.id.content, fragment);
		transaction.addToBackStack(null);
		transaction.commit();
	}
	public void getNetInfor() {
		//首先是获取网络连接管理者
		ConnectivityManager manager = (ConnectivityManager)
				getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo info = manager.getActiveNetworkInfo();
		//网络状态存在并且是已连接状态
		if (info != null && info.isConnected()) {
			Toast.makeText(getActivity(), "网络已连接", Toast.LENGTH_SHORT).show();
			htmlplayaudiofragment = new HtmlPlayAudioFragment();
			replaceFragment(htmlplayaudiofragment);
		} else {
			Toast.makeText(getActivity(), "网络连接失败", Toast.LENGTH_SHORT).show();
			//下面的这种写法你应该看得懂
			new AlertDialog.Builder(getActivity())
					.setTitle("请检查网络连接")
					.setPositiveButton("确定", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							if (android.os.Build.VERSION.SDK_INT < 26) {
								//安卓系统3.0以上打开设置界面，也可以直接用ACTION_WIRELESS_SETTINGS打开到wifi界面
								startActivity(new Intent(android.provider.Settings.ACTION_SETTINGS));
							} else {
								startActivity(new Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS));
							}
						}
					})
					.setNegativeButton("取消", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialogInterface, int i) {

						}
					})
					.show();
		}
	}
	private void initViewPager() {
		final List<View> advPics = new ArrayList<View>();
		ImageView img1 = new ImageView(getActivity());
		img1.setBackgroundResource(R.drawable.thefirst);
		advPics.add(img1);
		ImageView img2 = new ImageView(getActivity());
		img2.setBackgroundResource(R.drawable.thesencond);
		advPics.add(img2);
		ImageView img3 = new ImageView(getActivity());
		img3.setBackgroundResource(R.drawable.thethird);
		advPics.add(img3);
		imageViews = new ImageView[advPics.size()];
		for (int i = 0; i < advPics.size(); i++) {
			imageView = new ImageView(getActivity());
			imageView.setLayoutParams(new ViewGroup.LayoutParams(20, 20));
			// 设置子父控件之间的距离
			imageView.setPadding(20, 0, 20, 0);
			imageViews[i] = imageView;
			if (i == 0) {
				imageViews[i]
						.setBackgroundResource(R.drawable.banner_dian_focus);
			} else {
				imageViews[i]
						.setBackgroundResource(R.drawable.banner_dian_blur);
			}
			group.addView(imageViews[i]);
		}
		advPager.setAdapter(new AdvAdapter(advPics));
		advPager.setOnPageChangeListener(new GuidePageChangeListener());
		advPager.setOnTouchListener(new View.OnTouchListener() {
			public boolean onTouch(View v, MotionEvent event) {
				switch (event.getAction()) {
					case MotionEvent.ACTION_DOWN:
					case MotionEvent.ACTION_MOVE:
						isContinue = false;
						break;
					case MotionEvent.ACTION_UP:
						isContinue = true;
						break;
					default:
						isContinue = true;
						break;
				}
				return false;
			}
		});
		Runnable r = new Runnable() {

			@Override
			public void run() {
				if (isContinue) {
					index++;
					if (index >= advPics.size()) {
						index = 0;
					}
					viewHandler.sendEmptyMessage(index);
					viewHandler.postDelayed(this, 3000);
				}
			}

		};
		viewHandler.postDelayed(r, 3000);

		// advPager.setCurrentItem(0);
	}

	private final Handler viewHandler = new Handler() {

		public void handleMessage(Message msg) {
			advPager.setCurrentItem(index);
			super.handleMessage(msg);
		}

	};

	private final class GuidePageChangeListener implements OnPageChangeListener {

		@Override
		public void onPageScrollStateChanged(int arg0) {

		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {

		}

		@Override
		public void onPageSelected(int arg0) {
			for (int i = 0; i < imageViews.length; i++) {
				imageViews[arg0]
						.setBackgroundResource(R.drawable.banner_dian_focus);
				if (arg0 != i) {
					imageViews[i]
							.setBackgroundResource(R.drawable.banner_dian_blur);
				}
			}

		}

	}

	private final class AdvAdapter extends PagerAdapter {
		private List<View> views = null;

		public AdvAdapter(List<View> views) {
			this.views = views;
		}

		public void destroyItem(View arg0, int arg1, Object arg2) {
			// 删除页卡
			((ViewPager) arg0).removeView(views.get(arg1));
		}

		public void finishUpdate(View arg0) {

		}

		public int getCount() {
			// 返回页卡的数量
			// System.out.println("叶卡的数量"+views.size());
			return views.size();
		}


		public Object instantiateItem(View arg0, int arg1) {
			// 添加页卡
			((ViewPager) arg0).addView(views.get(arg1), 0);
			return views.get(arg1);
		}


		public boolean isViewFromObject(View arg0, Object arg1) {
			// 官方提示
			return arg0 == arg1;
		}

		public void restoreState(Parcelable arg0, ClassLoader arg1) {

		}


		public Parcelable saveState() {
			return null;
		}


		public void startUpdate(View arg0) {

		}

	}

	private ListAdapter getAdapter() {
		// 该list用来存放每一个item对应的文字和图片
		List<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
		for (int i = 0; i < itemTexts.length; i++) {
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("itemText", itemTexts[i]);
			map.put("itemImage", itemImages[i]);
			list.add(map);
		}
		SimpleAdapter simpleAdapter = new SimpleAdapter(this.getActivity(), list, R.layout.item, new String[] { "itemText", "itemImage" },
				new int[] { R.id.imageText, R.id.imageView });
		return simpleAdapter;
	}
	public void onDestroyView() {
		super.onDestroyView();
		if(null != view){
			((ViewGroup)view.getParent()).removeView(view);
		}
	}
}



