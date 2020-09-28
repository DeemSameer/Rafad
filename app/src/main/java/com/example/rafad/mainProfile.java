package com.example.rafad;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class mainProfile extends AppCompatActivity {
    Button back , editProfile, homebutton, editbutton2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_profile);
        back=findViewById(R.id.backHome);
        editProfile= findViewById(R.id.edit);
        homebutton = findViewById(R.id.bHome);
        editbutton2 = findViewById(R.id.edit2);


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(mainProfile.this, homepageDonator.class));
                finish();

            }
        });


        homebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(mainProfile.this, homepageDonator.class));
                finish();

            }
        });


               editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(mainProfile.this, donProfile.class));
                finish();

            }
        });


        editbutton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               Intent i = new Intent(view.getContext(),donProfile.class);
               //pass the data
                i.putExtra("fullname","Nada Alfarraj");
                i.putExtra("email","nadafjj@gmail.com");
                i.putExtra("phone","0545947952");
               startActivity(i);

            }
        });



    }
}