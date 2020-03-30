package com.example.homework2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btn_ex1=findViewById(R.id.btn_ex1);
        Button btn_ex2=findViewById(R.id.btn_ex2);
        Button btn_ex3=findViewById(R.id.btn_ex3);
        btn_ex1.setOnClickListener(new MyListener());
        btn_ex2.setOnClickListener(new MyListener());
        btn_ex3.setOnClickListener(new MyListener());
        }

        private class MyListener implements View.OnClickListener{
        public void onClick(View v){
            switch (v.getId()){
                case R.id.btn_ex1:
                    startActivity(new Intent(MainActivity.this,Exercise1Activity.class));
                    break;
                case R.id.btn_ex2:
                    startActivity(new Intent(MainActivity.this,Exercise2Activity.class));
                    break;
                case R.id.btn_ex3:
                    startActivity(new Intent(MainActivity.this,Exercise3Activity.class));
            }
        }
    }

}
