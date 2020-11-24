package com.example.rafad;

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
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AdabterBenReq extends ArrayAdapter<postinfo> {

    public static final String TAG = "TAG";
    private final Activity context;
    private final List<postinfo> arrayList;
    StorageReference storageRef;
    FirebaseFirestore fStore;
    FirebaseAuth fAuth;
    String st;
    Button RateDon;
    double Rating;

    public AdabterBenReq(@NonNull Activity context, @NonNull List<postinfo> arrayList) {
        super(context, R.layout.activity_adapter_d, arrayList);
        this.arrayList=arrayList;
        Log.d(TAG,  "SIZE ADAPTER His=> " +arrayList.size());
        this.context=context;
    }



    public View getView(final int position, View view, ViewGroup parent) {

        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.activity_adabter_ben_req, null,true);
        fStore=FirebaseFirestore.getInstance();
        storageRef = FirebaseStorage.getInstance().getReference();
        fAuth = FirebaseAuth.getInstance();
        //for Rating
        RateDon = (Button) rowView.findViewById(R.id.RateDonButton);



        TextView titText = (TextView) rowView.findViewById(R.id.name);
        TextView titText2 = (TextView) rowView.findViewById(R.id.status);
        TextView tit = (TextView) rowView.findViewById(R.id.benReqTit);
        //for  rate
        TextView Rate = (TextView) rowView.findViewById(R.id.rate);

        final ImageView HisImage=(ImageView)rowView.findViewById(R.id.imageView10);





        StorageReference profileRef = storageRef.child(arrayList.get(position).imageID);
        profileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(HisImage);
                Log.d(TAG, "inter AdaptorBenReq");

            }
        });


        if(arrayList.get(position).isRe.equals("no"))
        { st="مرفوض";}
        if (arrayList.get(position).isRe.equals("Pending"))
        { st="قيد الانتظار";}
        if (arrayList.get(position).isRe.equals("yes")) {
            st = "مقبول";
        }


/////////////////////////////////////////////////////////////////////////////////////////////////////



        CollectionReference donators = fStore.collection("donators");
        DocumentReference docRefB = donators.document(arrayList.get(position).UID);

        docRefB.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Log.d(TAG, "inter AdaptorBenReq doc DocumentSnapshot data: " + document.getData());
                        Rating=document.getDouble("Rate");
                        //get("Rate");
                    } else {
                        Log.d(TAG, "No such document");
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });



        // DocumentSnapshot document12= fStore.collection("donators").document(arrayList.get(position).UID).get().getResult();


        //DocumentReference document12= fStore.collection("donators").document(arrayList.get(position).UID);
        Log.d(TAG, "inter AdaptorBenReq ID: "+arrayList.get(position).UID);



/////////////////////////////////////////////////////////////////////////////////////////////////////

        if(arrayList.get(position).isRe.equals("yes")) {
            //btnDone.setVisibility(View.GONE);
            RateDon.setVisibility(View.VISIBLE);
        } else {
            RateDon.setVisibility(View.INVISIBLE);
            // btnDone.setVisibility(View.VISIBLE);
        }

/////////////////////////////////////////////////////////////////////////////////////////////////////


        Log.d(TAG, "inter AdaptorBenReq rate 173: "+arrayList.get(position).rate);
/////////////////////////////////////////////////////////////////////////////////////////////////////


        RateDon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                /// already rated
                if (arrayList.get(position).IsRated.equals("yes")) {
                    final popUpRated popupRated = new popUpRated();
                    popupRated.showPopupWindow(view);
                    Log.d(TAG, "inter AdaptorBenReq rate 188: "+arrayList.get(position).rate);
                    popupRated.AcceptRate.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            ///////////////////
                            context.startActivity(new Intent(context, benReqView.class));
                            Toast.makeText(getContext(), "لقد تم التقييم من قبل ", Toast.LENGTH_SHORT).show();
                            //////////////////
                        }
                    });
                }else {


                    final popUpRate popupRate = new popUpRate();
                    popupRate.showPopupWindow(view);
                    ///Approve Rate///
                    popupRate.AcceptRate.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            ///////////////////
                            if (popupRate.radioButton1.isChecked() || popupRate.radioButton2.isChecked() || popupRate.radioButton3.isChecked() || popupRate.radioButton4.isChecked() || popupRate.radioButton5.isChecked()) {
                                FirebaseFirestore db = FirebaseFirestore.getInstance();
                                CollectionReference donators = db.collection("donators");
                                DocumentReference docRefB = donators.document(arrayList.get(position).UID);

                                double rate = 0;
                                if (popupRate.radioButton1.isChecked())
                                    rate = 1;
                                if (popupRate.radioButton2.isChecked())
                                    rate = 2;
                                if (popupRate.radioButton3.isChecked())
                                    rate = 3;
                                if (popupRate.radioButton4.isChecked())
                                    rate = 4;
                                if (popupRate.radioButton5.isChecked())
                                    rate = 5;

                                Rating = (Rating + rate) / 2;
                                docRefB.update("Rate", Rating);

                                CollectionReference items = db.collection("item");
                                DocumentReference docRefItem = items.document(arrayList.get(position).itemID);
                                Log.d(TAG, "inter AdaptorBenReqr itemID: " + arrayList.get(position).itemID);
                                docRefItem.update("IsRated", "yes");
                                //RateDon.setVisibility(View.INVISIBLE);


                                //////////////////////////////////////////////////////


                                context.startActivity(new Intent(context, benReqView.class));
                                Toast.makeText(getContext(), "لقد تم تقييم المتبرع بنجاح", Toast.LENGTH_SHORT).show();

                                //return;
                            } else {
                                //Toast.makeText(MyListAdapter.this, " الرجاء ادخال الحالة ", Toast.LENGTH_LONG).show();
                                popupRate.settext.setText("يرجى إدخال التقييم");
                            }
                            //////////////////
                        }
                    });
                }
                ///end approve///

            }
        });//End Big accept button



/////////////////////////////////////////////////////////////////////////////////////////////////////

        Log.d(TAG, "inter AdaptorBenReq BN: "+arrayList.get(position).UID);
        Log.d(TAG, "inter AdaptorBenReq st: "+st);
        Log.d(TAG, "inter AdaptorBenReq rate: "+arrayList.get(position).rate);
        titText.setText("اسم المتبرع: "+arrayList.get(position).BN);
        titText2.setText(" حالة الطلب: "+st);
        tit.setText("عنوان الطلب: "+arrayList.get(position).tit);

        Log.d(TAG, "inter AdaptorBenReqr Rating: "+Rating);
        Rating=Rating+0.0005;
        Log.d(TAG, "inter AdaptorBenReqr Rating1: "+Rating);
        Log.d(TAG, "inter AdaptorBenReqr Rating3: "+String.format("%.2f", Rating));
        Log.d(TAG, "inter AdaptorBenReqr Rating4: "+String.format("%.2f", Rating));
        Rate.setText("تقييم  المتبرع: "+ String.format("%.2f", Rating));


        return rowView;

    }



}