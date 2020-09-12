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
import com.google.firebase.auth.FirebaseAuth;

public class forgetp extends AppCompatActivity {
    private Button fo;
    private EditText ed;
    private FirebaseAuth m;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgetp);

        m=FirebaseAuth.getInstance();
        fo=findViewById(R.id.button2);
        ed=findViewById(R.id.editTextTextPersonName2);

        fo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userem=ed.getText().toString();
                if(TextUtils.isEmpty(userem)){
                    Toast.makeText(forgetp.this,"please write your email address",Toast.LENGTH_SHORT).show();
                }
                else{
                    m.sendPasswordResetEmail(userem).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(forgetp.this,"please check your email account if you want to reset yout password",Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(forgetp.this,login.class));

                            }
                            else{
                                String message=task.getException().getMessage();
                                Toast.makeText(forgetp.this,"error occurred"+message,Toast.LENGTH_SHORT).show();

                            }
                        }
                    });

                }
            }
        });
    }
}