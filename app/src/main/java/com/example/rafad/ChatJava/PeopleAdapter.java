package com.example.rafad.ChatJava;


import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;

import com.example.rafad.R;
import com.example.rafad.benDataModel;
import com.example.rafad.homepageAdmin;
import com.example.rafad.popUpClass;
import com.example.rafad.sendMail;
import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import android.app.AlertDialog;
import android.content.DialogInterface;


public class PeopleAdapter extends ArrayAdapter<PeopleModel> {

    public static final String TAG = "TAG";
    private final Activity context;
    private final List<PeopleModel> arrayList;
    FirebaseAuth fAuth;
    boolean check=false;
    public PeopleAdapter(@NonNull Activity context, @NonNull List<PeopleModel> arrayList) {
        super(context, R.layout.item_person, arrayList);
        this.arrayList=arrayList;
        Log.d(TAG,  "SIZE ADAPTER => " +arrayList.size());
        this.context=context;
    }

    public View getView(int position,View view,ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        fAuth = FirebaseAuth.getInstance();
        View rowView=inflater.inflate(R.layout.item_person, null,true);

        Button button=rowView.findViewById(R.id.button);

        final String UID=arrayList.get(position).getUID();
        //First accept
        Log.d(TAG, "PeopleAdapter ");
        TextView titleText =  rowView.findViewById(R.id.Username);
        TextView subtitleText =  rowView.findViewById(R.id.lastMessage);



        titleText.setText(arrayList.get(position).getName());
        subtitleText.setText(arrayList.get(position).getLastMsg());
        return rowView;
}




}