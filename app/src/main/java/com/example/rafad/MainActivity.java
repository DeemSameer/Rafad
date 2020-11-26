package com.example.rafad;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.rafad.ChatJava.MainChatAllPeople;
import com.example.rafad.ChatJava.MessageActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

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