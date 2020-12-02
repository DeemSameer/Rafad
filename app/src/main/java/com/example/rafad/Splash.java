package com.example.rafad;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class Splash extends AppCompatActivity {
    ImageView x,y;
    Animation frombottom,fromtop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        x=(ImageView)findViewById(R.id.top);
        y=(ImageView)findViewById(R.id.bottom);
        frombottom= AnimationUtils.loadAnimation(this,R.anim.frombottom);
        y.setAnimation(frombottom);

        fromtop= AnimationUtils.loadAnimation(this,R.anim.fromtop);
        x.setAnimation(fromtop);


    }
}