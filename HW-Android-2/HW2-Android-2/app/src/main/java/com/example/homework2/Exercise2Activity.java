package com.example.homework2;

import android.os.Bundle;

import androidx.annotation.ContentView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;


public class Exercise2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ex2);

        final Button btn=findViewById(R.id.button3);
        final TextView tv1=findViewById(R.id.tv1);

        final ViewGroup rv=getWindow().getDecorView().findViewById(android.R.id.content);


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int count=0;

                Log.d("bdc","开始计算");
                count=getAllChildViewCount(rv)-1;
                Log.d("bdc","计算结束");

                String content="当前有"+count+"个view";
                TextView tv=findViewById(R.id.tv2);
                tv.setText(content);

            }
        });

    }

    public int getAllChildViewCount(View view) {

        //到达底部view
        if(!(view instanceof ViewGroup)){

            Log.d("viewid",String.valueOf(view.getId()));
            return 1;
        }

        ViewGroup vp=(ViewGroup) view;

        //不含子view
        if(vp.getChildCount()==0){
            Log.d("viewgroupid",String.valueOf(view.getId()));
            return 1;
        }

        int childNum=vp.getChildCount();
        int ViewCount=1;
        Log.d("viewgroupid",String.valueOf(view.getId()));

        for(int i=0;i<childNum;i++){
            ViewCount+=getAllChildViewCount(vp.getChildAt(i));
        }

        return ViewCount;
    }
}
