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


    public HistoryItemAdapter(@NonNull Activity context, @NonNull List<postinfo> arrayList) {
        super(context, R.layout.activity_history_item_adapter, arrayList);
        this.arrayList=arrayList;
        Log.d(TAG,  "SIZE ADAPTER His=> " +arrayList.size());
        this.context=context;
    }



    public android.view.View getView(final int position, View view, ViewGroup parent) {

        final String itemID=arrayList.get(position).itemID;
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.activity_history_item_adapter, null,true);
        fStore=FirebaseFirestore.getInstance();
        storageRef = FirebaseStorage.getInstance().getReference();





        TextView desText = (TextView) rowView.findViewById(R.id.des);
        final ImageView HisImage=(ImageView)rowView.findViewById(R.id.imageView10);
        TextView catText = (TextView) rowView.findViewById(R.id.cat);


      /*  storageRef.child(arrayList.get(position).imageID).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                // Got the download URL for 'users/me/profile.png'
             // HisImage.setImageURI(uri);

             final StorageReference fileRef = storageRef.child(arrayList.get(position).imageID);
        fileRef.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Picasso.get().load(uri).into(HisImage);
                    }
                });

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
//                Toast.makeText(HistoryItemAdapter.this,
  //                      " فشل تغيير الصورة ",
    //                    Toast.LENGTH_SHORT).show();
            }
        });


            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle any errors
                Log.d(TAG,  "SIZE ADAPTER HisAdaptor=> " );
            }
        });*/

        StorageReference storageRef =
                FirebaseStorage.getInstance().getReference();
        storageRef.child("gs://rafad11.appspot.com/"+arrayList.get(position).imageID).getDownloadUrl()
                .addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        // Got the download URL for 'users/me/profile.png'
                        HisImage.setImageURI(uri);
                    }
                })
                            .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            // Handle any errors
                        }
                    });


      String mid=arrayList.get(position).imageID.substring(7);
        final StorageReference fileRef = storageRef.child(mid);
        desText.setText(arrayList.get(position).des);
        //HisImage.setImageURI(Uri.parse(("gs://rafad11.appspot.com/"+arrayList.get(position).imageID)));
       // Glide.with(context).load(("gs://rafad11.appspot.com/"+arrayList.get(position).imageID)).into(HisImage);
       /* Glide.with( context )
                .load(fileRef)
                .into(HisImage);*/
        catText.setText(arrayList.get(position).cat);


        return rowView;

    }


}