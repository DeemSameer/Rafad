package com.example.rafad;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class homepageDonator extends AppCompatActivity {
    Button logout ,profile1;
    FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage_donator);

        fAuth = FirebaseAuth.getInstance();


        logout = findViewById(R.id.logoutButton);
        profile1= findViewById(R.id.profileb);

        profile1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(homepageDonator.this, mainProfile.class));
                Intent i = new Intent(homepageDonator.this, postitem.class);
                startActivity(i);
                finish();

            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fAuth.signOut();
                startActivity(new Intent(homepageDonator.this, login.class));
                finish();
            }
        });


    }


}