package com.example.rafad;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Application;
import android.os.Bundle;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import sdk.chat.app.firebase.ChatSDKFirebase;
import sdk.chat.core.session.ChatSDK;

public class MainChat extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        try {
            ChatSDKFirebase.quickStart(getApplicationContext(), "pre_1", "DummyKeyChangeItAfterWard", true);
            //It's recomended to put beak point here bec it mostly crash :(
            //Option2 after 17m in https://www.youtube.com/watch?v=ZzfSd3hc3xw Ends in minute 22
        }catch(Exception e){

        }



    }
}