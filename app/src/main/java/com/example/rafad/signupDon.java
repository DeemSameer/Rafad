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

public class signupDon extends AppCompatActivity {
    EditText signUpEmail, SignUpPassword1, SignUpPassword2, signUpPhone;
    Button signupDonButton;
    FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_don);

        signUpEmail = findViewById(R.id.signUpEmail);
        SignUpPassword1 = findViewById(R.id.SignUpPassword1);
        SignUpPassword2 = findViewById(R.id.SignUpPassword2);
        signUpPhone = findViewById(R.id.signUpPhone);
        signupDonButton = findViewById(R.id.signupDonButton);

        fAuth = FirebaseAuth.getInstance();


        //check if it's current user
         if (fAuth.getCurrentUser()!=null){
             startActivity(new Intent(getApplicationContext(),login.class) );/////////////////////////////Change to the login
             finish();
         }

        signupDonButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String email = signUpEmail.getText().toString().trim();
                String Pasword = SignUpPassword1.getText().toString();
                String Pasword2 = SignUpPassword2.getText().toString();

                if(TextUtils.isEmpty(email)){
                    signUpEmail.setError("email is required");
                    return;
                }
                if(TextUtils.isEmpty(Pasword)){
                    SignUpPassword1.setError("Pasword is required");
                    return;
                }
                if (Pasword.length()<8){
                    SignUpPassword1.setError("Pasword most be equal or greater than 8");
                    return;
                }
                if (!Pasword.equals(Pasword2)){
                    SignUpPassword2.setError("Paswords are mismatch");
                    return;
                }
                fAuth.createUserWithEmailAndPassword(email,Pasword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                     if (task.isSuccessful()){
                         Toast.makeText(signupDon.this, "User created", Toast.LENGTH_SHORT).show();
                         startActivity(new Intent(getApplicationContext(),login.class) );/////////////////////////////Change to the login
                     }else{
                         Toast.makeText(signupDon.this, "Error"+task.getException().getMessage(), Toast.LENGTH_SHORT).show();

                     }//end else
                    }//end on complete
                });
            }// end method on click

        });
    }
}