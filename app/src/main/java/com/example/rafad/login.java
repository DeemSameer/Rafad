package com.example.rafad;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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


public class login extends AppCompatActivity {

    EditText lEmail, lpassword;
    Button lLogin,lSignup;
    FirebaseAuth fAuth;
    TextView textView1;



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
                    return;
                }


                if (TextUtils.isEmpty(password)){
                    lpassword.setError(" كلمة المرور مطلوبة ");
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
                                Toast.makeText(login.this, " تم تسجيل تخولك بنجاح! ", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getApplicationContext(),homePage.class));
                                finish();

                            }
                            else {
                                Toast.makeText(login.this, " يرجى تأكيد الإيميل ", Toast.LENGTH_SHORT).show();

                            }

                        } else {
                            Toast.makeText(login.this, " حصل خطأ ما! " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }

                    }

                });




            }







            });




    }}