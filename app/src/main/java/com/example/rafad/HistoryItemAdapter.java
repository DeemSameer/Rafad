package com.example.rafad;

import android.app.Activity;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.List;
import android.graphics.Bitmap;

public class HistoryItemAdapter extends ArrayAdapter<postinfo> {

    public static final String TAG = "TAG";
    private final Activity context;
    private final List<postinfo> arrayList;
    StorageReference storageRef;
    FirebaseFirestore fStore;
    FirebaseAuth fAuth;


    public HistoryItemAdapter(@NonNull Activity context, @NonNull List<postinfo> arrayList) {
        super(context, R.layout.activity_history_item_adapter, arrayList);
        this.arrayList=arrayList;
        Log.d(TAG,  "SIZE ADAPTER His=> " +arrayList.size());
        this.context=context;
    }



    public android.view.View getView(final int position, View view, ViewGroup parent) {

        final String itemID = arrayList.get(position).itemID;
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.activity_history_item_adapter, null,true);
        fStore = FirebaseFirestore.getInstance();
        storageRef = FirebaseStorage.getInstance().getReference();
        fAuth = FirebaseAuth.getInstance();




        TextView desText = (TextView) rowView.findViewById(R.id.des);
        TextView titText = (TextView) rowView.findViewById(R.id.tit);
        final ImageView HisImage = (ImageView)rowView.findViewById(R.id.imageView10);
        //TextView catText = (TextView) rowView.findViewById(R.id.cat);
        TextView date = (TextView) rowView.findViewById(R.id.date);



        StorageReference profileRef = storageRef.child(arrayList.get(position).imageID);
        profileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(HisImage);
                Log.d(TAG, "interrrrrr ");

            }
        });





        desText.setText(arrayList.get(position).des);
        titText.setText(arrayList.get(position).tit);
        //catText.setText(arrayList.get(position).cat);
        date.setText(arrayList.get(position).date);
        return rowView;

    }



}