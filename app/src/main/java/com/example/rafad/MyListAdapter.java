package com.example.rafad;
import android.app.Activity;
import android.content.Intent;
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
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class MyListAdapter extends ArrayAdapter<benDataModel> {
    public static final String TAG = "TAG";
    private final Activity context;
    private final List<benDataModel> arrayList;
    FirebaseAuth fAuth;
    boolean check=false;
    public MyListAdapter(@NonNull Activity context, @NonNull List<benDataModel> arrayList) {
        super(context, R.layout.mylist, arrayList);
        this.arrayList=arrayList;
        Log.d(TAG,  "SIZE ADAPTER => " +arrayList.size());
        this.context=context;
    }



    public View getView(int position,View view,ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        fAuth = FirebaseAuth.getInstance();
        View rowView=inflater.inflate(R.layout.mylist, null,true);

        Button button=(Button)rowView.findViewById(R.id.button);

        final String UID=arrayList.get(position).getUID();
        //First accept
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "HIIIIIIIIIII ");
                final popUpClass popupclass = new popUpClass();
                popupclass.showPopupWindow(view);
                ///Approve///
                popupclass.Accept.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ///////////////////
                        if (popupclass.radioButton1.isChecked()|| popupclass.radioButton2.isChecked()||popupclass.radioButton3.isChecked()|| popupclass.radioButton4.isChecked()|| popupclass.radioButton5.isChecked()) {
                            FirebaseFirestore db = FirebaseFirestore.getInstance();
                            CollectionReference beneficiaries = db.collection("beneficiaries");
                            DocumentReference docRefB = beneficiaries.document(UID);
                            docRefB.update("flag", "Accepted");
                            Map<String, Object> user = new HashMap<>();
                            String state="";
                            if (popupclass.radioButton1.isChecked())
                                state="1";
                            if (popupclass.radioButton2.isChecked())
                                state="2";
                            if (popupclass.radioButton3.isChecked())
                                state="3";
                            if (popupclass.radioButton4.isChecked())
                                state="4";
                            if (popupclass.radioButton5.isChecked())
                                state="5";

                            user.put("State",state);
                            docRefB.update(user);
                            context.startActivity(new Intent(context, homepageAdmin.class));
                            return;
                        }else{
                            //Toast.makeText(MyListAdapter.this, " الرجاء ادخال الحالة ", Toast.LENGTH_LONG).show();
                            popupclass.settext.setText("يرجى ادخال الحالة");
                        }
                        //////////////////
                    }
                });
                ///end approve///


/*
                //Fullstar
                popupclass.FullStar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        FirebaseFirestore db=FirebaseFirestore.getInstance();
                        CollectionReference beneficiaries = db.collection("beneficiaries");
                        DocumentReference docRefB = beneficiaries.document(UID);
                        Map<String, Object> user = new HashMap<>();
                        user.put("State","1");
                        docRefB.update(user);
                        check=true;
                    }
                });

                //
                //Fullstar1
                popupclass.fullStar1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        FirebaseFirestore db=FirebaseFirestore.getInstance();
                        CollectionReference beneficiaries = db.collection("beneficiaries");
                        DocumentReference docRefB = beneficiaries.document(UID);
                        Map<String, Object> user = new HashMap<>();
                        user.put("State","2");
                        docRefB.update(user);
                        check=true;
                    }
                });

                //
                //Fullstar2
                popupclass.fullStar2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        FirebaseFirestore db=FirebaseFirestore.getInstance();
                        CollectionReference beneficiaries = db.collection("beneficiaries");
                        DocumentReference docRefB = beneficiaries.document(UID);
                        Map<String, Object> user = new HashMap<>();
                        user.put("State","3");
                        docRefB.update(user);
                        check=true;
                    }
                });

                //
                //Fullstar3
                popupclass.fullStar3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        FirebaseFirestore db=FirebaseFirestore.getInstance();
                        CollectionReference beneficiaries = db.collection("beneficiaries");
                        DocumentReference docRefB = beneficiaries.document(UID);
                        Map<String, Object> user = new HashMap<>();
                        user.put("State","4");
                        docRefB.update(user);
                        check=true;
                    }
                });

                //
                //Fullstar4
                popupclass.fullStar4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        FirebaseFirestore db=FirebaseFirestore.getInstance();
                        CollectionReference beneficiaries = db.collection("beneficiaries");
                        DocumentReference docRefB = beneficiaries.document(UID);
                        Map<String, Object> user = new HashMap<>();
                        user.put("State","5");
                        docRefB.update(user);
                        check=true;
                    }
                });
*/
                //


            }
        });//End Big accept button
        ///Disapprove///
        Button DisApprove=(Button) rowView.findViewById(R.id.button7);
        DisApprove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ///////////////////
                FirebaseFirestore db=FirebaseFirestore.getInstance();
                CollectionReference beneficiaries = db.collection("beneficiaries");
                DocumentReference docRefB = beneficiaries.document(UID);
                docRefB.update("flag", "Declined");
                context.startActivity(new Intent(context, homepageAdmin.class));
                return;
                //////////////////
            }
        });
        ///end Disapprove///


        TextView titleText =  rowView.findViewById(R.id.Username);
        TextView subtitleText =  rowView.findViewById(R.id.phone);
        TextView subtitleText1 =  rowView.findViewById(R.id.ssn);
        TextView subtitleText2 =  rowView.findViewById(R.id.Resd);
        TextView subtitleText3 =  rowView.findViewById(R.id.income);

        titleText.setText(arrayList.get(position).getUserName());
        subtitleText.setText("رقم الجوال:"+arrayList.get(position).getPhoneNumber());
        subtitleText1.setText("الهوية:"+arrayList.get(position).getSSN());
        subtitleText2.setText("نوع السكن:"+arrayList.get(position).getTypeOfResidence());
        subtitleText3.setText("الدخل:"+arrayList.get(position).getTotalIncome());

        return rowView;

    };


}



