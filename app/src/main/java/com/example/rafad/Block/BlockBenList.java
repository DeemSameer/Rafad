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

import com.example.rafad.R;
import com.example.rafad.sendMail;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.List;

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


        titleText.setText("الإسم: "+arrayList.get(position).getPhoneNumber());
        subtitleText.setText("رقم الجوال: "+arrayList.get(position).getName());
        subtitleText1.setText("عدد مرات الإبلاغ: "+arrayList.get(position).getCount());
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
                        //send mail to the benficary
                        sendMail(arrayList.get(position).getPhoneNumber(),arrayList.get(position).getEmail());
                        context.startActivity(new Intent(context, blockBen.class));

                    }
                });

            }
        });

        Button declined= rowView.findViewById(R.id.button7);
        declined.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopUpBlockConfirmation2 pop=new PopUpBlockConfirmation2();
                pop.showPopupWindow(view);
                pop.Accept.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        DocumentReference docRef= fStore.collection("Reports").document(arrayList.get(position).getUID());
                        docRef.delete();
                        context.startActivity(new Intent(context, blockBen.class));

                    }
                });

            }
        });


        return rowView;

    };

    void sendMail(String name , String email){
        sendMail.sendMail(email, "لقد تم حظر حسابك! " , " <html>\n" +
                "<head>\n" +
                "  <style>\n" +
                "html {\n" +
                "  height: 100%;\n" +
                "}\n" +
                "body {\n" +
                "  position: relative;\n" +
                "  height: 100%;\n" +
                "  margin: 0;\n" +
                " box-shadow: inset 33.33vw 0px 0px #523B60, inset 66.66vw 0px 0px white, inset 99.99vw 0px 0px  #EF8D6E;\n" +
                "\n" +
                "\n" +
                "\n" +
                "  color: #523B60;\n" +
                "\n" +
                "\n" +
                "}\n" +
                "\n" +
                ".inner{\n" +
                "position: relative;\n" +
                "  height: 100%;\n" +
                "  margin: 0;\n" +
                "\t\tbackground: -webkit-linear-gradient(left, #523B60, #EF8D6E 80%);\n" +
                "}\n" +
                "\n" +
                ".center {\n" +
                "    text-align: center;\n" +
                "\n" +
                "\twidth: 50%;\n" +
                "  height: 50%;\n" +
                "  overflow: auto;\n" +
                "  margin: auto;\n" +
                "  position: absolute;\n" +
                "  top: 0; left: 0; bottom: 0; right: 0;\n" +
                "  background:  white;\n" +
                "  opacity:0.4;\n" +
                "      border-radius:10px;\n" +
                "\t  padding:10px;\n" +
                "\n" +
                "\n" +
                "}\n" +
                "\n" +
                "  </style>\n" +
                "</head>\n" +
                "<body>\n" +
                "\n" +
                "<div class=\"inner\">\n" +
                "<hr>\n" +
                "<br>\n" +
                "<br>\n" +
                "<div class=\"center\">\n" +
                "<br><br>\n" +
                "  <h1> مرحبًا عزيزنا المستفيد\n" +name+
                "  </h1>\n" +
                "  <br>\n" +
                "\n" +
                "    <h4>\n" +
                "\tيأسف تطبيق رفد باخبارك بحظر حسابك\n" +
                "\t\n" +
                "</h4>\n" +
                "<h5>لقد تم التبليغ عن حسابك وبعد النظر في امرك تم حظر الحساب\n" +
                "</h5>\n" +
                "  <br>\n" +
                "\n" +
                "  <p>يوّد تطبيق رفد أن يلقاك قريبًا، دمت بود</p>\n" +
                "  <br>\n" +
                "\n" +
                "  <p>Email: rafad.app@gmail.com</p>\n" +
                "\n" +
                "</div>\n" +
                "<br>\n" +
                "<br>\n" +
                "<br>\n" +
                "</div>\n" +
                "\n" +
                "\n" +
                "</body>\n" +
                "</html>  ");
    }
    }

