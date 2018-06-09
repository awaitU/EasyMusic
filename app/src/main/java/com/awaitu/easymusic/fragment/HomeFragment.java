package com.awaitu.easymusic.fragment;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.awaitu.easymusic.R;
import com.awaitu.easymusic.adapter.MyFragmentPagerAdapter;

import java.util.ArrayList;

public class HomeFragment extends Fragment {
    private View view;
    Resources resources;
    private ViewPager mPager;
    private ArrayList<Fragment> fragmentsList;
    private TextView tabmusic, tabvideo, tvtabss;
    Fragment home1;
    Fragment home2;
    Fragment home3;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        view = inflater.inflate(R.layout.fragment_home, null);
        resources = getResources();
        InitTextView(view);
        InitViewPager(view);
        tabvideo.setTextColor(resources.getColor(R.color.gray));
        return view;
    }

    private void InitTextView(View parentView) {
        tabmusic = (TextView) parentView.findViewById(R.id.tab_1);
        tabvideo = (TextView) parentView.findViewById(R.id.tab_2);
        tvtabss = (TextView) parentView.findViewById(R.id.tab_3);

        tabmusic.setOnClickListener(new MyOnClickListener(0));
        tabvideo.setOnClickListener(new MyOnClickListener(1));
        tvtabss.setOnClickListener(new MyOnClickListener(2));
    }

    private void InitViewPager(View parentView) {
        mPager = (ViewPager) parentView.findViewById(R.id.vPager);
        fragmentsList = new ArrayList<Fragment>();
        home1 = new HomeFragment_1();
        home2 = new HomeFragment_2();
        home3 = new HomeFragment_3();
        fragmentsList.add(home1);
        fragmentsList.add(home2);
        fragmentsList.add(home3);
        mPager.setAdapter(new MyFragmentPagerAdapter(getChildFragmentManager(), fragmentsList));
        mPager.setOnPageChangeListener(new MyOnPageChangeListener());
        mPager.setCurrentItem(0);

    }

    public class MyOnClickListener implements View.OnClickListener {
        private int index = 0;

        public MyOnClickListener(int i) {
            index = i;
        }

        @Override
        public void onClick(View v) {
            mPager.setCurrentItem(index);
        }
    }

    ;

    public class MyOnPageChangeListener implements OnPageChangeListener {

        @Override
        public void onPageSelected(int arg0) {
            switch (arg0) {
                case 0:

                    tabvideo.setTextColor(resources.getColor(R.color.gray));
                    tvtabss.setTextColor(resources.getColor(R.color.gray));

                    tabmusic.setTextColor(resources.getColor(R.color.lightblue));
                    break;
                case 1:

                    tabmusic.setTextColor(resources.getColor(R.color.gray));
                    tvtabss.setTextColor(resources.getColor(R.color.gray));

                    tabvideo.setTextColor(resources.getColor(R.color.lightblue));
                    break;
                case 2:

                    tabmusic.setTextColor(resources.getColor(R.color.gray));
                    tabvideo.setTextColor(resources.getColor(R.color.gray));

                    tvtabss.setTextColor(resources.getColor(R.color.lightblue));
                    break;
            }
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
        }

        @Override
        public void onPageScrollStateChanged(int arg0) {
        }
    }

}
