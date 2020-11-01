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
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.bumptech.glide.Glide;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.List;

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
        TextView catText = (TextView) rowView.findViewById(R.id.cat);
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
        catText.setText(arrayList.get(position).cat);
        date.setText(replaceArabicNumbers(arrayList.get(position).date));
        return rowView;

    }
    private String replaceArabicNumbers(CharSequence original) {
        if (original != null) {
            return original.toString().replaceAll("١","1")
                    .replaceAll("٢","2")
                    .replaceAll("٣","3")
                    .replaceAll("٣","3")
                    .replaceAll("٤","4")
                    .replaceAll("٥","5")
                    .replaceAll("٦","6")
                    .replaceAll("٧","7")
                    .replaceAll("٨","8")
                    .replaceAll("٨","9")
                    .replaceAll("٠","0");
        }

        return null;
    }


}