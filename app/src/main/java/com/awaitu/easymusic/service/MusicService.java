package com.awaitu.easymusic.service;

import java.util.List;

import com.awaitu.easymusic.utils.Constants;
import com.awaitu.easymusic.bean.Myapp;
import com.awaitu.easymusic.bean.Mp3Info;
import com.awaitu.easymusic.utils.MediaUtil;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.os.IBinder;
import android.util.Log;

public class MusicService extends Service implements OnPreparedListener,
        OnCompletionListener {
    public static MediaPlayer player;
    private MyBroadcastReceiver receiver;
    private List<Mp3Info> mp3Infos;
    private int position;
    private boolean isFirst = true;
    public static int current;
    private static int duration = 0;
    public static boolean isPlaying;

    @Override
    public void onCreate() {
        Log.i("music service", "oncreate");
        super.onCreate();
        regFilter();
    }

    public static int getCurrent() {
        current = player.getCurrentPosition();

        return current;
    }

    public static int getDuration() {
        duration = player.getDuration();
        return duration;
    }

    private void regFilter() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(Constants.ACTION_LIST_SEARCH);
        filter.addAction(Constants.ACTION_PAUSE);
        filter.addAction(Constants.ACTION_PLAY);
        filter.addAction(Constants.ACTION_NEXT);
        filter.addAction(Constants.ACTION_PRV);
        filter.addAction(AudioManager.ACTION_AUDIO_BECOMING_NOISY);
        filter.setPriority(1000);
        receiver = new MyBroadcastReceiver();
        registerReceiver(receiver, filter);
    }

    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (receiver != null) {
            unregisterReceiver(receiver);
        }
        if (player != null) {
            player.release();
            player = null;
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        player = new MediaPlayer();
        mp3Infos = intent.getParcelableArrayListExtra("mp3Infos");
        return super.onStartCommand(intent, flags, startId);
    }


    public class MyBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(Constants.ACTION_LIST_SEARCH)) {
                isPlaying = true;
                long id = intent.getLongExtra("id", 0);
                for (int i = 0; i < mp3Infos.size(); i++) {
                    if (id == mp3Infos.get(i).getId()) {
                        position = i;
                        prepareMusic(position);
                        isFirst = false;
                        break;
                    }
                }
            } else if (intent.getAction().equals(Constants.ACTION_PAUSE)) {
                isPlaying = false;
                if (player.isPlaying()) {
                    pauseMusic();
                }
            } else if (intent.getAction().equals(Constants.ACTION_PLAY)) {
                isPlaying = true;
                if (!player.isPlaying()) {
                    if (isFirst) {
                        position = intent.getIntExtra("position", 0);
                        prepareMusic(position);
                        isFirst = false;
                    } else {
                        player.seekTo(current);
                        player.start();
                    }
                }
            } else if (intent.getAction().equals(Constants.ACTION_NEXT)) {
                isPlaying = true;
                if ((Myapp.state % 3) == 1 || ((Myapp.state % 3) == 2)) {
                    if (position < mp3Infos.size() - 1) {
                        ++position;
                        prepareMusic(position);
                    } else {
                        position = 0;
                        prepareMusic(0);
                    }
                } else if ((Myapp.state % 3) == 0) {
                    Myapp.getRandom();
                    position = Myapp.position;
                    prepareMusic(position);
                }

            } else if (intent.getAction().equals(Constants.ACTION_PRV)) {
                isPlaying = true;
                if ((Myapp.state % 3) == 1 || ((Myapp.state % 3) == 2)) {
                    if (position == 0) {
                        position = mp3Infos.size() - 1;
                        prepareMusic(position);
                    } else {
                        --position;
                        prepareMusic(position);
                    }
                } else if ((Myapp.state % 3) == 0) {
                    Myapp.getRandom();
                    position = Myapp.position;
                    prepareMusic(position);
                }

            } else if (intent.getAction().equals(
                    AudioManager.ACTION_AUDIO_BECOMING_NOISY)) {
                isPlaying = false;
                if (intent.getIntExtra("state", 0) == 0) {
                    Intent intent2 = new Intent();
                    intent2.setAction(Constants.ACTION_PAUSE);
                    sendBroadcast(intent2);
                }

            }
        }

    }


    private void prepareMusic(int position) {
        player.reset();
        player.setAudioStreamType(AudioManager.STREAM_MUSIC);
        String url = mp3Infos.get(position).getUrl();
        try {
            player.setDataSource(url);
        } catch (Exception e) {
            e.printStackTrace();
        }
        player.setOnPreparedListener(this);
        player.setOnCompletionListener(this);
        player.prepareAsync();
    }


    @Override
    public void onCompletion(MediaPlayer arg0) {
        if ((Myapp.state % 3) == 2) {
            prepareMusic(position);
        } else {
            Intent intent = new Intent();
            intent.setAction(Constants.ACTION_NEXT);
            sendBroadcast(intent);
        }

    }

    @Override
    public void onPrepared(MediaPlayer arg0) {
        player.start();
    }

    public void pauseMusic() {
        current = player.getCurrentPosition();
        player.pause();
    }
}
