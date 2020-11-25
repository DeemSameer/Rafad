package com.example.rafad;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.rafad.Block.BlockBenList;
import com.example.rafad.Block.blockBen;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.onesignal.OneSignal;

public class homePageAdminBase extends AppCompatActivity {
ProgressBar b1,b2,d1;
TextView ta,ta1,ta2;
Button up1;
int Tsize,size;
Button backHomeIcon;
Button backHomArrow;
    Button logout,list;
    FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page_admin_base);
        fAuth = FirebaseAuth.getInstance();
        up1=findViewById(R.id.button6);
        backHomeIcon=findViewById(R.id.adminbHome);
        backHomArrow=findViewById(R.id.button);
        list= findViewById(R.id.button66);


        // Logging set to help debug issues, remove before releasing your app.
        OneSignal.setLogLevel(OneSignal.LOG_LEVEL.VERBOSE, OneSignal.LOG_LEVEL.NONE);

        // OneSignal Initialization
        OneSignal.startInit(this)
                .inFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification)
                .unsubscribeWhenNotificationsAreDisabled(true)
                .init();

        String LoggedIn_User_Email =FirebaseAuth.getInstance().getCurrentUser().getEmail();
        OneSignal.sendTag("User_ID",LoggedIn_User_Email);






      


        up1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(homePageAdminBase.this, homepageAdmin.class);
                startActivity(i);
                finish();
            }
        });
        backHomeIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(homePageAdminBase.this, homePageAdminBase.class));
                finish();

            }
        });
        list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(homePageAdminBase.this, blockBen.class));
                finish();
            }
        });

        backHomArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(homePageAdminBase.this, adminMainProfile.class));
                finish();

            }
        });
        logout = findViewById(R.id.logoutButton);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fAuth.signOut();
                startActivity(new Intent(homePageAdminBase.this, login.class));
                finish();
            }
        });





    }
}