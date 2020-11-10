package com.example.rafad.ChatJava;


import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.rafad.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;


public class MessageAdapter extends ArrayAdapter<Chat> {


    StorageReference storageReference;
    public static final String TAG = "TAG";
    private final Activity context;
    private final List<Chat> arrayList;
    FirebaseAuth fAuth;
    boolean check = false;

    public MessageAdapter(@NonNull Activity context, @NonNull List<Chat> arrayList) {
        super(context, R.layout.chat_item_right, arrayList);
        this.arrayList = arrayList;
        this.context = context;
    }

    public View getView(final int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        fAuth = FirebaseAuth.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();
        boolean haveDate=false;
        View rowView;
        if (arrayList.get(position).type==0)
            rowView = inflater.inflate(R.layout.chat_item_right, null, true);
        else if (arrayList.get(position).type==1)
            rowView = inflater.inflate(R.layout.chat_item_left, null, true);
        else if (arrayList.get(position).type==21){
            haveDate=true;
            rowView = inflater.inflate(R.layout.chat_item_right0, null, true);}
        else {
            haveDate=true;
            rowView = inflater.inflate(R.layout.chat_item_left0, null, true);
        }

        //First accept
        Log.d(TAG, "PeopleAdapter ");
        TextView titleText = rowView.findViewById(R.id.show_message);
        TextView time = rowView.findViewById(R.id.time);
        if (haveDate){
            TextView date = rowView.findViewById(R.id.date);
            //Today ?
            Calendar calendar=Calendar.getInstance();
            SimpleDateFormat simpleDateFormat=new SimpleDateFormat("dd/MM/yyyy");
            String Today=simpleDateFormat.format(calendar.getTime());
            if (Today.equals(arrayList.get(position).getDate()))
            date.setText("اليوم");
            else
                date.setText(arrayList.get(position).getDate());
        }
        haveDate=false;



        titleText.setText(arrayList.get(position).getMessage());
        time.setText(arrayList.get(position).getTime());


        return rowView;

    }


}