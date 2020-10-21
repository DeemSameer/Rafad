package com.example.rafad;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;
import android.app.Activity;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class AdapterD extends ArrayAdapter<postinfo> {

    public static final String TAG = "TAG";
    private final Activity context;
    private final List<postinfo> arrayList;
    StorageReference storageRef;
    FirebaseFirestore fStore;
    FirebaseAuth fAuth;
    Button request;
    String UID1;




    public AdapterD(@NonNull Activity context, @NonNull List<postinfo> arrayList) {
        super(context, R.layout.activity_adapter_d, arrayList);
        this.arrayList=arrayList;
        Log.d(TAG,  "SIZE ADAPTER His=> " +arrayList.size());
        this.context=context;
    }




    public android.view.View getView(final int position, View view, ViewGroup parent) {

        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.activity_adapter_d, null,true);
        fStore=FirebaseFirestore.getInstance();
        storageRef = FirebaseStorage.getInstance().getReference();
        fAuth = FirebaseAuth.getInstance();


        TextView titText = (TextView) rowView.findViewById(R.id.name);
        TextView titText2 = (TextView) rowView.findViewById(R.id.status);

        final ImageView HisImage=(ImageView)rowView.findViewById(R.id.imageView10);

        Button DisApprove=rowView.findViewById(R.id.button7);
        DisApprove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ///////////////////
                new AlertDialog.Builder(getContext())

                        .setTitle("رفض الطلب")
                        .setMessage("هل انت متأكد من رفض الطلب؟")
                        .setPositiveButton("نعم", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                FirebaseFirestore db = FirebaseFirestore.getInstance()
                                        ;
                                final String itemID=arrayList.get(position).itemID;
                                UID1=FirebaseAuth.getInstance().getCurrentUser().getUid();
                                CollectionReference beneficiaries = db.collection("item");
                                DocumentReference docRefB = beneficiaries.document(itemID);
                                docRefB.update("isRequested", "no");
                                context.startActivity(new Intent(context, requests.class));
                                Toast.makeText(getContext(), "لقد تم رفض الطلب بنجاح", Toast.LENGTH_SHORT).show();
                                //dialog1.dismiss();
                            }
                        }).setNegativeButton("الغاء", null).show();
                AlertDialog dialog1;    }
        });
        Button Approve=rowView.findViewById(R.id.button);
        Approve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ///////////////////
                new AlertDialog.Builder(getContext())

                        .setTitle("قبول الطلب")
                        .setMessage("هل انت متأكد من قبول الطلب؟")
                        .setPositiveButton("نعم", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                FirebaseFirestore db = FirebaseFirestore.getInstance()
                                        ;
                                final String itemID=arrayList.get(position).itemID;
                                UID1=FirebaseAuth.getInstance().getCurrentUser().getUid();
                                CollectionReference beneficiaries = db.collection("item");
                                DocumentReference docRefB = beneficiaries.document(itemID);
                                docRefB.update("isRequested", "yes");
                                context.startActivity(new Intent(context, requests.class));
                                Toast.makeText(getContext(), "لقد تم قبول الطلب بنجاح", Toast.LENGTH_SHORT).show();
                                //dialog1.dismiss();
                            }
                        }).setNegativeButton("الغاء", null).show();
                AlertDialog dialog1;    }
        });




        StorageReference profileRef = storageRef.child(arrayList.get(position).imageID);
        profileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(HisImage);
                Log.d(TAG, "inter Adaptor D");

            }
        });




        titText.setText(arrayList.get(position).BN);
        titText2.setText(arrayList.get(position).BS);


        return rowView;

    }
}