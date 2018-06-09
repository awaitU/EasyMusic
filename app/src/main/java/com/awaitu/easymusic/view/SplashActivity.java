package com.awaitu.easymusic.view;

import android.os.Bundle;

import com.awaitu.easymusic.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.Window;

public class SplashActivity extends Activity {

    private static final int SPLASH_TIME = 3000;
    Handler mhandler;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_splash);
        mhandler = new Handler();
        mhandler.postDelayed(new SplashHandler(),SPLASH_TIME);
    }
    private class SplashHandler implements Runnable{
        public void run(){
            startActivity(new Intent(getApplication(),MainActivity.class));
            finish();
        }
    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(event.getAction() == MotionEvent.ACTION_DOWN)
        {
            mhandler.post(new SplashHandler());

            return true;
        }
        return super.onTouchEvent(event);
    }

}
