package com.example.rafad;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.List;

public class HistoryItemAdapter extends ArrayAdapter<postinfo> {

    public static final String TAG = "TAG";
    private final Activity context;
    private final List<postinfo> arrayList;
    FirebaseAuth fAuth;
    StorageReference storageRef;
    FirebaseFirestore fStore;

    public HistoryItemAdapter(@NonNull Activity context, @NonNull List<postinfo> arrayList) {
        super(context, R.layout.activity_history_item_adapter, arrayList);
        this.arrayList=arrayList;
        Log.d(TAG,  "SIZE ADAPTER His=> " +arrayList.size());
        this.context=context;
    }



    public android.view.View getView(int position, View view, ViewGroup parent) {

        LayoutInflater inflater=context.getLayoutInflater();
        fAuth = FirebaseAuth.getInstance();
        View rowView=inflater.inflate(R.layout.activity_history_item_adapter, null,true);
        //fStore=FirebaseFirestore.getInstance().getReference();
        storageRef = FirebaseStorage.getInstance().getReference();


        ImageView HisImage=(ImageView)rowView.findViewById(R.id.imageView10);

        storageRef.child("users/me/profile.png").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                // Got the download URL for 'users/me/profile.png'
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle any errors
            }
        });




        return rowView;

    }


}