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
        this.arrayList = arrayList;
        Log.d(TAG, "SIZE ADAPTER => " + arrayList.size());
        this.context = context;
    }


   public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.mylist, null, true);



       TextView titleText =  rowView.findViewById(R.id.Username);
       TextView subtitleText =  rowView.findViewById(R.id.phone);
       TextView subtitleText1 =  rowView.findViewById(R.id.ssn);
       TextView subtitleText2 =  rowView.findViewById(R.id.Resd);
       TextView subtitleText3 =  rowView.findViewById(R.id.income);

       titleText.setText(arrayList.get(position).getUserName());
       subtitleText.setText("رقم الجوال:"+arrayList.get(position).getPhoneNumber());
       subtitleText1.setText("الهوية:"+arrayList.get(position).getSSN());
       subtitleText2.setText("نوع السكن:"+arrayList.get(position).getTypeOfResidence());
       subtitleText3.setText("الدخل:"+arrayList.get(position).getTotalIncome());




       return rowView;

   };
}



