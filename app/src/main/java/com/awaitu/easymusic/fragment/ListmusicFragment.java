package com.awaitu.easymusic.fragment;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RemoteViews;
import android.widget.TextView;

import com.awaitu.easymusic.R;
import com.awaitu.easymusic.adapter.LocalMusicAdapter;
import com.awaitu.easymusic.bean.Mp3Info;
import com.awaitu.easymusic.bean.Myapp;
import com.awaitu.easymusic.utils.Constants;
import com.awaitu.easymusic.utils.MediaUtil;
import com.awaitu.easymusic.view.MusicActivity;

import java.util.List;

import static android.content.Context.NOTIFICATION_SERVICE;
import static com.awaitu.easymusic.bean.Myapp.position;

public class ListmusicFragment extends Fragment implements OnItemClickListener, View.OnClickListener {
    private NotificationManager manager;
    private RemoteViews remoteViews;
    private View view;
    private LocalMusicAdapter adapter;
    private ListView mp3lists;
    private List<Mp3Info> infos;
    private LinearLayout mBtmLinear;
    private LocalMusicBroadcastReceiver receiver;
    private boolean isFirst = true;
    private List<Mp3Info> mp3Infos;
    private ImageView btm_album;
    private TextView btm_title;
    private TextView btm_artist;
    private ImageView btm_state;
    private ImageView btm_next;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_localmusic, container, false);
        initview();
        setOnclickListener();
        mp3lists.setAdapter(adapter);
        mp3lists.setOnItemClickListener(this);
        mp3Infos = MediaUtil.getMp3Infos(getContext());
        mBtmLinear.setOnClickListener(this);
        regFilter();
        return view;
    }

    private void initview() {
        mBtmLinear = (LinearLayout) view.findViewById(R.id.btm_linear);
        mp3lists = (ListView) view.findViewById(R.id.mp3lists);
        infos = MediaUtil.getMp3Infos(getActivity());
        adapter = new LocalMusicAdapter(getActivity(), infos);
        mBtmLinear = (LinearLayout) view.findViewById(R.id.btm_linear);
        btm_album = (ImageView) view.findViewById(R.id.bottom_img_album);
        btm_artist = (TextView) view.findViewById(R.id.bottom_tv_artist);
        btm_title = (TextView) view.findViewById(R.id.bottom_tv_title);
        btm_state = (ImageView) view.findViewById(R.id.player_state);
        btm_next = (ImageView) view.findViewById(R.id.play_next);
        manager = (NotificationManager) getActivity().getSystemService(NOTIFICATION_SERVICE);
        remoteViews = new RemoteViews(getActivity().getPackageName(),
                R.layout.customnotice);
        if (Myapp.isPlay) {
            Mp3Info info = infos.get(position);
            Bitmap bitmap = MediaUtil.getArtwork(getActivity(),
                    info.getId(), info.getAlbumId(), true, false);
            btm_album.setImageBitmap(bitmap);
            btm_artist.setText(info.getArtist());
            btm_title.setText(info.getTitle());
            btm_state.setImageResource(R.mipmap.player_btn_radio_pause_normal);
        }else{
            Mp3Info info = infos.get(position);
            Bitmap bitmap = MediaUtil.getArtwork(getActivity(),
                    info.getId(), info.getAlbumId(), true, false);
            btm_album.setImageBitmap(bitmap);
            btm_artist.setText(info.getArtist());
            btm_title.setText(info.getTitle());
            btm_state.setImageResource(R.mipmap.player_btn_radio_play_normal);
        }
    }

    private void setOnclickListener() {
        btm_state.setOnClickListener(this);
        btm_next.setOnClickListener(this);
        mBtmLinear.setOnClickListener(this);

    }

    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent broadcast = new Intent();
        broadcast.setAction(Constants.ACTION_LIST_SEARCH);
        broadcast.putExtra("id", infos.get(position).getId());
        broadcast.putExtra("position", position);
        getActivity().sendBroadcast(broadcast);
    }

    public Handler handler = new Handler() {

        public void handleMessage(Message msg) {

            Mp3Info info = (Mp3Info) msg.obj;
            Bitmap bitmap = MediaUtil.getArtwork(getActivity(),
                    info.getId(), info.getAlbumId(), true, false);
            btm_album.setImageBitmap(bitmap);
            btm_artist.setText(info.getArtist());
            btm_title.setText(info.getTitle());

            // 设置通知栏的图片文字
            remoteViews.setImageViewBitmap(R.id.widget_album, bitmap);
            remoteViews.setTextViewText(R.id.widget_title, info.getTitle());
            remoteViews.setTextViewText(R.id.widget_artist, info.getArtist());
            if (Myapp.isPlay) {
                btm_state.setImageResource(R.mipmap.player_btn_radio_pause_normal);
                remoteViews.setImageViewResource(R.id.widget_play, R.drawable.widget_btn_pause_normal);
            } else {
                btm_state.setImageResource(R.mipmap.player_btn_radio_play_normal);
                remoteViews.setImageViewResource(R.id.widget_play, R.drawable.widget_btn_play_normal);
            }
            setNotification();

        }

        ;
    };
    /**
     * 设置notifycation的播放，暂停按钮
     */
    private Handler handler2 = new Handler() {

        public void handleMessage(Message msg) {
            boolean is = (Boolean) msg.obj;
            if (is) {
                remoteViews.setImageViewResource(R.id.widget_play, R.drawable.widget_btn_play_normal);
                btm_state.setImageResource(R.mipmap.player_btn_radio_pause_normal);
            } else {
                remoteViews.setImageViewResource(R.id.widget_play, R.drawable.widget_btn_pause_normal);
                btm_state.setImageResource(R.mipmap.player_btn_radio_play_normal);
            }
        }

        ;

    };


    private void regFilter() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(Constants.ACTION_LIST_SEARCH);
        filter.addAction(Constants.ACTION_PAUSE);
        filter.addAction(Constants.ACTION_PLAY);
        filter.addAction(Constants.ACTION_NEXT);
        filter.addAction(Constants.ACTION_PRV);
        filter.addAction(Constants.DOWN_NOTIFY);
        filter.addAction(Intent.ACTION_SCREEN_OFF);
        filter.setPriority(800);
        receiver = new LocalMusicBroadcastReceiver();
        getActivity().registerReceiver(receiver, filter); // 注册接收
    }

    private void setNotification() {

        NotificationCompat.Builder builder = new NotificationCompat.Builder(getActivity());
        Intent intent = new Intent();
        intent.putExtra("position", position);
        intent.setClass(getActivity(), MusicActivity.class);
        // 点击跳转到主界面
        PendingIntent intent_go = PendingIntent.getActivity(getActivity(), 5, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        remoteViews.setOnClickPendingIntent(R.id.notice, intent_go);

        //设置取消通知按钮
        Intent down = new Intent();
        down.setAction(Constants.DOWN_NOTIFY);
        PendingIntent intent_close = PendingIntent.getBroadcast(getActivity(), 7, down,
                PendingIntent.FLAG_UPDATE_CURRENT);
        remoteViews.setOnClickPendingIntent(R.id.widget_close, intent_close);

        // 设置上一曲
        Intent prv = new Intent();
        prv.setAction(Constants.ACTION_PRV);
        PendingIntent intent_prev = PendingIntent.getBroadcast(getActivity(), 1, prv,
                PendingIntent.FLAG_UPDATE_CURRENT);
        remoteViews.setOnClickPendingIntent(R.id.widget_prev, intent_prev);

        // 设置播放
        if (Myapp.isPlay) {
            Intent playorpause = new Intent();
            playorpause.setAction(Constants.ACTION_PAUSE);
            PendingIntent intent_play = PendingIntent.getBroadcast(getActivity(), 2,
                    playorpause, PendingIntent.FLAG_UPDATE_CURRENT);
            remoteViews.setOnClickPendingIntent(R.id.widget_play, intent_play);
        }
        if (!Myapp.isPlay) {
            Intent playorpause = new Intent();
            playorpause.setAction(Constants.ACTION_PLAY);
            PendingIntent intent_play = PendingIntent.getBroadcast(getActivity(), 6,
                    playorpause, PendingIntent.FLAG_UPDATE_CURRENT);
            remoteViews.setOnClickPendingIntent(R.id.widget_play, intent_play);
        }

        // 下一曲
        Intent next = new Intent();
        next.setAction(Constants.ACTION_NEXT);
        PendingIntent intent_next = PendingIntent.getBroadcast(getActivity(), 3, next,
                PendingIntent.FLAG_UPDATE_CURRENT);
        remoteViews.setOnClickPendingIntent(R.id.widget_next, intent_next);

        // 设置收藏
        PendingIntent intent_fav = PendingIntent.getBroadcast(getActivity(), 4, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        remoteViews.setOnClickPendingIntent(R.id.widget_fav, intent_fav);

        builder.setSmallIcon(R.mipmap.logo); // 设置顶部图标

        Notification notify = builder.build();
        notify.contentView = remoteViews; // 设置下拉图标
        notify.bigContentView = remoteViews; // 防止显示不完全,需要添加apisupport
        notify.flags = Notification.FLAG_ONGOING_EVENT;
        notify.icon = R.mipmap.logo;

        manager.notify(100, notify);
    }

    /**
     * 自定义广播接收器
     *
     * @author sheepm
     */
    public class LocalMusicBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(Constants.ACTION_LIST_SEARCH)) {
                position = intent.getIntExtra("position", 0);
                Myapp.isPlay = true;
                long id = intent.getLongExtra("id", 0);
                for (int i = 0; i < mp3Infos.size(); i++) {
                    if (id == mp3Infos.get(i).getId()) {
                        isFirst = false;
                        Message message = Message.obtain();
                        message.obj = mp3Infos.get(i);
                        handler.sendMessage(message);
                    }
                }
            } else if (intent.getAction().equals(Constants.DOWN_NOTIFY)) {
                Log.d("Test", "I go here now");
                manager.cancelAll();
            } else if (intent.getAction().equals(Constants.ACTION_PLAY)) {
                if (isFirst) {
                    isFirst = false;
                    Myapp.isPlay = true;
                    Message message = Message.obtain();
                    message.obj = mp3Infos.get(intent
                            .getIntExtra("position", 0));
                    handler.sendMessage(message);

                } else {
                    Myapp.isPlay = true;
                    Message message = Message.obtain();
                    message.obj = Myapp.isPlay;
                    handler2.sendMessage(message);
                    setNotification();
                }

            } else if (intent.getAction().equals(Constants.ACTION_NEXT)) {
                Myapp.isPlay = true;
                isFirst = false;
                if ((Myapp.state % 3) == 1 || (Myapp.state % 3) == 2) {
                    if (position < mp3Infos.size() - 1) {
                        ++position;
                    } else {
                        position = 0;
                    }
                } else if ((Myapp.state % 3) == 0) {
                }
                Message message = Message.obtain();
                message.obj = mp3Infos.get(position);
                handler.sendMessage(message);
                Message message2 = Message.obtain();
                message2.obj = Myapp.isPlay;
                handler2.sendMessage(message2);

            } else if (intent.getAction().equals(Constants.ACTION_PAUSE)) {
                Myapp.isPlay = false;
                isFirst = false;
                Message message = Message.obtain();
                message.obj = mp3Infos.get(position);
                handler.sendMessage(message);
                Message message2 = Message.obtain();
                message2.obj = Myapp.isPlay;
                handler2.sendMessage(message2);
                setNotification();
            } else if (intent.getAction().equals(Constants.ACTION_PRV)) {
                Myapp.isPlay = true;
                if ((Myapp.state % 3) == 1 || (Myapp.state % 3) == 2) {
                    if (position == 0) {
                        position = mp3Infos.size() - 1;
                    } else {
                        --position;
                    }
                } else if ((Myapp.state % 3) == 0) {
                }
                Message message = Message.obtain();
                message.obj = mp3Infos.get(position);
                handler.sendMessage(message);
                Message message2 = Message.obtain();
                message2.obj = Myapp.isPlay;
                handler2.sendMessage(message2);

            } else if (intent.getAction().equals(Intent.ACTION_SCREEN_OFF)) {
                Intent lockscreen = new Intent(getActivity(), MusicActivity.class);
                lockscreen.putExtra("position", position);
                lockscreen.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(lockscreen);

            }
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (remoteViews != null) {
            manager.cancel(100);
        }
        if (receiver != null) {
            getActivity().unregisterReceiver(receiver);
        }
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btm_linear:
                Intent intent = new Intent();
                intent.putExtra("position", position);
                intent.setClass(getActivity(), MusicActivity.class);
                startActivity(intent);
                break;
            case R.id.player_state:
                if (btm_state
                        .getDrawable()
                        .getConstantState()
                        .equals(getResources().getDrawable(
                                R.mipmap.player_btn_radio_pause_normal)
                                .getConstantState())) {
                    Intent broadcast = new Intent();
                    broadcast.setAction(Constants.ACTION_PAUSE);
                    getActivity().sendBroadcast(broadcast);
                    btm_state.setImageResource(R.mipmap.player_btn_radio_play_normal);
                } else if (btm_state
                        .getDrawable()
                        .getConstantState()
                        .equals(getResources().getDrawable(
                                R.mipmap.player_btn_radio_play_normal)
                                .getConstantState())) {
                    Intent broadcast = new Intent();
                    broadcast.setAction(Constants.ACTION_PLAY);
                    getActivity().sendBroadcast(broadcast);
                    btm_state.setImageResource(R.mipmap.player_btn_radio_pause_normal);
                }
                break;
            case R.id.play_next:
                Intent broadcast = new Intent();
                broadcast.setAction(Constants.ACTION_NEXT);
                getActivity().sendBroadcast(broadcast);
                break;
            default:
                break;
        }
    }


}
