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

                    lEmail.setError(" ط§ظ„ط¥ظٹظ…ظٹظ„ ظ…ط·ظ„ظˆط¨ ");
                    Toast.makeText(login.this, " ط§ظ„ط¥ظٹظ…ظٹظ„ ظ…ط·ظ„ظˆط¨ ", Toast.LENGTH_LONG).show();
                    return;
                }


                if (TextUtils.isEmpty(password)){
                    lpassword.setError(" ظƒظ„ظ…ط© ط§ظ„ظ…ط±ظˆط± ظ…ط·ظ„ظˆط¨ط© ");
                    Toast.makeText(login.this, " ظƒظ„ظ…ط© ط§ظ„ظ…ط±ظˆط± ظ…ط·ظ„ظˆط¨ط© ", Toast.LENGTH_LONG).show();

                    return;
                }

                if (password.length() < 8){
                    lpassword.setError(" ط§ظ„ط±ظ‚ظ… ط§ظ„ط³ط±ظٹ ظٹط¬ط¨ ط£ظ† ظٹط­طھظˆظٹ ط¹ظ„ظ‰ ظ¨ ط±ظ…ظˆط² ط£ظˆ ط£ظƒط«ط± ");
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
                                                Toast.makeText(login.this, " طھظ… طھط³ط¬ظٹظ„ ط¯ط®ظˆظ„ظƒ ط¨ظ†ط¬ط§ط­! ", Toast.LENGTH_LONG).show();
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
                                                Toast.makeText(login.this, " طھظ… طھط³ط¬ظٹظ„ ط¯ط®ظˆظ„ظƒ ط¨ظ†ط¬ط§ط­! ", Toast.LENGTH_LONG).show();
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
                                                Toast.makeText(login.this, " طھظ… طھط³ط¬ظٹظ„ ط¯ط®ظˆظ„ظƒ ط¨ظ†ط¬ط§ط­! ", Toast.LENGTH_LONG).show();
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
                                Toast.makeText(login.this, " ظٹط±ط¬ظ‰ طھط£ظƒظٹط¯ ط§ظ„ط¥ظٹظ…ظٹظ„ ", Toast.LENGTH_LONG).show();

                            }

                        } else {
                            if (task.getException().getMessage().equals("There is no user record corresponding to this identifier. The user may have been deleted."))
                                Toast.makeText(login.this, " ط§ظ„ط§ظٹظ…ظٹظ„ ط؛ظٹط± ظ…ظˆط¬ظˆط¯ ظ„ط¯ظٹظ†ط§ ظٹط±ط¬ظ‰ طھط³ط¬ظٹظ„ ط­ط³ط§ط¨ ط¬ط¯ظٹط¯ " , Toast.LENGTH_LONG).show();
                            else if (task.getException().getMessage().equals("The password is invalid or the user does not have a password."))
                                Toast.makeText(login.this, " ظƒظ„ظ…ط© ط§ظ„ط³ط± ط؛ظٹط± طµط­ظٹط­ط© " , Toast.LENGTH_LONG).show();
                            else if (task.getException().getMessage().equals("We have blocked all requests from this device due to unusual activity. Try again later. [ Too many unsuccessful login attempts. Please try again later. ]"))
                                Toast.makeText(login.this, " طھظ… ط­ط¬ط¨ طھط³ط¬ظٹظ„ ط§ظ„ط¯ط®ظˆظ„ ظ„ظ„ظ…ط³طھط®ط¯ظ… ظ„طھط¬ط§ظˆط² ط§ظ„ط­ط¯ ط§ظ„ظ…ط³ظ…ظˆط­ ظ…ظ† ط§ظ„ظ…ط­ط§ظˆظ„ط§طھ ط¹ط§ظˆط¯ ط§ظ„طھط³ط¬ظٹظ„ ط¨ط¹ط¯ ظپطھط±ط©  " , Toast.LENGTH_LONG).show();
                            else if (task.getException().getMessage().equals("The email address is badly formatted."))
                                Toast.makeText(login.this, " ظٹط±ط¬ظ‰ ظƒطھط§ط¨ط© ط§ظ„ط§ظٹظ…ظٹظ„ ط¨ط´ظƒظ„ طµط­ظٹط­ " , Toast.LENGTH_LONG).show();
                            else
                                Toast.makeText(login.this, " ط­طµظ„ ط®ط·ط£ ظ…ط§! " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
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
            //UID
            String UID=FirebaseAuth.getInstance().getCurrentUser().getUid();
            //First collection - - - - - Admin
            FirebaseFirestore db=FirebaseFirestore.getInstance();
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
        }//end if not null
        //finish checking
    }




}

