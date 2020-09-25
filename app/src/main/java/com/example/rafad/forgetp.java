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
        Button button4 = (Button) findViewById(R.id.but);

        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(forgetp.this, login.class));
                finish();
            }
        });
        fo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userem=ed.getText().toString().trim();;
                if(TextUtils.isEmpty(userem)){
                    Toast.makeText(forgetp.this," الرجاء كتابة بريدك الإلكتروني ",Toast.LENGTH_LONG).show();
                }
                else{
                    m.sendPasswordResetEmail(userem).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(forgetp.this," تم ارسال رسالة إعادة تعيين كلمة المرور الى بريدك الإلكتروني ",Toast.LENGTH_LONG).show();
                                startActivity(new Intent(forgetp.this,login.class));
                                finish();

                            }
                            else{
                                if (task.getException().getMessage().equals("There is no user record corresponding to this identifier. The user may have been deleted."))
                                    Toast.makeText(forgetp.this, " الايميل غير موجود لدينا يرجى تسجيل حساب جديد " , Toast.LENGTH_LONG).show();
                                else if (task.getException().getMessage().equals("The email address is badly formatted."))
                                    Toast.makeText(forgetp.this, " يرجى كتابة الايميل بشكل صحيح " , Toast.LENGTH_LONG).show();
                                else{
                                String message=task.getException().getMessage();
                                Toast.makeText(forgetp.this," حدث خطأ ما ! "+message,Toast.LENGTH_LONG).show();}

                            }
                        }
                    });

                }
            }
        });
    }
}