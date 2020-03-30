package com.example.homework2;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.OrientationHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.homework2.Adapter.LinearAdapter;
import com.example.homework2.Adapter.MessageAdapter;
import com.example.homework2.model.Item;
import com.example.homework2.model.Message;
import com.example.homework2.model.PullParser;
import com.example.homework2.model.TestDecoration;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class Exercise3Activity extends AppCompatActivity implements MessageAdapter.ListItemClickListener{

    public static final String CHARTTO="person chart to";
    private RecyclerView mRecyclerView;
    private MessageAdapter mAdapter;
    List<Message> messages;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
          super.onCreate(savedInstanceState);
          setContentView(R.layout.activity_ex3);

          initData();
          initView();


    }
    public void initData(){
        //load data from assets/data.xml
        try {
            InputStream assetInput = getAssets().open("data.xml");
            messages = PullParser.pull2xml(assetInput);
            for (Message message : messages) {
                // Log.d("init message:",message.toString());
            }
        } catch (Exception exception) {
            Log.d("init message", "failed");
            exception.printStackTrace();
        }
    }

    public void initView(){
        mRecyclerView=(RecyclerView)findViewById(R.id.mRecyclerView);
        LinearLayoutManager mLayoutManager=new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter=new MessageAdapter(this,messages,this);
        mRecyclerView.setAdapter(mAdapter);

    }

    @Override
    public void onListItemClick(int clickedItemIndex){
        Log.d("bdc", "onListItemClick: position"+clickedItemIndex);
        Intent intent =new Intent(Exercise3Activity.this,ChartroomActivity.class);
        String ChartTo=messages.get(clickedItemIndex).getTitle();
        intent.putExtra(CHARTTO,ChartTo);
        startActivity(intent);
    }
}

/*
public class Exercise3Activity extends AppCompatActivity{

    private RecyclerView mRecyclerView;
    private LinearAdapter mRecyclerAdapter;
    List<Item> viewData = new ArrayList<Item>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ex3);

        initData();
        initView();
    }
    public void initData(){
            viewData.add(new Item("第1张图", R.mipmap.gryff));
            viewData.add(new Item("第2张图", R.mipmap.gryffindor));
            viewData.add(new Item("第3张图", R.mipmap.huff));
            viewData.add(new Item("第4张图", R.mipmap.hufflepuff));
            viewData.add(new Item("第5张图", R.mipmap.ichuff));
            viewData.add(new Item("第6张图", R.mipmap.icslyth));
            viewData.add(new Item("第7张图", R.mipmap.icraven));
            viewData.add(new Item("第8张图", R.mipmap.icraven));
    }

    public void initView(){
        mRecyclerView=findViewById(R.id.mRecyclerView);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        /*第一个参数 Context
         *第二个参数：布局方向LinearLayout.VERTICAL垂直和LinearLayout.HORIZONTAL水平
         *第三个参数：表示是否从最后的Item数据开始显示，true表示是，false就是正常显示—从开头显示。*/


        //设置布局管理器
        //mRecyclerView.setLayoutManager(mLayoutManager);
        //设置adapter
        //mRecyclerAdapter=new LinearAdapter(this,viewData);
        //mRecyclerView.setAdapter(mRecyclerAdapter);

       // mRecyclerView.addItemDecoration(new TestDecoration(this,OrientationHelper.HORIZONTAL));
    //}
//}

