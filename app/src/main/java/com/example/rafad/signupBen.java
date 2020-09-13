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
import android.widget.RadioButton;
import android.widget.TextView;
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

    EditText signUpEmail,userName,SignUpPassword1,SignUpPassword2,signUpPhone,signUpssn,signUpTotalincome,number;
    Button signup_button;
    RadioButton radioButton0,radioButton1;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userID;
    TextView textView1;


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
        signUpssn= findViewById(R.id.signUpsn);
        signUpTotalincome= findViewById(R.id.signUpTotalincome);
        radioButton0=findViewById(R.id.radioButton3);
        radioButton1=findViewById(R.id.radioButton4);
        number=findViewById(R.id.signUpssn);

        fAuth = FirebaseAuth.getInstance();
        fStore=FirebaseFirestore.getInstance();

/*
        //check if it's current user
        if (fAuth.getCurrentUser()!=null){
            startActivity(new Intent(getApplicationContext(),login.class) );/////////////////////////////Change to the login
            finish();
        }
*/

        //have account?
        textView1=findViewById(R.id.textView);
        textView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(signupBen.this , login.class));
                finish();
            }
        });


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
                final String flag = "Admin";
                String rB0= radioButton0.getText().toString();
                String rB1= radioButton1.getText().toString();
                String TOR;
                final String Number=number.getText().toString();//الضمان الاجتماعي



                if (rB0.matches("")&&rB1.matches("")){
                    radioButton0.setError("نوع السكن مطلوب");
                }
                if (!rB0.matches(""))
                  TOR="ملك";
                else
                    TOR="اجار";

                final String typeOfResidence=TOR;





                if(TextUtils.isEmpty(email)){
                    signUpEmail.setError("الايميل مطلوب");
                    return;
                }
                if(TextUtils.isEmpty(Password)){
                    SignUpPassword1.setError("كلمة السر مطلوبة");
                    return;
                }
                if(TextUtils.isEmpty(userName2)){
                    signUpEmail.setError("اسم المستخدم مطلوب");
                    return;
                }
                if(TextUtils.isEmpty(Phone)){
                    SignUpPassword1.setError("رقم الجوال مطلوب");
                    return;
                }
                if(TextUtils.isEmpty(signUpTotalincome2)){
                    signUpEmail.setError("اجمالي الدخل مطلوب");
                    return;
                }
                if(TextUtils.isEmpty(signUpssn2)){
                    SignUpPassword1.setError("الهوية الوطنية مطلوبة");
                    return;
                }
                if (Password.length()<7){
                    SignUpPassword1.setError("طول كلمة السر يجب أن لا يقل عن 8 ارقام او حروف");
                    return;
                }
                if (!Password.equals(Password2)){
                    SignUpPassword2.setError("كلمات السر غير متطابقة");
                    return;
                }
                if (Phone.length()!=10){
                    signUpPhone.setError("رقم الجوال اقل من 10 ارقام");
                    return;
                }
                if (signUpssn2.length()!=10){
                    signUpssn.setError("رقم الهوية الوطنية غير صالح");
                    return;
                }
                fAuth.createUserWithEmailAndPassword(email,Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            fAuth.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(signupBen.this, "User created", Toast.LENGTH_SHORT).show();

                                        userID = fAuth.getCurrentUser().getUid();
                                        DocumentReference documentrefReference = fStore.collection("users").document(userID);
                                        //store data
                                        Map<String, Object> user = new HashMap<>();
                                        user.put("phoneNumber", Phone);
                                        user.put("userName", userName2);
                                        user.put("type", type);
                                        user.put("email", email);
                                        user.put("SSN", signUpssn2);
                                        user.put("TotalIncome", signUpTotalincome2);
                                        user.put("flag", flag);
                                        user.put("typeOfResidence", typeOfResidence);
                                        user.put("securityNumber", Number);///check ittt -----

                                        //check the add if it's success or not
                                        documentrefReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                Log.d(TAG, "تم انشاء الحساب" + userID);
                                            }
                                        });
                                        startActivity(new Intent(getApplicationContext(),login.class) );
                                        finish();

                                    }//end if
                                    else {
                                        Toast.makeText(signupBen.this, "غلط"+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                    }//end else
                                }//oncomplete
                            });

                        }//end if1
                        else{
                            Toast.makeText(signupBen.this, "غلط"+task.getException().getMessage(), Toast.LENGTH_SHORT).show();

                        }//end else
                    }//end on complete
                });
            }// end method on click

        });

    }
}