package com.example.homework2;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class ChartroomActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chartroom);

        Intent intent=getIntent();
        String ChartTo=intent.getStringExtra(Exercise3Activity.CHARTTO);

        TextView textView=findViewById(R.id.tv_with_name);
        textView.setText("和"+ChartTo+"的对话");

    }
}
