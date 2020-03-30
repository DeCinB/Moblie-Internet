package com.example.homework2;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Exercise1Activity extends AppCompatActivity {

    private static final String TAG="bdc";

    private static final String ON_CREATE = "onCreate";
    private static final String ON_START = "onStart";
    private static final String ON_RESUME = "onResume";
    private static final String ON_PAUSE = "onPause";
    private static final String ON_STOP = "onStop";
    private static final String ON_RESTART = "onRestart";
    private static final String ON_DESTROY = "onDestroy";
    private static final String ON_SAVE_INSTANCE_STATE = "onSaveInstanceState";
    private static final String ON_RESTORE_INSTANCE_STATE = "onRestoreInstanceState";
    private static final String LIFECYCLE_CALLBACKS_TEXT_KEY = "callbacks";
    private TextView LifecycleDisplay;

    private void logAndAppend(String lifecycleEvent){
        Log.d(TAG,"Lifecycle Event:"+lifecycleEvent);
        LifecycleDisplay.append(lifecycleEvent+'\n');
        //Log.d(TAG,"lifeDisplay:"+LifecycleDisplay.getText());
    }


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ex1);
        LifecycleDisplay=findViewById(R.id.tv1);

        if(savedInstanceState!=null){
            if(savedInstanceState.containsKey(LIFECYCLE_CALLBACKS_TEXT_KEY)){
                String savedContent = (String) savedInstanceState.get(LIFECYCLE_CALLBACKS_TEXT_KEY);
                savedContent=savedContent+ON_STOP+"\n"+ON_DESTROY+"\n";
                LifecycleDisplay.setText(savedContent);
               // Log.d(TAG,"reset text");
            }
        }

        logAndAppend(ON_CREATE);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        logAndAppend(ON_RESTART);
    }

    @Override
    protected void onStart() {
        super.onStart();
        logAndAppend(ON_START);
    }

    @Override
    protected void onResume() {
        super.onResume();
        logAndAppend(ON_RESUME);
    }


    @Override
    protected void onPause() {
        super.onPause();
        logAndAppend(ON_PAUSE);
    }

    @Override
    protected void onStop() {
        logAndAppend(ON_STOP);
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        logAndAppend(ON_DESTROY);
        super.onDestroy();

    }

     @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        logAndAppend(ON_SAVE_INSTANCE_STATE);
        String content = LifecycleDisplay.getText().toString();//当前已有的log 提取出来
        outState.putString(LIFECYCLE_CALLBACKS_TEXT_KEY, content); //把内容存储起来
    }

    @Override
   protected void onRestoreInstanceState(Bundle savedInstanceState){
       super.onRestoreInstanceState(savedInstanceState);
       logAndAppend(ON_RESTORE_INSTANCE_STATE);
   }
}
