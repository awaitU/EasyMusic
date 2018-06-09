package com.awaitu.easymusic.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.awaitu.easymusic.R;


public class HtmlPlayAudioFragment extends Fragment {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_html_play_audio, container, false);

        WebView webview = (WebView)view.findViewById(R.id.webview);
        WebSettings settings=webview.getSettings();
        //开启对JavaScript的支持
        settings.setJavaScriptEnabled(true);
        webview.loadUrl("http://www.gaoliping.com/newmusic.html");
        return view;
    }



    @Override
    public void onDetach() {
        super.onDetach();
    }


}
