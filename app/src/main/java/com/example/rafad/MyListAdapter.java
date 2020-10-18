package com.example.rafad;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
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
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import android.app.AlertDialog;
import android.content.DialogInterface;

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

      Button button=rowView.findViewById(R.id.button);

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

                            //////////////////////////////////////////////////////

                            docRefB.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                @Override
                                public void onSuccess(DocumentSnapshot documentSnapshot) {
                                    String email = documentSnapshot.getString("email");
                                            //(String) docRefB.get("email");
                                    String name = documentSnapshot.getString("userName");
                                            //(String) docRefB.get("userName");
                                    sendMail.sendMail(email, "تهانينا !  " , " أهلا بك عزيزنا  "+ name + " \n نرحب بك في تطبيق رَفَد ونود أن نخبرك بأنه تم قبول حسابك \n نتمنى لك تجربة مفيدة، دمت بود.  ");
                                }
                            });
                            ////////////////////////////////////////////////////////


                            context.startActivity(new Intent(context, homepageAdmin.class));
                            Toast.makeText(getContext(),"لقد تم قبول المستفيد بنجاح",Toast.LENGTH_SHORT).show();

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
        Button DisApprove=rowView.findViewById(R.id.button7);
        DisApprove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ///////////////////
                new AlertDialog.Builder(getContext())

                        .setTitle("رفض مستفيد")
                        .setMessage("هل أنت متأكد من رفض المستفيد؟")
                        .setPositiveButton("رفض", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                FirebaseFirestore db = FirebaseFirestore.getInstance();
                                CollectionReference beneficiaries = db.collection("beneficiaries");
                                DocumentReference docRefB = beneficiaries.document(UID);

                                docRefB.update("flag", "Declined");

                                //////////////////////////////////////////////////////

                                docRefB.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                    @Override
                                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                                        String email = documentSnapshot.getString("email");
                                        //(String) docRefB.get("email");
                                        String name = documentSnapshot.getString("userName");
                                        //(String) docRefB.get("userName");
                                        sendMail.sendMail(email, "تم رفض الحساب !  " , " أهلا بك عزيزنا  "+ name + " \n يسعد تطبيق رَفَد بانضمامك لنا ولكن يؤسفنا إخبارك بأنه تم رفض حسابك لعدم استيفاء الشروط   \n  يمكنك إعادة التسجيل وإدخال معلومات صالحة\n\n يوّد تطبيق رَفَد أن يلقاك قريبًا، دمت بود .  ");
                                    }
                                });
                                ////////////////////////////////////////////////////////
                                context.startActivity(new Intent(context, homepageAdmin.class));
                                Toast.makeText(getContext(), "لقد تم رفض المستفيد بنجاح", Toast.LENGTH_SHORT).show();


                                //dialog1.dismiss();

                            }
                        }).setNegativeButton("إلغاء", null).show();


                AlertDialog dialog1;


                //////////////////


            }

        });
        ///end Disapprove///


        TextView titleText =  rowView.findViewById(R.id.Username);
        TextView subtitleText =  rowView.findViewById(R.id.phone);
        TextView subtitleText1 =  rowView.findViewById(R.id.ssn);
        TextView subtitleText2 =  rowView.findViewById(R.id.Resd);
        TextView subtitleText3 =  rowView.findViewById(R.id.income);

        String SecNum;
        if(arrayList.get(position).getSecurityNumber().isEmpty())
         SecNum= "لا يوجد";
        else
            SecNum= arrayList.get(position).getSecurityNumber();

        titleText.setText(arrayList.get(position).getUserName());
        subtitleText.setText(arrayList.get(position).getPhoneNumber());
        subtitleText1.setText("رقم الضمان الإجتماعي: "+SecNum);
        subtitleText2.setText("نوع السكن: "+arrayList.get(position).getTypeOfResidence());
        subtitleText3.setText("الدخل: "+arrayList.get(position).getTotalIncome());

        return rowView;

    };


}



