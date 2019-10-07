package com.example.elonchat.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.elonchat.MessageActivity;
import com.example.elonchat.Model.Chat;
import com.example.elonchat.Model.User;
import com.example.elonchat.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.Viewholder> {


    public static final int MSG_TYPE_LEFT = 0;
    public static final int MSG_TYPE_RIGHT = 1;
    private Context context;
    private List<Chat> chats;
    private String imageUrl;

    FirebaseUser firebaseUser;

    public MessageAdapter (Context context, List<Chat> chats, String imageUrl){

        this.chats = chats;
        this.context = context;
        this.imageUrl = imageUrl;
    }

    @NonNull
    @Override
    public MessageAdapter.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if (viewType == MSG_TYPE_RIGHT) {

            View view = LayoutInflater.from(context).inflate(R.layout.chat_item_right, parent, false);
            return new MessageAdapter.Viewholder(view);
        }else {
            View view = LayoutInflater.from(context).inflate(R.layout.chat_item_left, parent, false);
            return new MessageAdapter.Viewholder(view);
        }

    }

    @Override
    public void onBindViewHolder(@NonNull MessageAdapter.Viewholder holder, int position) {
Chat chat = chats.get(position);

holder.showmsg.setText(chat.getMessage());

if (imageUrl.equals("default")){

    holder.profileImage.setImageResource(R.mipmap.ic_launcher);
}else {
    Glide.with(context).load(imageUrl).into(holder.profileImage);
}

    }

    @Override
    public int getItemCount() {
        return chats.size();
    }

    public class Viewholder extends  RecyclerView.ViewHolder{

        public TextView showmsg;
        public ImageView profileImage;


        public Viewholder(View itemView){
            super(itemView);

            showmsg = itemView.findViewById(R.id.showmsg);
            profileImage = itemView.findViewById(R.id.profile_picture);
        }

    }

    @Override
    public int getItemViewType(int position) {
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if (chats.get(position).getSender().equals(firebaseUser.getUid())){
            return MSG_TYPE_RIGHT;
        }else {
            return MSG_TYPE_LEFT;
        }
    }
}

