package com.example.rafad;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.onesignal.OneSignal;

import java.util.ArrayList;
import java.util.List;

public class homepageAdmin extends AppCompatActivity {
    Button logout;
    FirebaseAuth fAuth;
    List<benDataModel> arrayList=new ArrayList<>();
    static int size;
    public static final String TAG = "TAG";
    ListView list;
    Button adminbHome2,adminpro2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage_admin);

        fAuth = FirebaseAuth.getInstance();
        // Logging set to help debug issues, remove before releasing your app.
        OneSignal.setLogLevel(OneSignal.LOG_LEVEL.VERBOSE, OneSignal.LOG_LEVEL.NONE);

        // OneSignal Initialization
        OneSignal.startInit(this)
                .inFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification)
                .unsubscribeWhenNotificationsAreDisabled(true)
                .init();

        String LoggedIn_User_Email =FirebaseAuth.getInstance().getCurrentUser().getEmail();
        OneSignal.sendTag("User_ID","deemsameer.ds@gmail.com");


        adminbHome2=findViewById(R.id.adminbHome2);
        adminpro2=findViewById(R.id.adminpro2);

        adminbHome2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(homepageAdmin.this, homePageAdminBase.class));
                finish();

            }
        });
        adminpro2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(homepageAdmin.this, adminMainProfile.class));
                finish();

            }
        });

/////////////////////////////////////////////////DB////////////////////////////////////
        FirebaseFirestore db=FirebaseFirestore.getInstance();
        db.collection("beneficiaries")
                .whereEqualTo("flag", "Admin")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData()+"//"+(String)document.get("typeOfResidence"));
                                arrayList.add(new benDataModel((String)document.getId(), (String)document.get("phoneNumber"), (String)document.get("typeOfResidence"),(String)document.get("TotalIncome"), (String)document.get("userName"),(String)document.get("email"), (String)document.get("SSN"), (String)document.get("securityNumber")));
                                Log.d(TAG,  "SIZE ADMIN => " +arrayList.size());

                            }


                            MyListAdapter adapter=new MyListAdapter(homepageAdmin.this, arrayList);
                            list=(ListView)findViewById(R.id.list);
                            list.setAdapter(adapter);
                        }
                         else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
        Button button4 = (Button) findViewById(R.id.but);

        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(homepageAdmin.this, homePageAdminBase.class));
                finish();
            }
        });


        ////////////////////////////////////////////////////////////////

    }

}

