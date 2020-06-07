package com.example.lumos;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

import com.airbnb.lottie.LottieAnimationView;
/**
*@filename VideoActivity
*@author BianDexin
*@date
*@describe 视频播放页
*@Email 1481410986@qq.com
**/
public class VideoActivity extends AppCompatActivity {

    private static final String TAG="bdc VideoActivity";
    private static final String KEY_INDEX = "index";
    private static final String KEY_FEED_URL = "feed_url";
    private static final String KEY_NAME = "nick_name";
    private static final String KEY_DESCRIPTION = "description";
    private static final String KEY_LIKE_COUNT = "like";
    private static final String KEY_STAR="star";
    private TextView name;
    private TextView description;
    private TextView likeCount;
    private FullScreenVideoView videoView;
    private LottieAnimationView like;
    private LottieAnimationView load;
    private LottieAnimationView play;
    private LottieAnimationView star;
    private GestureDetector gestureDetector;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);

        Intent intent=getIntent();
        int index=intent.getIntExtra(KEY_INDEX,-1);
        String nameText="@"+intent.getStringExtra(KEY_NAME);
        String desText=intent.getStringExtra(KEY_DESCRIPTION);
        String likeText=intent.getStringExtra(KEY_LIKE_COUNT);
        String feedUrl=intent.getStringExtra(KEY_FEED_URL);

        name=findViewById(R.id.nick_name);
        name.setText(nameText);

        description=findViewById(R.id.description);
        description.setText(desText);

        likeCount=findViewById(R.id.like_count);
        likeCount.setText(likeText);

        videoView=findViewById(R.id.video_view);

        like=findViewById(R.id.like);
        load=findViewById(R.id.load);
        play=findViewById(R.id.play);
        star=findViewById(R.id.star);

        star.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                star.playAnimation();//播放收藏动画
                Intent intent_back=new Intent();
                intent_back.putExtra(KEY_STAR,index);
                setResult(222,intent_back);//设置返回值
            }
        });

        gestureDetector=new GestureDetector(this,new GestureDetector.SimpleOnGestureListener(){
            @Override/*单击播放、暂停*/
            public boolean onSingleTapConfirmed(MotionEvent e){
                Log.d(TAG, "onSingleTapConfirmed:");
                if(videoView.isPlaying()){
                    videoView.pause();
                    play.setVisibility(View.VISIBLE);
                    play.playAnimation();
                }
                else{
                    play.cancelAnimation();
                    play.setVisibility(View.GONE);
                    videoView.start();
                }
                return super.onSingleTapConfirmed(e);
            }

            @Override/*双击点赞*/
            public boolean onDoubleTap(MotionEvent e){
                Log.d(TAG, "onDoubleTap");
                if(like.getProgress()==0){
                    like.setVisibility(View.VISIBLE);
                    like.playAnimation();
                    like.addAnimatorListener(new Animator.AnimatorListener() {
                        @Override
                        public void onAnimationStart(Animator animation) {
                        }
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            like.setVisibility(View.GONE);
                            like.removeAllAnimatorListeners();
                        }
                        @Override
                        public void onAnimationCancel(Animator animation) {
                        }
                        @Override
                        public void onAnimationRepeat(Animator animation) {
                        }
                    });
                }
                return super.onDoubleTap(e);
            }

        });

        videoView.setVideoPath(feedUrl);
        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener(){
            @SuppressLint("ClickableViewAccessibility")
            @Override
            public void onPrepared(MediaPlayer mp) {
                Log.d(TAG, "onPrepared:");
                load.cancelAnimation();
                load.setVisibility(View.GONE);
                videoView.start();
            }
        });

        videoView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                gestureDetector.onTouchEvent(event);
                return false;
            }
        });
        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                play.setVisibility(View.VISIBLE);
                play.playAnimation();
            }
        });
    }
}
