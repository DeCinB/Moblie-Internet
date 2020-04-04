package com.example.chapter3.homework;


import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;

import java.util.List;


public class PlaceholderFragment extends Fragment {

    private static final String TAG = "PlaceholderFragment";
    public View mview;
    public RecyclerView mrecyclerView;
    public Adapter madapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO ex3-3: 修改 fragment_placeholder，添加 loading 控件和列表视图控件

        mview=inflater.inflate(R.layout.fragment_placeholder, container, false);
        mrecyclerView=(RecyclerView) mview.findViewById(R.id.recyclerview);
        mrecyclerView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
        madapter=new Adapter(getContext(),5) ;
        Log.d(TAG,"count:"+madapter.getItemCount());
        mrecyclerView.setAdapter(madapter);

        return mview;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        final LottieAnimationView animationView=mview.findViewById(R.id.animation_view);
        final ObjectAnimator inanimator=ObjectAnimator.ofFloat(mview.findViewById(R.id.recyclerview),"alpha",0,1);
        final ObjectAnimator outanimator=ObjectAnimator.ofFloat(mview.findViewById(R.id.animation_view),"alpha",1,0);
        inanimator.setRepeatCount(0);
        outanimator.setRepeatCount(0);
        inanimator.setDuration(1000);
        outanimator.setDuration(1000);

        Log.d(TAG,"onActivityCreated");
        getView().postDelayed(new Runnable() {
            @Override
            public void run() {
                // 这里会在 5s 后执行
                // TODO ex3-4：实现动画，将 lottie 控件淡出，列表数据淡入
                outanimator.start();
                animationView.setVisibility(View.GONE);

                inanimator.start();
            }
        }, 5000);
    }


    class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder>{

        public int count;
        private  Context context;
        private LayoutInflater inflater;

        public Adapter(Context context,int count){
            this.count=count;
            this.context=context;
            this.inflater=LayoutInflater.from(context);

        }

        @Override
        public Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

            View view=inflater.inflate(R.layout.item,viewGroup,false);
            Log.d(TAG,"onCreate"+i);
            return (new Adapter.ViewHolder(view));
        }

        @Override
        public void onBindViewHolder(@NonNull Adapter.ViewHolder viewHolder, int i) {
            viewHolder.tv.setText("item"+i);
            Log.d(TAG,"onBind"+i);
        }
        @Override
        public int getItemCount() {
            return count;
        }


        public class ViewHolder extends RecyclerView.ViewHolder{
            TextView tv;

            public ViewHolder(View view){
                super(view);
                tv=view.findViewById(R.id.tv);
            }
        }
    }
}


