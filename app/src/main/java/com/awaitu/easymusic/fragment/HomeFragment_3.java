package com.awaitu.easymusic.fragment;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.awaitu.easymusic.R;

import java.util.ArrayList;
import java.util.List;


public class HomeFragment_3 extends Fragment {
    private int i;
    private ListView internetmusic;
    private List<String> address = new ArrayList<String>();
    private List<String> list = new ArrayList<String>();
    private MediaPlayer player;//声明一个MediaPlayer对象
    private String name = "测试音乐名字";
    private ArrayAdapter<String> adapter;
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        View view = inflater.inflate(R.layout.fragment_home_3, null);
        internetmusic = (ListView)view.findViewById(R.id.internetmusic);
        requestinternetmusicaddress();
        adapter = new ArrayAdapter<String>(getContext(),android.R.layout.simple_list_item_1,list);
        internetmusic.setAdapter(adapter);
        internetmusic.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Toast.makeText(getActivity(),"稍后将为您播放"+"第"+i+"条歌曲",Toast.LENGTH_SHORT).show();
//                    playmusic();

            }
        });
        return view;
    }
    //获取服务器端接口获取音乐名字
    public void requestinternetmusicaddress(){
        for(int i = 1;i<30;i++){
            //
            list.add(name+i);
        }

    }
    //获取服务器端接口获取音乐网络地址
    public void requestinternetmusicaddress(String [] arr){

//        address.add();

    }
//    public void playmusic(){
//        try {
//            player = MediaPlayer.create(getActivity(), Uri
//                    .parse(uri));//实例化对象，通过播放本机服务器上的一首音乐
//            player.prepare();
//            player.seekTo(i);
//            player.setLooping(false);//设置不循环播放
//        }catch (Exception e){
//
//        }
//
//    }
}
