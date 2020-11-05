package com.example.rafad.ChatJava;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.rafad.R;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MyViewHolder>{
    public static final int MSG_TYPE_LEFT=0;
    public static final int MSG_TYPE_RIGHT=1;
    public static final String TAG = "TAG";
    Context Context;
    ArrayList<Chat> Chat;

    public MessageAdapter(android.content.Context context, ArrayList<com.example.rafad.ChatJava.Chat> chat) {
        Context = context;
        Chat = chat;
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView Message;
        MyViewHolder(View itemView){
            super(itemView);
            this.Message=(TextView)itemView.findViewById(R.id.show_message);
        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater li = LayoutInflater.from(parent.getContext());
        View view;
        if (viewType==MessageAdapter.MSG_TYPE_RIGHT)
            view=li.inflate(R.layout.chat_item_right,parent,false);
        else
            view=li.inflate(R.layout.chat_item_left,parent,false);

        MyViewHolder myViewHolder=new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        TextView Message=holder.Message;
        Message.setText(Chat.get(position).getMessage()+"");//+"" for casting //help some times
    }

    @Override
    public int getItemCount() {
        return Chat.size() ;
    }

    //وصلت 33:49 من هنا
    // https://www.youtube.com/watch?v=amV-6aSTYEE&t=1622s
}

