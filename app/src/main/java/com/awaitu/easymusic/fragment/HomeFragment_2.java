package com.awaitu.easymusic.fragment;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.awaitu.easymusic.R;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment_2 extends Fragment {
    private View view;
    private ListView mListView;
    private List<VideoInfo> videoList=new ArrayList<VideoInfo>();
    private VideoInfo video;
    private myAdapter adapter;
    private int currentIndex=0;
    private String url1="http://www.gaoliping.com/ceshi.mp4";
    private String url2="http://www.gaoliping.com/ceshi.mp4";
    private VideoView mVideoView;
    MediaController mMediaCtrl;
    private int playPosition=-1;
    private boolean isPlaying=false;
    private int preLast = -1;
    private int ifFirst = 0;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub
         view = inflater.inflate(R.layout.verticalviewpager, null);
        //构造视频数据
        for(int i=0;i<50;i++){
            if(i%2==0){
                video=new VideoInfo(url1,"测试"+i,R.drawable.video2);
            }else{
                video=new VideoInfo(url2,"测试"+i,R.drawable.video1);
            }
            videoList.add(video);
        }
        mListView=(ListView) view.findViewById(R.id.video_listview);
        adapter = new myAdapter();
        mListView.setAdapter(adapter);
        mListView.setOnScrollListener(new AbsListView.OnScrollListener() {

            public void onScrollStateChanged(AbsListView view, int scrollState) {
                // TODO Auto-generated method stub

            }
            public void onScroll(AbsListView view, int firstVisibleItem,
                                 int visibleItemCount, int totalItemCount) {
                System.out.println(firstVisibleItem + "--" + visibleItemCount);
                if((currentIndex<firstVisibleItem || currentIndex>mListView.getLastVisiblePosition())&&isPlaying){
                    playPosition=mVideoView.getCurrentPosition();
                    mVideoView.pause();
                    mVideoView.setMediaController(null);
                    //	isPaused=true;
                    isPlaying=false;
                    Toast.makeText(getActivity(),"视频已经暂停了",Toast.LENGTH_SHORT).show();
                }else
                {
                    if(preLast==firstVisibleItem)
                    {
                        if(visibleItemCount>2 && currentIndex==0 && ifFirst!=3)
                        {
                            ifFirst =0;
                            currentIndex =firstVisibleItem + 1;
                            adapter.notifyDataSetChanged();
                        }else if(visibleItemCount== 2 && firstVisibleItem==0 && ifFirst==0)
                        {
                            ifFirst = 1;
                            currentIndex =firstVisibleItem ;
                            adapter.notifyDataSetChanged();
                        }
                    }else
                    {
                        preLast = firstVisibleItem;
                        ifFirst = 0;
                        if(visibleItemCount>=2)
                        {

                            currentIndex =firstVisibleItem + 1;
                            adapter.notifyDataSetChanged();

                        }else if(visibleItemCount==1)
                        {
                            currentIndex=firstVisibleItem;

                            adapter.notifyDataSetChanged();
                        }

                    }


                }
            }
        });
/*		mListView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				currentIndex=position;
				adapter.notifyDataSetChanged();
			}
		});*/
        return view;
    }

    class myAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            // TODO Auto-generated method stubs
            return videoList.size();
        }

        @Override
        public Object getItem(int position) {
            // TODO Auto-generated method stub
            return videoList.get(position);
        }

        @Override
        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            final ViewHolder holder;
            final int mPosition=position;
            if(convertView==null){
                convertView=LayoutInflater.from(getActivity()).inflate(R.layout.video_item_layout, null);
                holder=new ViewHolder();
                holder.videoImage=(ImageView) convertView.findViewById(R.id.video_image);
                holder.videoNameText=(TextView)convertView.findViewById(R.id.video_name_text);
                holder.videoPlayBtn=(ImageButton)convertView.findViewById(R.id.video_play_btn);
                holder.mProgressBar=(ProgressBar) convertView.findViewById(R.id.progressbar);
                convertView.setTag(holder);
            }else{
                holder=(ViewHolder) convertView.getTag();
            }
            holder.videoImage.setImageDrawable(getResources().getDrawable(videoList.get(position).getVideoImage()));
            holder.videoNameText.setText(videoList.get(position).getVideoName());
            holder.videoPlayBtn.setVisibility(View.VISIBLE);
            holder.videoImage.setVisibility(View.VISIBLE);
            holder.videoNameText.setVisibility(View.VISIBLE);
            mMediaCtrl = new MediaController(getActivity(),false);
            if(currentIndex == position){
                holder.videoPlayBtn.setVisibility(View.INVISIBLE);
                holder.videoImage.setVisibility(View.INVISIBLE);
                holder.videoNameText.setVisibility(View.INVISIBLE);

                if(isPlaying || playPosition==-1){
                    if(mVideoView!=null){
                        mVideoView.setVisibility(View.GONE);
                        mVideoView.stopPlayback();
                        holder.mProgressBar.setVisibility(View.GONE);
                    }
                }
                mVideoView=(VideoView) convertView.findViewById(R.id.videoview);
                mVideoView.setVisibility(View.VISIBLE);
                mMediaCtrl.setAnchorView(mVideoView);
                mMediaCtrl.setMediaPlayer(mVideoView);
                mVideoView.setMediaController(mMediaCtrl);
                mVideoView.requestFocus();
                holder.mProgressBar.setVisibility(View.VISIBLE);
                mVideoView.setVideoPath(videoList.get(mPosition).getUrl());
                if(isPlaying){
                    Toast.makeText(getActivity(),"播放新的视频",Toast.LENGTH_SHORT).show();
                    mVideoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        public void onCompletion(MediaPlayer mp) {
                            if(mVideoView!=null){
                                mVideoView.seekTo(0);
                                mVideoView.stopPlayback();
                                mVideoView.start();
                                //currentIndex=-1;
                                //	isPaused=false;
                                isPlaying=false;
                                holder.mProgressBar.setVisibility(View.GONE);
                                adapter.notifyDataSetChanged();
                            }
                        }
                    });
                }
                mVideoView.setOnErrorListener(new MediaPlayer.OnErrorListener() {

                    @Override
                    public boolean onError(MediaPlayer mp, int what, int extra) {
                        mp.reset();
                        return false;
                    }
                });


                mVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {

                    @Override
                    public void onPrepared(MediaPlayer mp) {
                        holder.mProgressBar.setVisibility(View.GONE);
                        mVideoView.pause();
                    }
                });

            }else{
                holder.videoPlayBtn.setVisibility(View.VISIBLE);
                holder.videoImage.setVisibility(View.VISIBLE);
                holder.videoNameText.setVisibility(View.VISIBLE);
                holder.mProgressBar.setVisibility(View.GONE);
            }

            holder.videoPlayBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    playPosition=mVideoView.getCurrentPosition();
                    mVideoView.pause();
                    mVideoView.setMediaController(null);
                    isPlaying=false;
                    if(mPosition==0)
                    {
                        ifFirst = 3;
                    }
                    currentIndex=mPosition;
                    playPosition=-1;
                    adapter.notifyDataSetChanged();
                }
            });
            return convertView;
        };
    }
    static class ViewHolder{
        ImageView videoImage;
        TextView videoNameText;
        ImageButton videoPlayBtn;
        ProgressBar mProgressBar;
    }
    static class VideoInfo {
        private String url;
        private String videoName;
        private int videoImage;
        public VideoInfo(String url,String name,int path) {
            this.videoName=name;
            this.videoImage=path;
            this.url=url;
        }
        public String getVideoName() {
            return videoName;
        }
        public void setVideoName(String videoName) {
            this.videoName = videoName;
        }
        public int getVideoImage() {
            return videoImage;
        }
        public void setVideoImage(int videoImage) {
            this.videoImage = videoImage;
        }
        public String getUrl() {
            return url;
        }
        public void setUrl(String url) {
            this.url = url;
        }
    }
    public void onDestroyView() {
        super.onDestroyView();
        if(null != view){
            ((ViewGroup)view.getParent()).removeView(view);
        }
    }
}
