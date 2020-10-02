package com.example.rafad;


import android.app.Activity;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.List;

class MyListAdapter extends ArrayAdapter<benDataModel> {
    public static final String TAG = "TAG";
    private final Activity context;
    private final List<benDataModel> arrayList;

    public MyListAdapter(@NonNull Activity context, @NonNull List<benDataModel> arrayList) {
        super(context, R.layout.mylist, arrayList);
        this.arrayList=arrayList;
        Log.d(TAG,  "SIZE ADAPTER => " +arrayList.size());
        this.context=context;
    }



    public View getView(int position,View view,ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.mylist, null,true);

        TextView titleText = (TextView) rowView.findViewById(R.id.title);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);
        TextView subtitleText = (TextView) rowView.findViewById(R.id.subtitle);
        TextView subtitleText1 = (TextView) rowView.findViewById(R.id.subtitle2);
        TextView subtitleText2 = (TextView) rowView.findViewById(R.id.subtitle3);
        TextView subtitleText3 = (TextView) rowView.findViewById(R.id.subtitle4);
        TextView subtitleText4 = (TextView) rowView.findViewById(R.id.subtitle5);
        TextView subtitleText5 = (TextView) rowView.findViewById(R.id.subtitle6);
        TextView subtitleText6 = (TextView) rowView.findViewById(R.id.subtitle7);



        titleText.setText(arrayList.get(position).getUserName());
        imageView.setImageResource(R.drawable.chaticon);
        subtitleText.setText(arrayList.get(position).getPhoneNumber());
        subtitleText1.setText(arrayList.get(position).getTypeOfResidence());
        subtitleText2.setText(arrayList.get(position).getSSN());
        subtitleText3.setText(arrayList.get(position).getEmail());
        subtitleText4.setText(arrayList.get(position).getEmail());
        subtitleText5.setText(arrayList.get(position).getSecurityNumber());
        subtitleText6.setText(arrayList.get(position).getTotalIncome());




        return rowView;

    };
}


