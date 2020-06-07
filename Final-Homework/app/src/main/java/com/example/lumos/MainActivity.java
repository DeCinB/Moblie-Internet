package com.example.lumos;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.widget.VideoView;

import com.airbnb.lottie.LottieAnimationView;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
*@filename MainActivity
*@author BianDexin
*@date
*@describe 视频流消息列表
*@Email 1481410986@qq.com
**/
public class MainActivity extends AppCompatActivity implements VideoAdapter.ListItemClickListener {
    private static final String TAG = "bdc";
    private static final String KEY_INDEX = "index";
    private static final String KEY_FEED_URL = "feed_url";
    private static final String KEY_NAME = "nick_name";
    private static final String KEY_DESCRIPTION = "description";
    private static final String KEY_LIKE_COUNT = "like";
    private static final String KEY_STAR="star";

    private RecyclerView mRecyclerView;
    private VideoAdapter mVideoAdapter;
    private Button btnTotal;
    private Button btnStar;
    private List<VideoInfo> videos;
    private List<VideoInfo> starVideos;
    private boolean isStar=false;
    private LinearLayoutManager mLayoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        initData();

    }

    public void initData() {
        videos = new ArrayList<>();
        starVideos=new ArrayList<>();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://beiyou.bytedance.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Log.d(TAG, "retrofit start");
        ApiService apiService = retrofit.create(ApiService.class);
        apiService.getVideos().enqueue(new Callback<List<VideoInfo>>() {
            @Override
            public void onResponse(Call<List<VideoInfo>> call, Response<List<VideoInfo>> response) {
                Log.d(TAG, "onResponse");
                if(response.body()!=null){
                    videos=response.body();
                    if(videos.size()>0){
                        mVideoAdapter.setData(videos);
                        mVideoAdapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onFailure(Call<List<VideoInfo>> call, Throwable t) {
                Log.d(TAG, "onFailure:");
            }
        });
    }

    public void initView() {
        btnTotal=findViewById(R.id.total);
        btnStar=findViewById(R.id.myStar);
        mRecyclerView = findViewById(R.id.mRecyclerView);
        mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mVideoAdapter = new VideoAdapter(this, videos, this);
        mRecyclerView.setAdapter(mVideoAdapter);

        btnTotal.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if(isStar) {
                    mVideoAdapter.setData(videos);
                    mVideoAdapter.notifyDataSetChanged();
                    isStar=false;
                }
                else
                    Toast.makeText(getApplicationContext(),"已为全部视频",Toast.LENGTH_LONG).show();
            }
        });

        btnStar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if(!isStar){
                    mVideoAdapter.setData(starVideos);
                    mVideoAdapter.notifyDataSetChanged();
                    isStar=true;
                }
                else
                    Toast.makeText(getApplicationContext(),"已为收藏视频",Toast.LENGTH_LONG).show();
            }
        });
    }
        @Override
        public void onListItemClick(int clickedItemIndex){
            Log.d("bdc", "onListItemClick: position"+clickedItemIndex);
            VideoInfo video=isStar? starVideos.get(clickedItemIndex):videos.get(clickedItemIndex);

            Intent intent=new Intent(MainActivity.this,VideoActivity.class);

            intent.putExtra(KEY_INDEX,clickedItemIndex);
            intent.putExtra(KEY_NAME,video.nickname);
            intent.putExtra(KEY_FEED_URL,video.feedUrl);
            intent.putExtra(KEY_DESCRIPTION,video.description);
            intent.putExtra(KEY_LIKE_COUNT,video.likeCount+" ");

            startActivityForResult(intent,222);
        }

        @Override
        protected void onActivityResult(int requestCode, int resultCode, Intent data){
            super.onActivityResult(requestCode,resultCode,data);
            if(requestCode==222) {
                int starIndex=data.getIntExtra(KEY_STAR,-1);
                if(starIndex!=-1)
                    starVideos.add(videos.get(starIndex));
            }
        }
}
