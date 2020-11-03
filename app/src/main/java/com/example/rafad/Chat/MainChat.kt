package com.example.rafad.Chat

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.rafad.R
import com.google.firebase.firestore.ListenerRegistration

class MainChat : AppCompatActivity() {
    private lateinit var userListenerRegistration: ListenerRegistration//Change the list of people if someone sign up !
    private var shouldInitRecyclerListener = true
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_chat)
    }
}