package com.example.rafad;

import android.app.Activity;
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
    double Rating=0;

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
        RateDon = (Button) rowView.findViewById(R.id.RateDon);



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

        Log.d(TAG, "inter AdaptorBenReq BN: "+arrayList.get(position).BN);
        Log.d(TAG, "inter AdaptorBenReq st: "+st);
        Log.d(TAG, "inter AdaptorBenReq tit: "+arrayList.get(position).tit);
        titText.setText("اسم المتبرع: "+arrayList.get(position).BN);
        titText2.setText(" حالة الطلب: "+st);
        tit.setText("عنوان الطلب: "+arrayList.get(position).tit);
        Log.d(TAG, "inter AdaptorBenReq Rate: "+Rating);
        //for rate
        Rate.setText("تقييم  المتبرع: "+ Rating);


        return rowView;

    }



}