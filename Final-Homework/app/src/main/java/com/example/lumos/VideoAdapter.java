package com.example.lumos;

import android.animation.Animator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.media.MediaPlayer;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;
/**
*@filename VideoAdapter
*@author BianDexin
*@date   
*@describe TODO
*@Email 1481410986@qq.com
**/
public class VideoAdapter extends RecyclerView.Adapter <VideoAdapter.mViewHolder>{
    private static final String TAG = "VideoAdapter";
    private LayoutInflater inflater;
    private Context context;
    private List<VideoInfo> videoInfoList;
    private final ListItemClickListener mOnClickListener;

    public VideoAdapter(Context context, List<VideoInfo> videoInfoList, ListItemClickListener listener) {
        this.context = context;
        this.videoInfoList=videoInfoList;
        this.mOnClickListener = listener;
        inflater = LayoutInflater.from(context);
    }

    public void setData(List<VideoInfo> videos) {
        videoInfoList=videos;
    }
    @Override
    public VideoAdapter.mViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item, parent, false);
        return (new VideoAdapter.mViewHolder(view));
    }

    @Override
    public void onBindViewHolder(VideoAdapter.mViewHolder holder, int position) {

        Log.d("bdc", "onBingdViewHolder" + position);
        VideoInfo video = videoInfoList.get(position);

        Glide.with(context)
                .load(video.avatar)
                .error(R.mipmap.error)
                .placeholder(R.mipmap.default_pre)
                .apply(new RequestOptions().centerCrop())
                .into(holder.preImg);

        holder.name.setText(video.nickname);
        holder.description.setText(video.description);
        String likeText=" "+video.likeCount;
        holder.likeCount.setText(likeText);

    }

    @Override
    public int getItemCount() {
        return videoInfoList==null? 0:videoInfoList.size();
    }

    public class mViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final ImageView preImg;
        private final TextView name;
        private final TextView description;
        private final TextView likeCount;
        private final ImageView heart;


        public mViewHolder(@NonNull View itemView) {
            super(itemView);
            preImg=itemView.findViewById(R.id.preView);
            name=itemView.findViewById(R.id.nickname);
            description=itemView.findViewById(R.id.description);
            likeCount=itemView.findViewById(R.id.like_count);
            heart=itemView.findViewById(R.id.heart);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int clickedPosition=getAdapterPosition();
            if(mOnClickListener!=null)
            {
                mOnClickListener.onListItemClick(clickedPosition);
            }
        }
    }

    public interface ListItemClickListener {
        void onListItemClick (int position);
    }
}
