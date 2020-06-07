package com.example.lumos;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
*@filename Main2Activity
*@author BianDexin
*@date
*@describe 一个Item全屏
*@Email 1481410986@qq.com
**/
public class Main2Activity extends AppCompatActivity {
    private static final String TAG = "bdc";
    private List<VideoInfo> videos;
    private FragmentStateAdapter mFragmentStateAdapter;
    private TextView name;
    private TextView description;
    private TextView likeCount;
    private LottieAnimationView like;
    private LottieAnimationView load;
    private LottieAnimationView play;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewpager);

        name=findViewById(R.id.nick_name);
        description=findViewById(R.id.description);
        likeCount=findViewById(R.id.like_count);
        like=findViewById(R.id.like_a);
        load=findViewById(R.id.load);
        play=findViewById(R.id.play);

        mFragmentStateAdapter=new FragmentStateAdapter(getSupportFragmentManager(),this.getLifecycle()) {
            @NonNull
            @Override
            public Fragment getItem(int position) {
                String feedUrl=videos.get(position).feedUrl;
                return MyFragment.newInstance(position,feedUrl);
            }

            @Override
            public int getItemCount() {
                return videos.size();
            }
        };

        /*获取视频资源*/
        videos = new ArrayList<>();
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
                    if(videos.size()>0)
                        mFragmentStateAdapter.notifyDataSetChanged();
                }
            }
            @Override
            public void onFailure(Call<List<VideoInfo>> call, Throwable t) {
                Log.d(TAG, "onFailure:");
            }
        });


        ViewPager2 viewPager2=findViewById(R.id.view_pager);
        viewPager2.setAdapter(mFragmentStateAdapter);

        //切换页面，更新显示的视频信息（作者、简介、赞数）
        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                VideoInfo videoInfo=videos.get(position);
                String nameText="@"+videoInfo.nickname;
                name.setText(nameText);
                description.setText(videoInfo.description);
                likeCount.setText(" "+videoInfo.likeCount);
                //重置点赞动画
                like.setProgress(0);
                //加载动画可见、播放
                load.setVisibility(View.VISIBLE);
                load.playAnimation();
                //重播按钮动画
                play.setVisibility(View.GONE);
            }
        });
    }
}
