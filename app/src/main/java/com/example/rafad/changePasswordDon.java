package com.example.rafad;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class changePasswordDon extends AppCompatActivity {

    EditText password, confirmpassword;
    Button Done, backToProfile;
    FirebaseAuth fAuth;
    FirebaseUser user;
    TextView n,e,ph,e2;
    FirebaseFirestore fStore;
    String userId ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password_don);

        password = findViewById(R.id.newPass);
        confirmpassword = findViewById(R.id.confirmNewPass);
        fAuth = FirebaseAuth.getInstance();
        Done = findViewById(R.id.saveNewPass);
        backToProfile = findViewById(R.id.backtoProfilDon);
        n = findViewById(R.id.name);
        e =findViewById(R.id.email22);
        e2 =findViewById(R.id.e2);
        ph = findViewById(R.id.phoneno);
        fStore= FirebaseFirestore.getInstance();
        userId = fAuth.getCurrentUser().getUid();

        backToProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(changePasswordDon.this,donProfile.class);
                //pass the data
                i.putExtra("fullName",n.getText().toString());
                i.putExtra("email",e2.getText().toString());
                i.putExtra("phone", ph.getText().toString());
                startActivity(i);
              //  startActivity(new Intent(changePasswordDon.this, donProfile.class));
                finish();
            }
        });


        Done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                change(view);

            }
        });

        DocumentReference documentReference = fStore.collection("donators").document(userId);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                ph.setText(value.getString("phoneNumber"));
                n.setText(value.getString("userName"));
                e.setText(value.getString("email"));
                e2.setText(value.getString("email"));
            }
        });



    }

    public void change (View v){
        String enteredPass = password.getText().toString();
        String enteredcon=confirmpassword.getText().toString();
        FirebaseUser user =fAuth.getCurrentUser();
        if (user!=null){
            if(!enteredPass.equals(enteredcon)){
                confirmpassword.setError(" كلمة المرور لاتتطابق مع تأكيد كلمة المرور ");
                return;
            }
            if(enteredPass.isEmpty()){
                password.setError(" لا يمكن ترك كلمة المرور فارغة ");
            }

            if(enteredcon.isEmpty()){
                confirmpassword.setError(" لا يمكن ترك تأكيد كلمة المرور فارغة ");
                return;
            }

            if(enteredPass.length()<8){
                password.setError(" يجب أن تحتوي كلمة المرور على 8 رموز أو أكثر  ");
                return;
            }


            user.updatePassword(password.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(getApplicationContext(), "تم تغيير كلمة المرور بنجاح ", Toast.LENGTH_LONG).show();
                        Intent i = new Intent(changePasswordDon.this,donProfile.class);
                        //pass the data
                        i.putExtra("fullName",n.getText().toString());
                        i.putExtra("email",e.getText().toString());
                        i.putExtra("phone", ph.getText().toString());
                        startActivity(i);
                        finish();


                    } else {
                        String e = task.getException().getMessage().toString();
                        if (e.equals("This operation is sensitive and requires recent authentication. Log in again before retrying this request.")){
                            Toast.makeText(getApplicationContext(),  "هذه العملية حساسة وتتطلب مصادقة حديثة. قم بتسجيل الدخول مرة أخرى قبل إعادة محاولة هذا الطلب.", Toast.LENGTH_LONG).show();
                        }
                        Toast.makeText(getApplicationContext(),  task.getException().getMessage() , Toast.LENGTH_LONG).show();
                        Intent i = new Intent(changePasswordDon.this,donProfile.class);
                        //pass the data
                        i.putExtra("fullName",n.getText().toString());
                        i.putExtra("email",e2.getText().toString());
                        i.putExtra("phone", ph.getText().toString());
                        startActivity(i);
                        finish();


                    }
                }

                });

            }
        }


}



