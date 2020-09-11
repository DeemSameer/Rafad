package com.example.rafad;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class signupBen extends AppCompatActivity {
    public static final String TAG = "TAG";

    EditText signUpEmail,userName,SignUpPassword1,SignUpPassword2,signUpPhone,signUpssn,signUpTotalincome;
    Button signup_button;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userID;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_ben);


        signUpEmail = findViewById(R.id.signUpEmail);
        SignUpPassword1 = findViewById(R.id.SignUpPassword1);
        SignUpPassword2 = findViewById(R.id.SignUpPassword2);
        signUpPhone = findViewById(R.id.signUpPhone);
        signup_button = findViewById(R.id.signup_button);
        userName= findViewById(R.id.userName);
        signUpssn= findViewById(R.id.signUpssn);
        signUpTotalincome= findViewById(R.id.signUpTotalincome);

        fAuth = FirebaseAuth.getInstance();
        fStore=FirebaseFirestore.getInstance();


        //check if it's current user
        if (fAuth.getCurrentUser()!=null){
            startActivity(new Intent(getApplicationContext(),login.class) );/////////////////////////////Change to the login
            finish();
        }

        signup_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                final String email = signUpEmail.getText().toString().trim();
                final String Password = SignUpPassword1.getText().toString();
                final String Password2 = SignUpPassword2.getText().toString();
                final String Phone = signUpPhone.getText().toString();
                final String userName2= userName.getText().toString();
                final String type= "beneficiary";
                final String signUpssn2= signUpssn.getText().toString();
                final String signUpTotalincome2=signUpTotalincome.getText().toString();


                if(TextUtils.isEmpty(email)){
                    signUpEmail.setError("email is required");
                    return;
                }
                if(TextUtils.isEmpty(Password)){
                    SignUpPassword1.setError("Password is required");
                    return;
                }
                if(TextUtils.isEmpty(userName2)){
                    signUpEmail.setError("email is required");
                    return;
                }
                if(TextUtils.isEmpty(Phone)){
                    SignUpPassword1.setError("Password is required");
                    return;
                }
                if(TextUtils.isEmpty(signUpTotalincome2)){
                    signUpEmail.setError("email is required");
                    return;
                }
                if(TextUtils.isEmpty(signUpssn2)){
                    SignUpPassword1.setError("Password is required");
                    return;
                }
                if (Password.length()<8){
                    SignUpPassword1.setError("Password most be equal or greater than 8");
                    return;
                }
                if (!Password.equals(Password2)){
                    SignUpPassword2.setError("Paswords are mismatch");
                    return;
                }
                fAuth.createUserWithEmailAndPassword(email,Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(signupBen.this, "User created", Toast.LENGTH_SHORT).show();

                            userID= fAuth.getCurrentUser().getUid();
                            DocumentReference documentrefReference = fStore.collection("users").document(userID);
                            //store data
                            Map<String,Object> user= new HashMap<>();
                            user.put("phoneNumber", Phone);
                            user.put("userName", userName2);
                            user.put("type",type);
                            user.put("email",email);
                            user.put("SSN",signUpssn2);
                            user.put("TotalIncome",signUpTotalincome2 );

                            //check the add if it's success or not
                            documentrefReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Log.d(TAG,"User id created for"+userID);
                                }
                            });


                            startActivity(new Intent(getApplicationContext(),login.class) );
                        }else{
                            Toast.makeText(signupBen.this, "Error"+task.getException().getMessage(), Toast.LENGTH_SHORT).show();

                        }//end else
                    }//end on complete
                });
            }// end method on click

        });

    }
}