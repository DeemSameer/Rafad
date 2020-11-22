package com.example.rafad.Block;

import android.app.Activity;
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

import androidx.annotation.NonNull;

import com.example.rafad.ChatJava.MessageActivity;
import com.example.rafad.R;
import com.example.rafad.benDataModel;
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

public class BlockBenList extends ArrayAdapter<BenModelBlock> {
public static final String TAG = "TAG";
private final Activity context;
    FirebaseFirestore fStore;

private final List<BenModelBlock> arrayList;
        FirebaseAuth fAuth;
    StorageReference storageReference;

public BlockBenList(@NonNull Activity context, @NonNull List<BenModelBlock> arrayList) {
        super(context, R.layout.blockbenitem, arrayList);
        this.arrayList=arrayList;
        Log.d(TAG,  "SIZE ADAPTER => " +arrayList.size());
        this.context=context;
        }

    public View getView(final int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        fAuth = FirebaseAuth.getInstance();
        fStore= FirebaseFirestore.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();
        View rowView = inflater.inflate(R.layout.blockbenitem, null, true);

        TextView titleText =  rowView.findViewById(R.id.Username);
        TextView subtitleText =  rowView.findViewById(R.id.phone);
        TextView subtitleText1 =  rowView.findViewById(R.id.ssn);


        titleText.setText(arrayList.get(position).getName());
        subtitleText.setText(arrayList.get(position).getPhoneNumber());
        subtitleText1.setText(arrayList.get(position).getCount());
        final ImageView profileImageViewChat = (ImageView) rowView.findViewById(R.id.profile_image);

        try {

            StorageReference profileRef = storageReference.child("users/" + arrayList.get(position).getUID() + "profile.jpg");
            Log.d(TAG, "before12 People Adapter" + profileRef);
            profileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    Picasso.get().load(uri).into(profileImageViewChat);
                    Log.d(TAG, "interrrrrr People Adapter");


                }
            });
        }catch (Exception e){

        }

        Button acceptt= rowView.findViewById(R.id.button);
        acceptt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopUpBlockConfirmation pop=new PopUpBlockConfirmation();
                pop.showPopupWindow(view);
                pop.Accept.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //Change the status to delete
                        CollectionReference beneficiaries = fStore.collection("beneficiaries");
                        DocumentReference docRefB = beneficiaries.document(arrayList.get(position).getUID());
                        docRefB.update("flag", "Blocked");//block
                        DocumentReference docRef= fStore.collection("Reports").document(arrayList.get(position).getUID());
                        docRef.delete();//delete so the item will not be shown again.
                    }
                });

              //  context.startActivity(new Intent(, blockBen.class));

            }
        });

        Button declined= rowView.findViewById(R.id.button7);
        declined.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DocumentReference docRef= fStore.collection("Reports").document(arrayList.get(position).getUID());
                docRef.delete();
            }
        });


        return rowView;

    };
    }

