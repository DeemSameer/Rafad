package com.example.rafad.ChatJava;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
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
/*
public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MyViewHolder>{
    public static final int MSG_TYPE_LEFT=0;
    public static final int MSG_TYPE_RIGHT=1;
    public static final String TAG = "TAG";
    public static int viewTypeOut;
    Context Context;
    ArrayList<Chat> Chat;

    public MessageAdapter(android.content.Context context, ArrayList<com.example.rafad.ChatJava.Chat> chat) {
        Context = context;
        Chat = chat;
    }

    @Override
    public int getItemViewType(int position) {

        if (Chat.get(position).i==0) {
            return 0;
        }
        return 1;
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
        Log.d(TAG, "viewType  "+viewType);

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
        Message.setText(Chat.get(position).getMessage()+"");//+"" for casting //help some time
    }

    public static void setType(int type){
        viewTypeOut=type;
    }
    @Override
    public int getItemCount() {
        return Chat.size() ;
    }



    //وصلت 33:49 من هنا
    // https://www.youtube.com/watch?v=amV-6aSTYEE&t=1622s
}
*/


public class MessageAdapter extends RecyclerView.Adapter {

    private static final String TAG = "RecyclerAdapter";
    List<Chat> chats;
    public static int viewTypeOut;
    Context Context;

    public MessageAdapter(android.content.Context context, ArrayList<com.example.rafad.ChatJava.Chat> chat) {
        Context = context;
        chats = chat;
    }

    public MessageAdapter(List<Chat> chats) {
        this.chats = chats;
    }

    @Override
    public int getItemViewType(int position) {

        if (chats.get(position).type==0) {
            return 0;
        }
        return 1;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view;

        if (viewType == 0) {
            view = layoutInflater.inflate(R.layout.chat_item_right, parent, false);
            return new ViewHolderOne(view);
        }
        else {
            view = layoutInflater.inflate(R.layout.chat_item_left, parent, false);
            return new ViewHolderOne(view);
        }
    }

    TextView Message;

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

            ViewHolderOne viewHolderOne = (ViewHolderOne) holder;
            Message.setText(chats.get(position).getMessage() + "");

    }

    @Override
    public int getItemCount() {
        return chats.size();
    }

    class ViewHolderOne extends RecyclerView.ViewHolder {

        public ViewHolderOne(@NonNull View itemView) {
            super(itemView);
            Message = (TextView) itemView.findViewById(R.id.show_message);
        }
    }


}
