package com.example.rafad;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class benChangePass extends AppCompatActivity {
    TextView benN, benE,benPh,benE2;
    Button backToProfile, DoneBen;
    FirebaseFirestore fStore;
    String userId ;
    FirebaseAuth fAuth;
    EditText password, confirmpassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ben_change_pass);
        backToProfile = findViewById(R.id.backtoProfilBen);
        DoneBen = findViewById(R.id.saveNewPassBen);
        password = findViewById(R.id.newBenPass);
        confirmpassword =findViewById(R.id.confirmNewBenPass);
        benN = findViewById(R.id.nameBen);
        benE =findViewById(R.id.emailBen2);
        benPh =findViewById(R.id.phoneBen);
        benE2 =findViewById(R.id.e2Ben);

        fStore= FirebaseFirestore.getInstance();
        fAuth = FirebaseAuth.getInstance();

        userId = fAuth.getCurrentUser().getUid();



        backToProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(benChangePass.this,benEditProfile.class);
                //pass the data
                i.putExtra("fullName",benN.getText().toString());
                i.putExtra("email",benE2.getText().toString());
                i.putExtra("phone", benPh.getText().toString());
                startActivity(i);
                finish();
            }
        });

        DoneBen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                change(view);

            }
        });


        DocumentReference documentReference = fStore.collection("beneficiaries").document(userId);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                benPh.setText(value.getString("phoneNumber"));
                benN.setText(value.getString("userName"));
                benE.setText(value.getString("email"));
                benE2.setText(value.getString("email"));
            }
        });


    }

    public void change (View v){
        String enteredPass = password.getText().toString();
        String enteredcon=confirmpassword.getText().toString();
        FirebaseUser user =fAuth.getCurrentUser();
        if (user!=null){
            if(!enteredPass.equals(enteredcon)){
                confirmpassword.setError(" كلمة المرور لاتتطابق مع تأكيد كلمة المرور ");
                return;
            }
            if(enteredPass.isEmpty()){
                password.setError(" لا يمكن ترك كلمة المرور فارغة ");
            }

            if(enteredcon.isEmpty()){
                confirmpassword.setError(" لا يمكن ترك تأكيد كلمة المرور فارغة ");
                return;
            }

            if(enteredPass.length()<8){
                password.setError(" يجب أن تحتوي كلمة المرور على 8 رموز أو أكثر  ");
                return;
            }


            user.updatePassword(password.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(benChangePass.this, "تم تغيير كلمة المرور بنجاح ", Toast.LENGTH_LONG).show();
                        Intent i = new Intent(benChangePass.this,benEditProfile.class);
                        //pass the data
                        i.putExtra("fullName",benN.getText().toString());
                        i.putExtra("email",benE.getText().toString());
                        i.putExtra("phone", benPh.getText().toString());
                        startActivity(i);
                        finish();


                    } else {
                        String e = task.getException().getMessage().toString();
                        if (e.equals("This operation is sensitive and requires recent authentication. Log in again before retrying this request.")){
                            Toast.makeText(benChangePass.this,  "هذه العملية حساسة وتتطلب مصادقة حديثة. قم بتسجيل الدخول مرة أخرى قبل إعادة محاولة هذا الطلب.", Toast.LENGTH_LONG).show();
                        }
                        Toast.makeText(benChangePass.this,  task.getException().getMessage() , Toast.LENGTH_LONG).show();
                        Intent i = new Intent(benChangePass.this,benEditProfile.class);
                        //pass the data
                        i.putExtra("fullName",benN.getText().toString());
                        i.putExtra("email",benE2.getText().toString());
                        i.putExtra("phone", benPh.getText().toString());
                        startActivity(i);
                        finish();


                    }
                }

            });

        }
    }





}