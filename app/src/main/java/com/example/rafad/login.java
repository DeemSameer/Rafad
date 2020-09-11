package com.example.rafad;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class login extends AppCompatActivity {
     EditText email , pass;
     Button login , signup;
    FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login2);
        mFirebaseAuth = FirebaseAuth.getInstance();
        email = findViewById(R.id.editTextTextPersonName);
        pass = findViewById(R.id.editTextTextPassword2);
        login = findViewById(R.id.button);
        signup = findViewById(R.id.button2);
        mAuthStateListener =new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser mFirebaseUser =mFirebaseAuth.getCurrentUser();
                if(mFirebaseUser!=null ){
                    Toast.makeText(login.this ,"you are logged in",Toast.LENGTH_SHORT).show();
                    Intent i =new Intent (login.this , MainActivity.class);
                    startActivity(i);
                }
                else {
                    Toast.makeText(login.this ,"Please log in",Toast.LENGTH_SHORT).show();

                }


            }
        };
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email2= email.getText().toString();
                String pass2= pass.getText().toString();
                if(email2.isEmpty()){
                    email.setError("please Enter Email Id");
                    email.requestFocus();
                }
                else if (pass2.isEmpty()){
                    pass.setError("please Enter password");
                    pass.requestFocus();
                }
                else if(email2.isEmpty()&&pass2.isEmpty()){
                    Toast.makeText(login.this ,"Fileds are empty!", Toast.LENGTH_SHORT).show();

                }
                else if(!(email2.isEmpty()&&pass2.isEmpty())){
                    mFirebaseAuth.signInWithEmailAndPassword(email2,pass2).addOnCompleteListener(login.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(!task.isSuccessful()){
                                Toast.makeText(login.this ,"Signin unsuccessfull,please try again",Toast.LENGTH_SHORT).show();

                            }
                            else {
                                startActivity(new Intent(login.this,MainActivity.class));
                            }
                        }
                    });
                }
                else{
                    Toast.makeText(login.this ,"error Occurred!",Toast.LENGTH_SHORT).show();

                }


            }
        });
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent ins=new Intent(login.this, MainActivity.class);
                startActivity(ins);
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        mFirebaseAuth.addAuthStateListener(mAuthStateListener);
    }
}
