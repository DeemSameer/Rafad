package com.example.rafad;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
Timer timer ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Intent intent = new Intent (MainActivity.this, login.class);
                startActivity(intent);
                finish();
            }
        },4000 );


/*
        Button button = (Button) findViewById(R.id.mainButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                open_SignUp();
            }
        });

    }

    public void open_SignUp(){
        Intent intent = new Intent(this, signUpBase.class);
        startActivity(intent);
    }

 */

    }
}