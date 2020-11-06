package com.example.rafad.ChatJava;


import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.bumptech.glide.Glide;
import com.example.rafad.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;
import com.xwray.groupie.kotlinandroidextensions.ViewHolder;

import java.util.List;


public class PeopleAdapter extends ArrayAdapter<PeopleModel> {


    StorageReference storageReference;
    public static final String TAG = "TAG";
    private final Activity context;
    private final List<PeopleModel> arrayList;
    FirebaseAuth fAuth;
    boolean check = false;

    public PeopleAdapter(@NonNull Activity context, @NonNull List<PeopleModel> arrayList) {
        super(context, R.layout.item_person, arrayList);
        this.arrayList = arrayList;
        this.context = context;
    }

    public View getView(final int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        fAuth = FirebaseAuth.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();
        View rowView = inflater.inflate(R.layout.item_person, null, true);

        Button button = rowView.findViewById(R.id.button);

        final String UID = arrayList.get(position).getUID();
        //First accept
        Log.d(TAG, "PeopleAdapter ");
        TextView titleText = rowView.findViewById(R.id.Username);
        TextView subtitleText = rowView.findViewById(R.id.lastMessage);
        TextView timetext = rowView.findViewById(R.id.timetext);
        TextView datetext = rowView.findViewById(R.id.datetext);
        TextView unread = rowView.findViewById(R.id.unread);


        final ImageView profileImageViewChat = (ImageView) rowView.findViewById(R.id.imageView9);

        Log.d(TAG, "before1 People Adapter");

        StorageReference profileRef = storageReference.child("users/" + arrayList.get(position).getUID() + "profile.jpg");
        Log.d(TAG, "before12 People Adapter" + profileRef);
        profileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
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
        unread.setText("");



        rowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "Entered  ");
                MessageActivity.callMe(arrayList.get(position).getUID(),arrayList.get(position).getName());
                context.startActivity(new Intent(context, MessageActivity.class));
            }
        });

        return rowView;

    }
    public PeopleModel getItem(int position){
        return arrayList.get(position);
    }

}