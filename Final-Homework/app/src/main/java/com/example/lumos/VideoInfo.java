package com.example.lumos;

import com.google.gson.annotations.SerializedName;

import java.util.List;
/**
 *@filename VideoInfo
 *@author BianDexin
 *@date
 *@describe &#x89c6;&#x9891;&#x4fe1;&#x606f;&#x7c7b;
 *@Email 1481410986@qq.com
 **/
public class VideoInfo {
    @SerializedName("_id")
    public String id;
    @SerializedName("feedurl")
    public String feedUrl;
    @SerializedName("nickname")
    public String nickname;
    @SerializedName("description")
    public String description;
    @SerializedName("avatar")
    public String avatar;
    @SerializedName("likecount")
    public int likeCount;

    @Override
    public String toString(){
        return "id:"+id+
                "feedUrl:"+feedUrl+
                "nickname:"+nickname+
                "description:"+description+
                "avatar:"+avatar+
                "likeCount:"+likeCount;
    }

}
