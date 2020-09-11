package com.example.rafad;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class login extends AppCompatActivity {

    EditText lEmail, lpassword;
    Button lLogin,lSignup;
    FirebaseAuth fAuth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login2);

        lEmail = findViewById(R.id.email);
        lpassword = findViewById(R.id.editTextTextPassword2);
        lLogin = findViewById(R.id.login);
        lSignup = findViewById(R.id.buttonsign);
        fAuth = FirebaseAuth.getInstance();


        lLogin.setOnClickListener(new View.OnClickListener(){
        @Override
        public void onClick(View v){

            String email = lEmail.getText().toString().trim();
            String password = lpassword.getText().toString().trim();

            if (TextUtils.isEmpty(email)){

                lEmail.setError("الإيميل مطلوب");
                return;
            }


            if (TextUtils.isEmpty(password)){
                lpassword.setError("كلمة المرور مطلوبة");
                return;
            }

            if (password.length() < 6){
                lpassword.setError("الرقم السري يجب أن يكون أكثر من ٦ رموز");
                return;
            }

        fAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                Toast.makeText(login.this, "تم تسجيل تخولك بنجاح!", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(),homePage.class));
                } else {
                    Toast.makeText(login.this, "حصل خطأ ما!" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }

            }
        });



        }


    });




}}