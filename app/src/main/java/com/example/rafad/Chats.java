package com.example.rafad;

import android.app.Application;

import sdk.chat.core.session.ChatSDK;

public class Chats extends Application {

    public void onCreate() {
        super.onCreate();
        try {
            ChatSDK.ui().startSplashScreenActivity(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    }
