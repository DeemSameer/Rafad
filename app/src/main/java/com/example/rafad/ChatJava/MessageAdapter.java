package com.example.rafad.ChatJava;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.Adapter;

import com.example.rafad.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder>{

    public static final int MSG_TYPE_LEFT=0;
    public static final int MSG_TYPE_RIGHT=1;

    StorageReference storageReference;
    public static final String TAG = "TAG";


    private  Context mContext;
    private  final List<Chat> mChat;



    public MessageAdapter( @NonNull Context mContext, @NonNull List<Chat> mChat) {

        super(mContext, mChat);
        this.mChat=mChat;
        this.mContext=mContext;
    }



    public View getView(final int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        fAuth = FirebaseAuth.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();
        View rowView = inflater.inflate(R.layout.item_person, null, true);

        Button button = rowView.findViewById(R.id.button);

        //  final String UID = arrayList.get(position).getUID();
        //First accept
        Log.d(TAG, "MessageAdapter ");
        TextView titleText = rowView.findViewById(R.id.Username);
        TextView subtitleText = rowView.findViewById(R.id.lastMessage);
        TextView timetext = rowView.findViewById(R.id.timetext);
        TextView datetext = rowView.findViewById(R.id.datetext);
        final ImageView profileImageViewChat = (ImageView) rowView.findViewById(R.id.imageView9);

        Log.d(TAG, "before1 MessageAdapter");

        //StorageReference profileRef = storageReference.child("users/" + arrayList.get(position).getUID() + "profile.jpg");
        //  Log.d(TAG, "before12 People Adapter" + profileRef);
      /*  profileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(profileImageViewChat);
                Log.d(TAG, "interrrrrr People Adapter");

            }
        });

        titleText.setText(arrayList.get(position).getName());
        subtitleText.setText(arrayList.get(position).getLastMsg());
        timetext.setText(arrayList.get(position).getTime());
        datetext.setText(arrayList.get(position).getDate());


        rowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "Entered  ");
                MessageActivity.callMe(arrayList.get(position).getUID(),arrayList.get(position).getName());
                context.startActivity(new Intent(context, MessageActivity.class));
            }
        });*/

        return rowView;

    }
    public MessageAdapter getItem(int position){
        return arrayList.get(position);
    }


    @NonNull
    @Override
    public MessageAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull MessageAdapter.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    private class ViewHolder {
    }
}
