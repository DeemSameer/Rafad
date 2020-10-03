package com.example.rafad;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class changePasswordDon extends AppCompatActivity {

    EditText password, confirmpassword;
    Button Done, backToProfile;
    FirebaseAuth fAuth;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password_don);

        password = findViewById(R.id.newPass);
        confirmpassword = findViewById(R.id.confirmNewPass);
        fAuth = FirebaseAuth.getInstance();
        Done = findViewById(R.id.saveNewPass);
        backToProfile = findViewById(R.id.backtoProfilDon);

        backToProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(changePasswordDon.this, donProfile.class));
                finish();
            }
        });


        Done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                change(view);

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
                        startActivity(new Intent(changePasswordDon.this, donProfile.class));
                        finish();


                    } else {
                        String e = task.getException().getMessage().toString();
                        Toast.makeText(getApplicationContext(),  task.getException().getMessage() , Toast.LENGTH_LONG).show();
                        startActivity(new Intent(changePasswordDon.this, donProfile.class));
                        finish();


                    }
                }

                });

            }
        }


}



