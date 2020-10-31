package com.example.rafad.notification;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class Token extends AppCompatActivity {
    private String token;

    public Token(String token) {
        this.token = token;
    }

    public Token() {
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}