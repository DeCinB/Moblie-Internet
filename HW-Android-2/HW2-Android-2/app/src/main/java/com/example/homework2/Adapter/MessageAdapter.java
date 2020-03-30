package com.example.homework2.Adapter;

import android.util.Log;
import android.view.View;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import android.content.res.Resources;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;


import com.example.homework2.R;
import com.example.homework2.model.Message;
import com.example.homework2.widget.CircleImageView;

import org.w3c.dom.Text;

import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageViewHolder> {

    private LayoutInflater inflater;
    private Context context;
    private List<Message> messageList;
    private final ListItemClickListener mOnClickListener;

    public MessageAdapter(Context context, List<Message> messageList,ListItemClickListener listener) {
        this.context = context;
        this.messageList = messageList;
        this.mOnClickListener = listener;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public MessageAdapter.MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.im_list_item, parent, false);
        return (new MessageAdapter.MessageViewHolder(view));
    }

    @Override
    public void onBindViewHolder(MessageAdapter.MessageViewHolder holder, int position) {
        Resources pR = context.getResources();
        Log.d("bdc", "onBingdViewHolder" + position);
        Message message = messageList.get(position);

        holder.title.setText(message.getTitle());
        holder.description.setText(message.getDescription());
        holder.time.setText(message.getTime());

        switch (message.getIcon()) {
            case "TYPE_STRANGER":
                holder.avatar.setImageDrawable(pR.getDrawable(R.mipmap.session_stranger));
                break;
            case "TYPE_USER":
                holder.avatar.setImageDrawable(pR.getDrawable(R.mipmap.icon_girl));
                break;
            case "TYPE_SYSTEM":
                holder.avatar.setImageDrawable(pR.getDrawable(R.mipmap.session_system_notice));
                break;
            case "TYPE_ROBOT":
                holder.avatar.setImageDrawable(pR.getDrawable(R.mipmap.session_robot));
                break;
            case "TYPE_GAME":
                holder.avatar.setImageDrawable(pR.getDrawable(R.mipmap.icon_micro_game_comment));
                break;
        }
        if (!message.isOfficial()) {
            holder.robot_notice.setVisibility(View.INVISIBLE);
        }

    }

    @Override
    public int getItemCount() {
        return messageList.size();
    }

    public class MessageViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final CircleImageView avatar;
        private final ImageView robot_notice;
        private final TextView title;
        private final TextView description;
        private final TextView time;

        public MessageViewHolder(@NonNull View itemView) {
            super(itemView);
            avatar = (CircleImageView) itemView.findViewById(R.id.iv_avatar);
            robot_notice = (ImageView) itemView.findViewById(R.id.robot_notice);
            title = (TextView) itemView.findViewById(R.id.tv_title);
            description = (TextView) itemView.findViewById(R.id.tv_description);
            time = (TextView) itemView.findViewById(R.id.tv_time);
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
