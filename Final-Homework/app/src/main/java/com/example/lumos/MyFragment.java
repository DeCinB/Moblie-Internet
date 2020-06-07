package com.example.lumos;

import android.animation.Animator;
import android.content.Context;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.airbnb.lottie.LottieAnimationView;
import com.airbnb.lottie.LottieListener;

/**
*@filename MyFragment
*@author BianDexin
*@date
*@describe
*@Email 1481410986@qq.com
**/
public class MyFragment extends Fragment {
    private static final String TAG="bdc Fragment";
    private static final String KEY_FEED_URL = "feed_url";
    private static final String KEY_POSITION="position";
    int position;
    private VideoView videoView;
    private LottieAnimationView like;
    private LottieAnimationView load;
    private LottieAnimationView play;
    private GestureDetector gestureDetector;


    public static MyFragment newInstance(int position,String feedUrl){
        MyFragment mf=new MyFragment();
        Bundle args = new Bundle();
        args.putString(KEY_FEED_URL,feedUrl);
        args.putInt(KEY_POSITION,position);
        mf.setArguments(args);
        return mf;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
            Bundle args = getArguments();
            String feedUrl=args.getString(KEY_FEED_URL);
            position=args.getInt(KEY_POSITION,-1);

            View view = inflater.inflate(R.layout.layout_fragment, container, false);

            videoView=view.findViewById(R.id.video_view_a);
            videoView.setVideoPath(feedUrl);
            videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    load.cancelAnimation();
                    load.setVisibility(View.GONE);

                    videoView.start();
                }
            });

            videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    play.setVisibility(View.VISIBLE);
                    play.setProgress(0);
                    play.playAnimation();
                }
            });

            gestureDetector=new GestureDetector(getContext(),new GestureDetector.SimpleOnGestureListener(){
            @Override/*单击播放、暂停*/
            public boolean onSingleTapConfirmed(MotionEvent e){
                Log.d(TAG, "onSingleTapConfirmed:");
                if(videoView.isPlaying()){
                    videoView.pause();
                    play.setVisibility(View.VISIBLE);
                    play.setProgress(0);
                    play.playAnimation();
                }
                else{
                    videoView.start();
                    play.cancelAnimation();
                    play.setVisibility(View.GONE);
                }
                return super.onSingleTapConfirmed(e);
            }

            @Override/*双击点赞*/
            public boolean onDoubleTap(MotionEvent e){
                Log.d(TAG, "onDoubleTap");
                if(like.getProgress()==0){
                    Log.d(TAG, "onDoubleTap:progress");
                    like.setVisibility(View.VISIBLE);
                    like.playAnimation();
                    String text=like.isAnimating()+" "+like.getVisibility();
                    Log.d(TAG, "onDoubleTap:"+text);
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
        videoView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                gestureDetector.onTouchEvent(event);
                return false;
            }
        });
            return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
        like=getActivity().findViewById(R.id.like_a);
        load=getActivity().findViewById(R.id.load);
        play=getActivity().findViewById(R.id.play);
    }
}
