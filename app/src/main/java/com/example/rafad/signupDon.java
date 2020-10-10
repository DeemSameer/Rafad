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
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ArrayAdapter;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class signupDon extends AppCompatActivity {
    public static final String TAG = "TAG";
    EditText signUpEmail, SignUpPassword1, SignUpPassword2, signUpPhone,UserName;
    Button signupDonButton;
    TextView textView1;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userID;
    String[]CountryList={"المدينة المنورة","مكة المكرمة","الرياض","القصيم","الشرقية","عسير","تبوك","الجوف","الباحة","نجران","جازان","الحدود الشمالية","حائل"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_don);

        signUpEmail = findViewById(R.id.signUpEmail);
        SignUpPassword1 = findViewById(R.id.SignUpPassword1);
        SignUpPassword2 = findViewById(R.id.SignUpPassword2);
        signUpPhone = findViewById(R.id.signUpPhone);
        signupDonButton = findViewById(R.id.button2);
        UserName= findViewById(R.id.UserName);

        fAuth = FirebaseAuth.getInstance();
        fStore=FirebaseFirestore.getInstance();
        ArrayAdapter<String> arrayAdapter=new ArrayAdapter<String>(this,android.R.layout.simple_dropdown_item_1line, CountryList);
        final MaterialBetterSpinner betterSpinner=(MaterialBetterSpinner) findViewById(R.id.loca);
        betterSpinner.setAdapter(arrayAdapter);

        Button button4 = (Button) findViewById(R.id.but3);

        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(signupDon.this, signUpBase.class));
                finish();
            }
        });




        //have account?
        textView1=findViewById(R.id.textView);
        textView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(signupDon.this , login.class));
                finish();
            }
        });

        signupDonButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                final String email = signUpEmail.getText().toString().trim();
                final String Password = SignUpPassword1.getText().toString();
                final String Password2 = SignUpPassword2.getText().toString();
                final String Phone = signUpPhone.getText().toString();
                final String location=betterSpinner.getText().toString();
                final String userName= UserName.getText().toString();
                final String type= "Donator";


                if(TextUtils.isEmpty(email)){
                    signUpEmail.setError(" البريد الإلكتروني مطلوب");
                    return;
                }
                if(TextUtils.isEmpty(userName)){
                    UserName.setError("اسم المستخدم مطلوب");
                    return;
                }
                if(TextUtils.isEmpty(Password)){
                    SignUpPassword1.setError(" كلمة المرور مطلوبة ");
                    return;
                }
                if (Password.length()<=7){
                    SignUpPassword1.setError("كلمة المرور يجب أن تحتوي على 8 رموز أو أكثر ");
                    return;
                }
                if (!Password.equals(Password2)){
                    SignUpPassword2.setError("  كلمة المرور غير متطابقة مع تأكيد كلمة المرور ");
                    return;
                }
                if (Phone.length()!=10){
                    signUpPhone.setError(" يجب أن يتكون رقم الجوال من 10 أرقام ");
                    return;
                }
                if (!Phone.substring(0,2).equals("05")){
                    signUpPhone.setError(" يجب أن يبدأ رقم الجوال بـ 05 ");
                    return;
                }

                fAuth.createUserWithEmailAndPassword(email,Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                     if (task.isSuccessful()){
                         fAuth.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                             @Override
                             public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()){
                                    Toast.makeText(signupDon.this, "تم انشاء الحساب" , Toast.LENGTH_LONG).show();

                                    userID= fAuth.getCurrentUser().getUid();
                                    DocumentReference documentrefReference = fStore.collection("donators").document(userID);
                                    //store data
                                    Map<String,Object> user= new HashMap<>();
                                    user.put("phoneNumber", Phone);
                                    user.put("userName", userName);
                                    //user.put("type",type);
                                    user.put("email",email);

                                    user.put("items",Arrays.asList("array for items"));
                                    //check the add if it's success or not
                                    documentrefReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            Log.d(TAG,"User id created for"+userID);
                                        }
                                    });


                                    startActivity(new Intent(getApplicationContext(),login.class) );
                                    finish();
                                }
                                else{
                                    Toast.makeText(signupDon.this, " حصل خطأ ما ! ", Toast.LENGTH_LONG).show();

                                }
                             }
                         });

                     }else{
                         if (task.getException().getMessage().equals("The email address is already in use by another account."))
                             Toast.makeText(signupDon.this, " الايميل موجود لدينا يرجى تسجيل دخول " , Toast.LENGTH_LONG).show();
                         else if (task.getException().getMessage().equals("We have blocked all requests from this device due to unusual activity. Try again later. [ Too many unsuccessful login attempts. Please try again later. ]"))
                             Toast.makeText(signupDon.this, " تم حجب تسجيل جديد للمستخدم لتجاوز الحد المسموح من المحاولات عاود التسجيل بعد فترة  " , Toast.LENGTH_LONG).show();
                         else if (task.getException().getMessage().equals("The email address is badly formatted."))
                             Toast.makeText(signupDon.this, " يرجى كتابة الايميل بشكل صحيح " , Toast.LENGTH_LONG).show();
                         else
                         Toast.makeText(signupDon.this, " حصل خطأ ما ! "+task.getException().getMessage(), Toast.LENGTH_LONG).show();

                     }//end else
                    }//end on complete
                });
            }// end method on click

        });
    }
}