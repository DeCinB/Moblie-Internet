package com.example.helloworld;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       final RadioGroup houseGroup=findViewById(R.id.radioGroup);
       final ImageView photo=findViewById(R.id.photo);
       Button btn1=findViewById(R.id.btn1);//引用控件
       final RadioButton btnGryf=findViewById(R.id.btnGryf);
       final RadioButton btnHuff=findViewById(R.id.btnHuff);
       final RadioButton btnRaven=findViewById(R.id.btnRaven);
       final RadioButton btnSlyth=findViewById(R.id.btnSlyth);

 //       final ImageView img1=findViewById(R.id.img1);
        final TextView tv1=findViewById(R.id.tv1);

       houseGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
          @Override
           public void onCheckedChanged(RadioGroup group, int checkedId) {
               RadioButton ch=findViewById(checkedId);
               String s="Your choose:"+ch.getText();
              tv1.setText(s);
              Log.d("MainActivity","choose");
          }
      });
        //设置触摸监听器
       btn1.setOnClickListener(new View.OnClickListener(){
          @Override
          public void onClick(View v){
              Log.d("MainActivity","click button");
              if(btnGryf.isChecked()){
                  photo.setImageResource(R.drawable.gryffindor);
                  Log.d("MainActivity","Gryffindor");
              }
              else if(btnHuff.isChecked()){
                  photo.setImageResource(R.drawable.hufflepuff);
                  Log.d("MainActivity","Hufflepuff");
              }
              else if(btnRaven.isChecked()){
                  photo.setImageResource(R.drawable.ravenclaw);
                  Log.d("MainActivity","Ravenclaw");
              }
              else if(btnSlyth.isChecked()){
                  photo.setImageResource(R.drawable.slytherin);
                  Log.d("MainActivity","Slytherin");
              }
         }
       });
    }
}
