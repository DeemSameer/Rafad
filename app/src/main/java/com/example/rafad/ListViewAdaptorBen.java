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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ListViewAdaptorBen extends ArrayAdapter<postinfo> {


    public static final String TAG = "TAG";
    private final Activity context;
    private final List<postinfo> arrayList;
    StorageReference storageRef;
    FirebaseFirestore fStore;
    FirebaseAuth fAuth;
    Button request;
    String UID1;




    public ListViewAdaptorBen(@NonNull Activity context, @NonNull List<postinfo> arrayList) {
        super(context, R.layout.activity_list_view_adaptor_ben, arrayList);
        this.arrayList=arrayList;
        Log.d(TAG,  "SIZE ADAPTER His=> " +arrayList.size());
        this.context=context;
    }




    public android.view.View getView(final int position, View view, ViewGroup parent) {

        final String itemID=arrayList.get(position).itemID;
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.activity_list_view_adaptor_ben, null,true);
        fStore=FirebaseFirestore.getInstance();
        storageRef = FirebaseStorage.getInstance().getReference();
        fAuth = FirebaseAuth.getInstance();
        request=rowView.findViewById(R.id.button11);
        final String UID=arrayList.get(position).getUID();



        request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ///////////////////
                new AlertDialog.Builder(getContext())

                        .setTitle("طلب عنصر ")
                        .setMessage("هل انت متأكد من طلب العنصر؟")
                        .setPositiveButton("نعم", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                FirebaseFirestore db = FirebaseFirestore.getInstance()
                                        ;

                                UID1=FirebaseAuth.getInstance().getCurrentUser().getUid();
                                CollectionReference beneficiaries = db.collection("item");
                                DocumentReference docRefB = beneficiaries.document(itemID);
                                docRefB.update("isRequested", "Pending");
                                context.startActivity(new Intent(context, homePage.class));
                                docRefB.update("benID", UID1);
                                arrayList.get(position).setBID(UID1);
                                Toast.makeText(getContext(), "لقد تم طلب العنصر بنجاح", Toast.LENGTH_SHORT).show();
                                //dialog1.dismiss();
                            }
                        }).setNegativeButton("الغاء", null).show();
                AlertDialog dialog1;    }
        });


        TextView desText = (TextView) rowView.findViewById(R.id.desAdabtorBen);
        TextView titText = (TextView) rowView.findViewById(R.id.titAdabtorBen);
        final ImageView HisImage=(ImageView)rowView.findViewById(R.id.imageView10);
        TextView catText = (TextView) rowView.findViewById(R.id.catAdabtorBen);





        StorageReference profileRef = storageRef.child(arrayList.get(position).imageID);
        profileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(HisImage);
                Log.d(TAG, "inter Adaptor Benf ");

            }
        });



        desText.setText(arrayList.get(position).des);
        titText.setText(arrayList.get(position).tit);
        catText.setText(arrayList.get(position).cat);

        return rowView;

    }
}