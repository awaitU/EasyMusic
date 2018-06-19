package com.awaitu.easymusic.view;

import android.os.Bundle;

import com.awaitu.easymusic.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.view.Window;

public class SplashActivity extends Activity {
    private static final int SPLASH_TIME = 3000;
    Handler mHandler;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_splash);
        mHandler = new Handler();
        mHandler.postDelayed(new SplashHandler(), SPLASH_TIME);

    }


    private class SplashHandler implements Runnable {
        public void run() {
            startActivity(new Intent(getApplication(), MainActivity.class));
            finish();
        }
    }

}
