package com.example.rafad;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
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

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class Adapter2 extends ArrayAdapter<postinfo> {

    public static final String TAG = "TAG";
    private final Activity context;
     List<postinfo> arrayList=new ArrayList<>();;
    StorageReference storageRef;
    FirebaseFirestore fStore;
    FirebaseAuth fAuth;
    String itemID;
    Button del;
    StorageReference profileRef;

    public Adapter2(@NonNull Activity context, @NonNull List<postinfo> arrayList) {
        super(context, R.layout.activity_historyitemwithedit, arrayList);
        this.arrayList=arrayList;
        Log.d(TAG,  "SIZE ADAPTER His=> " +arrayList.size());
        this.context=context;
    }



    public android.view.View getView(final int position, View view, ViewGroup parent) {

        itemID = arrayList.get(position).itemID;
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.activity_historyitemwithedit, null,true);
        fStore = FirebaseFirestore.getInstance();
        storageRef = FirebaseStorage.getInstance().getReference();
        fAuth = FirebaseAuth.getInstance();





        TextView desText = (TextView) rowView.findViewById(R.id.des);
        TextView titText = (TextView) rowView.findViewById(R.id.tit);
        final ImageView HisImage = (ImageView)rowView.findViewById(R.id.imageView10);
       // TextView catText = (TextView) rowView.findViewById(R.id.cat);
        del = rowView.findViewById(R.id.delete);
        TextView date = (TextView) rowView.findViewById(R.id.date);



        profileRef = storageRef.child(arrayList.get(position).imageID);
        profileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(HisImage);
                Log.d(TAG, "interrrrrr ");

            }
        });


        desText.setText(arrayList.get(position).des);
        titText.setText(arrayList.get(position).tit);
       // catText.setText(arrayList.get(position).cat);
        date.setText(arrayList.get(position).date);

        del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /* */
                ///////////////////
                new AlertDialog.Builder(getContext())

                        .setTitle("حذف")
                        .setMessage("هل انت متأكد من الحذف؟")
                        .setPositiveButton("نعم", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                profileRef.delete();
                                fStore.collection("item").document(itemID).delete();
                                context.startActivity(new Intent(context, don_3view.class));
                                Toast.makeText(getContext(), " تم الحذف بنجاح", Toast.LENGTH_SHORT).show();

                            }
                        }).setNegativeButton("الغاء", null).show();
                //////////////////////////////////////////////////


                AlertDialog dialog1;

            }
        });
        return rowView;
    }


}