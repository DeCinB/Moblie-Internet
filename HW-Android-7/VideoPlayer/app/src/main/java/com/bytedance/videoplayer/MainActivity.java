package com.bytedance.videoplayer;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.VideoView;

import java.text.SimpleDateFormat;

public class MainActivity extends AppCompatActivity {
    private VideoView videoView;
    private LinearLayout videoLayout;
    private Button btnPlay;
    private Button btnPause;
    private SeekBar seekBar;
    private TextView timeView;
    private LinearLayout layout;

    private static final String TAG = "MainActivity";
    private static final String KEY_PROGRESS="progress";
    private static final String KEY_PLAY="play";
    private int duration;
    int portraitHeight;
    int landscapeHeight;

    private SimpleDateFormat format;
    private final Handler handler=new Handler();
    private final Runnable runnable=new Runnable() {
        @Override
        public void run() {
            //获取当前视频播放位置，计算进度条应显示的位置，设置时间显示文本
            int curTime=videoView.getCurrentPosition();
            int position=curTime*100/duration;
            seekBar.setProgress(position);
            String timeStr=format.format(curTime)+"/"+format.format(duration);
            timeView.setText(timeStr);
            Log.d(TAG, "run:position="+position+"curTime="+curTime/1000+"秒");
            //每隔一秒更新一次进度条和时间
            handler.postDelayed(this,1000);
        }
    };

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        videoView=findViewById(R.id.videoView);
        videoLayout=findViewById(R.id.videoLayout);
        btnPlay=findViewById(R.id.btnPlay);
        btnPause=findViewById(R.id.btnPause);
        seekBar=findViewById(R.id.seekbar);
        timeView=findViewById(R.id.timeview);
        layout=findViewById(R.id.layout);
        //视频资源
        Intent intent = getIntent();
        Uri uri = intent.getData();

        if (uri != null) {//从相册选取视频播放
            videoView.setVideoURI(uri);
            Log.d(TAG, "onCreate: Uri");
        }
        else {
            videoView.setVideoPath(getVideoPath(R.raw.accusefive_red));
        }

        //视频就绪
        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                //获取视频时间长度
                    duration = videoView.getDuration();

                    //根据视频时间长度设置相应的的时间显示格式
                    if (duration / (3600 * 1000) <= 0)
                        format = new SimpleDateFormat("mm:ss");
                    else
                        format = new SimpleDateFormat("HH:mm:ss");
                    if (savedInstanceState!=null) {
                        if(savedInstanceState.containsKey(KEY_PROGRESS)){
                            int progress=savedInstanceState.getInt(KEY_PROGRESS);
                            seekBar.setProgress(progress);
                            int curTime=progress*duration/100;
                            String timeStr=format.format(curTime);
                            timeView.setText(timeStr);
                            videoView.seekTo(curTime);
                            Log.d(TAG, "onPrepared: "+KEY_PROGRESS+progress);
                        }
                        if(savedInstanceState.containsKey(KEY_PLAY)){
                            boolean play=savedInstanceState.getBoolean(KEY_PLAY);
                            Log.d(TAG, "onPrepared: "+KEY_PLAY+play);
                            if(!play)
                                videoView.pause();
                            else
                                videoView.start();
                        }
                    }
                    else{
                        String timeStr = "00:00/" + format.format(duration);
                        timeView.setText(timeStr);
                        Log.d(TAG, "duration=" + duration / 1000 );


                    }
                    //每秒检测一次进度、时间变化
                    handler.postDelayed(runnable,1000);
            }
        });

        btnPlay.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                videoView.start();
            }
        });

        btnPause.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                videoView.pause();
            }
        });

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                if (b) {
                    int destTime = i  * duration/100;
                    Log.d(TAG, "onProgressChanged: progress="+i+"destTime="+destTime);
                    String timeStr = format.format(destTime) + "/" + format.format(duration);
                    timeView.setText(timeStr);
                    Log.d(TAG, "onProgressChanged: 手动滑动进度条，setText为" + timeStr);

                }
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                Log.d(TAG, "onStartTrackingTouch");
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                int progress=seekBar.getProgress();
                int position = (int)progress*duration/100;
                videoView.seekTo(position);
                Log.d(TAG, "onStopTrackingTouch: 跳转至"+position+"播放");
            }
        });

    }

    @Override
    protected void onDestroy(){
        handler.removeCallbacks(runnable);
        Log.d(TAG, "onDestroy:");
        super.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        int progress=seekBar.getProgress();
        boolean play=videoView.isPlaying();
        outState.putInt(KEY_PROGRESS,progress);
        outState.putBoolean(KEY_PLAY,play);
    }


    private String getVideoPath(int resId) {
        return "android.resource://" + this.getPackageName() + "/" + resId;
    }

}
