package com.example.rafad;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = "TAG";
    Timer timer ;

    ImageView x,y;
    Animation frombottom,fromtop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        x=(ImageView)findViewById(R.id.top);
        y=(ImageView)findViewById(R.id.bottom);
        frombottom= AnimationUtils.loadAnimation(this,R.anim.frombottom);
        y.setAnimation(frombottom);

        fromtop= AnimationUtils.loadAnimation(this,R.anim.fromtop);
        x.setAnimation(fromtop);
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Intent intent = new Intent (MainActivity.this, login.class);
                startActivity(intent);
                finish();
            }
        },3000 );






    }
}