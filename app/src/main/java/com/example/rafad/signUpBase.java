package com.example.rafad;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class signUpBase extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_base);

        Button button = (Button) findViewById(R.id.signupBen);//signupDon
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                open_SignUpBen();
            }
        });
        Button button2 = (Button) findViewById(R.id.signupDon);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                open_SignUpDon();
            }
        });
    }

    public void open_SignUpBen(){
        Intent intent = new Intent(this, signupBen.class);
        startActivity(intent);
        finish();

    }
    public void open_SignUpDon(){
        Intent intent = new Intent(this, signupDon.class);
        startActivity(intent);
        finish();

    }
}
