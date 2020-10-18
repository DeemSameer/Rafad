package com.example.rafad;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class homePageAdminBase extends AppCompatActivity {
ProgressBar b1,b2,d1;
TextView ta,ta1,ta2;
Button up1;
int Tsize,size;
Button backHomeIcon;
Button backHomArrow;
    Button logout;
    FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page_admin_base);
        fAuth = FirebaseAuth.getInstance();
        up1=findViewById(R.id.button6);
        backHomeIcon=findViewById(R.id.adminbHome);
        backHomArrow=findViewById(R.id.button);




        b1=findViewById(R.id.progressBarBenAccounts);
        b2=findViewById(R.id.progressBarBenAccounts2);
        d1=findViewById(R.id.progressBarBenAccounts3);
        //0
        b2.setProgress(0);
        d1.setProgress(0);
        //0
        ta=findViewById(R.id.textProgres);
        ta1=findViewById(R.id.textProgres2);
        ta2=findViewById(R.id.TextProgres2);
        //0
        ta1.setText("%0");
        ta2.setText("%0");
        //0

        Tsize=0;
        size=0;

        FirebaseFirestore db=FirebaseFirestore.getInstance();
        db.collection("beneficiaries")
                .whereEqualTo("flag", "Admin")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                size=size+1;
                            }
                        }
                        else {
                            Tsize=Tsize+1;
                        }
                    }
                });

        db.collection("beneficiaries")
                .whereEqualTo("flag", "Accpeted")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Tsize=Tsize+1;

                            }

                        }
                        else {
                        }
                    }
                });


        if (Tsize!=0){
        b1.setProgress((size/(Tsize+size)));
        ta.setText("%"+(size/(Tsize+size)));}
        else{
            b1.setProgress(100);
            ta.setText("%"+100);
        }



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