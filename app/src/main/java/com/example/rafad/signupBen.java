package com.example.rafad;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import java.util.HashMap;
import java.util.Map;

public class signupBen extends AppCompatActivity {
    public static final String TAG = "TAG";

    EditText signUpEmail,userName,SignUpPassword1,SignUpPassword2,signUpPhone,signUpssn,signUpTotalincome,number,location;
    Button signup_button;
    RadioButton radioButton0,radioButton1;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userID;
    TextView textView1, type1;
    String[]CountryList={"المدينة المنورة","مكة المكرمة","الرياض","القصيم","الشرقية","عسير","تبوك","الجوف","الباحة","نجران","جازان","الحدود الشمالية","حائل"};

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
        type1 = findViewById(R.id.type);
        fAuth = FirebaseAuth.getInstance();
        fStore=FirebaseFirestore.getInstance();
        ArrayAdapter<String> arrayAdapter=new ArrayAdapter<String>(this,android.R.layout.simple_dropdown_item_1line, CountryList);
        final MaterialBetterSpinner betterSpinner=(MaterialBetterSpinner) findViewById(R.id.loc);
        betterSpinner.setAdapter(arrayAdapter);


        Button button4 = (Button) findViewById(R.id.but2);

        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(signupBen.this, signUpBase.class));
                finish();
            }
        });



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
                final String location=betterSpinner.getText().toString();
                final String type= "beneficiary";
                final String signUpssn2= signUpssn.getText().toString();
                final String signUpTotalincome2=signUpTotalincome.getText().toString();
                final String flag = "Admin";
                String rB0= radioButton0.getText().toString();
                String rB1= radioButton1.getText().toString();
                String TOR;
                final String Number=number.getText().toString();//الضمان الاجتماعي



                if (!(radioButton0.isChecked()|| radioButton1.isChecked())){
                    type1.setError(" نوع السكن مطلوب ");
                    return;
                }

                if (radioButton0.isChecked())
                  TOR="ملك";
                else
                    TOR="ايجار";

                final String typeOfResidence=TOR;





                if(TextUtils.isEmpty(email)){
                    signUpEmail.setError(" البريد الإلكتروني مطلوب ");
                    return;
                }
                if(TextUtils.isEmpty(Password)){
                    SignUpPassword1.setError(" كلمة المرور مطلوبة ");
                    return;
                }
                if(TextUtils.isEmpty(userName2)){
                    userName.setError("اسم المستخدم مطلوب");
                    return;
                }
                if(TextUtils.isEmpty(userName2)){
                    userName.setError("اسم المستخدم مطلوب");
                    return;
                }

                if(TextUtils.isEmpty(Phone)){
                    signUpPhone.setError(" رقم الجوال مطلوب ");
                    return;
                }
                if(TextUtils.isEmpty(signUpTotalincome2)){
                    signUpTotalincome.setError(" اجمالي الدخل مطلوب ");
                    return;
                }
                if(TextUtils.isEmpty(signUpssn2)){
                    signUpssn.setError("الهوية الوطنية مطلوبة");
                    return;
                }
                if (Password.length()<=7){
                    SignUpPassword1.setError(" كلمة المرور يجب أن تحتوي على 8 رموز ");
                    return;
                }
                if (!Password.equals(Password2)){
                    SignUpPassword2.setError(" كلمة المرور غير متطابقة مع تأكيد كلمة المرور ");
                    return;
                }
                if (Phone.length()!=10){
                    signUpPhone.setError("يجب أن يتكون رقم الجوال من 10 أرقام");
                    return;
                }
                if (!Phone.substring(0,2).equals("05")){
                    signUpPhone.setError(" يجب أن يبدأ رقم الجوال بـ 05 ");
                    return;
                }
                if (signUpssn2.length()!=10){
                    signUpssn.setError(" رقم الهوية الوطنية غير صالح ");
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
                                        Toast.makeText(signupBen.this, " تم إنشاء الحساب ", Toast.LENGTH_LONG).show();

                                        userID = fAuth.getCurrentUser().getUid();
                                        DocumentReference documentrefReference = fStore.collection("beneficiaries").document(userID);
                                        //store data
                                        Map<String, Object> user = new HashMap<>();
                                        user.put("phoneNumber", Phone);
                                        user.put("userName", userName2);
                                        //user.put("type", type);
                                        user.put("email", email);
                                        user.put("SSN", signUpssn2);
                                        user.put("TotalIncome", signUpTotalincome2);
                                        user.put("flag", flag);
                                        user.put("typeOfResidence", typeOfResidence);
                                        user.put("securityNumber", Number);///check ittt -----
                                        user.put("location",location);


                                        //check the add if it's success or not
                                        documentrefReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                Log.d(TAG, " تم انشاء الحساب " + userID);
                                            }
                                        });
                                        startActivity(new Intent(getApplicationContext(),login.class) );
                                        finish();

                                    }//end if
                                    else {
                                        Toast.makeText(signupBen.this, " حصل خطأ ما ! "+task.getException().getMessage(), Toast.LENGTH_LONG).show();
                                    }//end else
                                }//oncomplete
                            });

                        }//end if1
                        else{
                            if (task.getException().getMessage().equals("The email address is already in use by another account."))
                                Toast.makeText(signupBen.this, " الايميل موجود لدينا يرجى تسجيل دخول " , Toast.LENGTH_LONG).show();
                            else if (task.getException().getMessage().equals("The email address is badly formatted."))
                                Toast.makeText(signupBen.this, " يرجى كتابة الايميل بشكل صحيح " , Toast.LENGTH_LONG).show();
                            else if (task.getException().getMessage().equals("We have blocked all requests from this device due to unusual activity. Try again later. [ Too many unsuccessful login attempts. Please try again later. ]"))
                                Toast.makeText(signupBen.this, " تم حجب تسجيل جديد للمستخدم لتجاوز الحد المسموح من المحاولات عاود التسجيل بعد فترة  " , Toast.LENGTH_LONG).show();
                            else
                            Toast.makeText(signupBen.this, " حصل خطأ ما ! "+task.getException().getMessage(), Toast.LENGTH_LONG).show();

                        }//end else
                    }//end on complete
                });
            }// end method on click

        });

    }

}
