package com.awaitu.easymusic.view;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.awaitu.easymusic.adapter.MenuItemAdapter;

import com.awaitu.easymusic.R;

import com.awaitu.easymusic.fragment.HomeFragment;
import com.awaitu.easymusic.fragment.MymusicFragment;
import com.awaitu.easymusic.fragment.PersonLoginFragment;


public class MainActivity extends FragmentActivity {
    private ListView mLvLeftMenu;
    private DrawerLayout drawerLayout;
    private FragmentManager fragmentManager;
    private FragmentTransaction transaction;
    private RadioGroup radioGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fragmentManager = getSupportFragmentManager();
        radioGroup = (RadioGroup) findViewById(R.id.radioGroup1);
        ((RadioButton) radioGroup.findViewById(R.id.radio0)).setChecked(true);
        drawerLayout = (DrawerLayout) findViewById(R.id.fd);
        mLvLeftMenu = (ListView) findViewById(R.id.id_lv_left_menu);
        transaction = fragmentManager.beginTransaction();
        Fragment fragment = new HomeFragment();
        transaction.replace(R.id.content, fragment);
        transaction.commit();
        setDrawerLayout();
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.radio0:
                        transaction = fragmentManager.beginTransaction();
                        Fragment homeFragment = new HomeFragment();
                        transaction.replace(R.id.content, homeFragment);
                        transaction.commit();
                        break;
                    case R.id.radio1:
                        transaction = fragmentManager.beginTransaction();
                        Fragment myFragment = new MymusicFragment();
                        transaction.replace(R.id.content, myFragment);
                        transaction.commit();
                        break;
                    case R.id.radio2:
                        transaction = fragmentManager.beginTransaction();
                        Fragment personFragment = new PersonLoginFragment();
                        transaction.replace(R.id.content, personFragment);
                        transaction.commit();
                        break;
                }

            }
        });
    }

    private void setDrawerLayout() {
        LayoutInflater inflater = LayoutInflater.from(this);
        mLvLeftMenu.addHeaderView(inflater.inflate(R.layout.nav_header_main, mLvLeftMenu, false));
        mLvLeftMenu.setAdapter(new MenuItemAdapter(this));
        mLvLeftMenu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 1:
                        Toast.makeText(MainActivity.this, "您现在是游客登录，注册会有惊喜~", Toast.LENGTH_SHORT).show();
                        break;
                    case 2:

                        Toast.makeText(MainActivity.this, "攻城狮正在施工~", Toast.LENGTH_SHORT).show();
                        break;
                    case 3:

                        Toast.makeText(MainActivity.this, "攻城狮正在施工~", Toast.LENGTH_SHORT).show();
                        break;
                    case 4:
                        Toast.makeText(MainActivity.this, "攻城狮正在施工~", Toast.LENGTH_SHORT).show();


                        break;
                    case 5:

//						drawerLayout.closeDrawers();
                        //						finish();
                        throw new RuntimeException();

                    default:
                        break;


                }
            }
        });
    }


}
