package com.example.lumos;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;


/**
*@filename ApiService
*@author BianDexin
*@date
*@describe 网络请求接口
*@Email 1481410986@qq.com
**/
public interface ApiService {
    // https://beiyou.bytedance.com/api/invoke/video/invoke/video
    @GET("api/invoke/video/invoke/video")
    Call<List<VideoInfo>> getVideos();



}