package com.example.rafad;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.*;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;


public class login extends AppCompatActivity {
    public static final String TAG = "TAG";
    EditText lEmail, lpassword;
    Button lLogin,lSignup;
    FirebaseAuth fAuth;
    TextView textView1;
    FirebaseUser firebaseUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login2);

        lEmail = findViewById(R.id.email);
        lpassword = findViewById(R.id.editTextTextPassword2);
        lLogin = findViewById(R.id.login);
        lSignup = findViewById(R.id.buttonsign);
        fAuth = FirebaseAuth.getInstance();
        textView1=findViewById(R.id.forget);



        textView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(login.this , forgetp.class));
                finish();

            }
        });
        lSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lSignup.setBackgroundColor(getResources().getColor(R.color.blueLight));
                Intent ins=new Intent(login.this, signUpBase.class);
                startActivity(ins);
                finish();
            }
        });


        lLogin.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

                String email = lEmail.getText().toString().trim();
                String password = lpassword.getText().toString().trim();

                if (TextUtils.isEmpty(email)){

                    lEmail.setError(" الإيميل مطلوب ");
                    Toast.makeText(login.this, " الإيميل مطلوب ", Toast.LENGTH_LONG).show();
                    return;
                }


                if (TextUtils.isEmpty(password)){
                    lpassword.setError(" كلمة المرور مطلوبة ");
                    Toast.makeText(login.this, " كلمة المرور مطلوبة ", Toast.LENGTH_LONG).show();

                    return;
                }

                if (password.length() < 8){
                    lpassword.setError(" الرقم السري يجب أن يحتوي على ٨ رموز أو أكثر ");
                    return;
                }

                fAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){

                            if(fAuth.getCurrentUser().isEmailVerified()){
                                //UID
                                String UID=FirebaseAuth.getInstance().getCurrentUser().getUid();
                                //First collection - - - - - Admin
                                FirebaseFirestore db=FirebaseFirestore.getInstance();
                                //1
                                CollectionReference Admins = db.collection("admins");
                                DocumentReference docRef = Admins.document(UID);
                                docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                        if (task.isSuccessful()) {
                                            DocumentSnapshot document = task.getResult();
                                            if (document.exists()) {
                                                Toast.makeText(login.this, " تم تسجيل دخولك بنجاح! ", Toast.LENGTH_LONG).show();
                                                Intent i = new Intent(login.this, homepageAdmin.class);
                                                startActivity(i);
                                                finish();
                                            }
                                        }
                                    }
                                });
                                //End Admin Checking - - - - - - - - - - -
                                //Second collection - - - - - donator
                                CollectionReference donators = db.collection("donators");
                                DocumentReference docRefD = donators.document(UID);
                                docRefD.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                        if (task.isSuccessful()) {
                                            DocumentSnapshot document = task.getResult();
                                            if (document.exists()) {
                                                Toast.makeText(login.this, " تم تسجيل دخولك بنجاح! ", Toast.LENGTH_LONG).show();
                                                Intent i = new Intent(login.this, homepageDonator.class);
                                                startActivity(i);
                                                finish();
                                            }
                                        }
                                    }
                                });
                                //End donator Checking - - - - - - - - - - -
                                //Third collection - - - - - beneficiaries
                                CollectionReference beneficiaries = db.collection("beneficiaries");
                                DocumentReference docRefB = beneficiaries.document(UID);
                                docRefB.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                        if (task.isSuccessful()) {
                                            DocumentSnapshot document = task.getResult();
                                            if (document.exists()) {
                                                Toast.makeText(login.this, " تم تسجيل دخولك بنجاح! ", Toast.LENGTH_LONG).show();
                                                Intent i = new Intent(login.this, homePage.class);
                                                startActivity(i);
                                                finish();
                                            }
                                        }
                                    }
                                });
                                //End beneficiaries Checking - - - - - - - - - - -

                            }
                            else {
                                Toast.makeText(login.this, " يرجى تأكيد الإيميل ", Toast.LENGTH_LONG).show();

                            }

                        } else {
                            if (task.getException().getMessage().equals("There is no user record corresponding to this identifier. The user may have been deleted."))
                                Toast.makeText(login.this, " الايميل غير موجود لدينا يرجى تسجيل حساب جديد " , Toast.LENGTH_LONG).show();
                            else if (task.getException().getMessage().equals("The password is invalid or the user does not have a password."))
                                Toast.makeText(login.this, " كلمة السر غير صحيحة " , Toast.LENGTH_LONG).show();
                            else if (task.getException().getMessage().equals("We have blocked all requests from this device due to unusual activity. Try again later. [ Too many unsuccessful login attempts. Please try again later. ]"))
                                Toast.makeText(login.this, " تم حجب تسجيل الدخول للمستخدم لتجاوز الحد المسموح من المحاولات عاود التسجيل بعد فترة  " , Toast.LENGTH_LONG).show();
                            else if (task.getException().getMessage().equals("The email address is badly formatted."))
                                Toast.makeText(login.this, " يرجى كتابة الايميل بشكل صحيح " , Toast.LENGTH_LONG).show();
                            else
                            Toast.makeText(login.this, " حصل خطأ ما! " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        }

                    }

                });




            }







            });
    }
    @SuppressLint("MissingSuperCall")
    @Override
    protected void onStart() {
        super.onStart();
        firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
        //Check if the user already logged in  --> login automatically - Saving current user-
        if (firebaseUser!=null) {
            if(fAuth.getCurrentUser().isEmailVerified()) {
                //UID
                String UID = FirebaseAuth.getInstance().getCurrentUser().getUid();
                //First collection - - - - - Admin
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                CollectionReference Admins = db.collection("admins");
                DocumentReference docRef = Admins.document(UID);
                docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                Intent i = new Intent(login.this, homepageAdmin.class);
                                startActivity(i);
                                finish();
                            }
                        }
                    }
                });
                //End Admin Checking - - - - - - - - - - -
                //Second collection - - - - - donator
                CollectionReference donators = db.collection("donators");
                DocumentReference docRefD = donators.document(UID);
                docRefD.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                Intent i = new Intent(login.this, homepageDonator.class);
                                startActivity(i);
                                finish();
                            }
                        }
                    }
                });
                //End donator Checking - - - - - - - - - - -
                //Third collection - - - - - beneficiaries
                CollectionReference beneficiaries = db.collection("beneficiaries");
                DocumentReference docRefB = beneficiaries.document(UID);
                docRefD.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                Intent i = new Intent(login.this, homePage.class);
                                startActivity(i);
                                finish();
                            }
                        }
                    }
                });
                //End beneficiaries Checking - - - - - - - - - - -
            }//end verfiying
        }//end if not null
        //finish checking
    }




}

