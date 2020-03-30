package com.example.homework2.Adapter;

import android.content.res.Resources;
import android.util.Log;
import android.view.View;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.homework2.R;
import com.example.homework2.model.Item;

import java.util.List;

public class LinearAdapter extends RecyclerView.Adapter<LinearAdapter.myViewHolder> {

    private LayoutInflater inflater;
    //LayoutInflater这个类的作用类似于findViewById()。
    //不同点是LayoutInflater是用来找res/layout/下的xml布局文件
    private Context context;
    private List<Item> itemList;
    private Resources resources;

    public LinearAdapter(Context context,List<Item> itemList) {
        this.context = context;
        this.itemList = itemList;
        this.resources = context.getResources();
        inflater = LayoutInflater.from(context);
    }

    /*创建item条目
    * 1.获取view
    * 2.创建一个内部Holder，返回view
    * */
    @Override
    public LinearAdapter.myViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.number_list_item,parent, false);
        LinearAdapter.myViewHolder holder=new LinearAdapter.myViewHolder(view);
        //这边可以做一些属性设置，甚至事件监听绑定

        view.setOnLongClickListener (new View.OnLongClickListener () {
            @Override
            public boolean onLongClick(View view) {
                return false;
            }
        });
        view.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View view) {
                Log.d("bdc","click");
            }
        });

        return holder;
    }

    //绑定数据
    @Override
    public void onBindViewHolder(LinearAdapter.myViewHolder holder, int position) {
        final Item item=itemList.get(position);
        holder.tv_title.setText(item.title);
        holder.img.setImageResource(item.ImgRid);
    }

    //条目个数
    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public class myViewHolder extends RecyclerView.ViewHolder {

         public TextView tv_title;
         public ImageView img;
         public myViewHolder(View view) {
            super(view);
            tv_title= (TextView) view.findViewById(R.id.item_title);
            img=(ImageView)view.findViewById(R.id.item_image);
        }
    }


}
