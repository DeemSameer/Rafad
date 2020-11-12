package com.example.rafad;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class homePage extends AppCompatActivity {
    Button logout, profile, clothes , furniture,device,all,toReq;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    TextView empty;
    //////// for view list of items
    List<postinfo> arrayItem=new ArrayList<>();
    List<postinfo> arrayItemC=new ArrayList<>();
    List<postinfo> arrayItemD=new ArrayList<>();
    List<postinfo> arrayItemF=new ArrayList<>();
    List<postinfo> arrayItemA=new ArrayList<>();

    public static final String TAG = "TAG";
    ListView listView;
    //////// above is for view list of items

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        clothes = findViewById(R.id.clothes1);
        furniture = findViewById(R.id.furniture);
        device = findViewById(R.id.device1);
        all = findViewById(R.id.all1);
        fAuth = FirebaseAuth.getInstance();
        fStore= FirebaseFirestore.getInstance();
        empty = findViewById(R.id.homepagetext2);
        logout=findViewById(R.id.logoutButton);
        profile= findViewById(R.id.profileb);
        toReq = findViewById(R.id.benHomeToReq);
        //////// for view list of items
        listView=(ListView)findViewById(R.id.postedlist);
        //////// above is for view list of items

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(homePage.this, BenMainProfile.class));
                finish();

            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fAuth.signOut();
                startActivity(new Intent(homePage.this, login.class));
                finish();
            }
        });

        toReq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(homePage.this, benReqView.class));
                finish();
            }
        });


        clothes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clothes.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
                furniture.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
                device.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
                all.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
                Query p =  fStore.collection("item").whereEqualTo("Catogery", "Ù…Ù„Ø§Ø¨Ø³")
                        .whereEqualTo("isRequested", "no" );

                p.get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        Log.d(TAG, document.getId() + " => " + document.getData());
                                        arrayItemC.add(new postinfo((String) document.getId(), (String) document.get("User id"), (String) document.get("Image"), (String) document.get("Description"), (String) document.get("Catogery"), (String) document.get("Title"),(String) document.get("isRequested"),(String) document.get("Date"),"" ,(String) document.get("donE")));
                                        Log.d(TAG, "SIZE item list => " + arrayItemC.size());
                                    }
                                    if (arrayItemC.size()==0)
                                    {
                                        empty.setText("Ù„Ø§ ÙŠÙˆØ¬Ø¯ Ø¨ÙŠØ§Ù†Ø§Øª Ù„Ù„Ø¹Ø±Ø¶");
                                    }
                                    else{
                                        empty.setText("");
                                    }
                                    ListViewAdaptorBen adapter = new ListViewAdaptorBen(homePage.this, arrayItemC);
                                    listView = (ListView) findViewById(R.id.postedlistHomePage);
                                    listView.setAdapter(adapter);
                                } else {
                                    Log.d(TAG, "Error getting documents: ", task.getException());
                                }
                            }
                        });

            }
        });

        furniture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clothes.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
                furniture.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
                device.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
                all.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
                Query p =  fStore.collection("item").whereEqualTo("Catogery", "Ø£Ø«Ø§Ø«")
                        .whereEqualTo("isRequested", "no" );

                p.get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        Log.d(TAG, document.getId() + " => " + document.getData());
                                        arrayItemF.add(new postinfo((String) document.getId(), (String) document.get("User id"), (String) document.get("Image"), (String) document.get("Description"), (String) document.get("Catogery"), (String) document.get("Title"),(String) document.get("isRequested") ,(String) document.get("Date"),"",(String) document.get("donE")));
                                        Log.d(TAG, "SIZE item list => " + arrayItemF.size());
                                    }
                                    if (arrayItemF.size()==0)
                                    {
                                        empty.setText("Ù„Ø§ ÙŠÙˆØ¬Ø¯ Ø¨ÙŠØ§Ù†Ø§Øª Ù„Ù„Ø¹Ø±Ø¶");
                                    }
                                    else{
                                        empty.setText("");
                                    }
                                    ListViewAdaptorBen adapter = new ListViewAdaptorBen(homePage.this, arrayItemF);
                                    listView = (ListView) findViewById(R.id.postedlistHomePage);
                                    listView.setAdapter(adapter);
                                } else {
                                    Log.d(TAG, "Error getting documents: ", task.getException());
                                }
                            }
                        });

            }
        });
        device.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clothes.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
                furniture.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
                device.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
                all.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
                Query p =  fStore.collection("item").whereEqualTo("Catogery", "Ø£Ø¬Ù‡Ø²Ø©")
                        .whereEqualTo("isRequested", "no" );

                p.get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        Log.d(TAG, document.getId() + " => " + document.getData());
                                        arrayItemD.add(new postinfo((String) document.getId(), (String) document.get("User id"), (String) document.get("Image"), (String) document.get("Description"), (String) document.get("Catogery"), (String) document.get("Title"),(String) document.get("isRequested") ,(String) document.get("Date"),"",(String) document.get("donE")));
                                        Log.d(TAG, "SIZE item list => " + arrayItemD.size());
                                    }
                                    if (arrayItemD.size()==0)
                                    {
                                        empty.setText("Ù„Ø§ ÙŠÙˆØ¬Ø¯ Ø¨ÙŠØ§Ù†Ø§Øª Ù„Ù„Ø¹Ø±Ø¶");
                                    }
                                    else{
                                        empty.setText("");
                                    }
                                    ListViewAdaptorBen adapter = new ListViewAdaptorBen(homePage.this, arrayItemD);
                                    listView = (ListView) findViewById(R.id.postedlistHomePage);
                                    listView.setAdapter(adapter);
                                } else {
                                    Log.d(TAG, "Error getting documents: ", task.getException());
                                }
                            }
                        });

            }
        });

        all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clothes.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
                furniture.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
                device.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
                all.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
                Query p =  fStore.collection("item")
                        .whereEqualTo("isRequested", "no" );

                p.get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        Log.d(TAG, document.getId() + " => " + document.getData());
                                        arrayItemA.add(new postinfo((String) document.getId(), (String) document.get("User id"), (String) document.get("Image"), (String) document.get("Description"), (String) document.get("Catogery"), (String) document.get("Title"),(String) document.get("isRequested") ,(String) document.get("Date"),"",(String) document.get("donE")));
                                        Log.d(TAG, "SIZE item list => " + arrayItemA.size());
                                    }
                                    if (arrayItemA.size()==0)
                                    {
                                        empty.setText("Ù„Ø§ ÙŠÙˆØ¬Ø¯ Ø¨ÙŠØ§Ù†Ø§Øª Ù„Ù„Ø¹Ø±Ø¶");
                                    }
                                    else{
                                        empty.setText("");
                                    }
                                    ListViewAdaptorBen adapter = new ListViewAdaptorBen(homePage.this, arrayItemA);
                                    listView = (ListView) findViewById(R.id.postedlistHomePage);
                                    listView.setAdapter(adapter);
                                } else {
                                    Log.d(TAG, "Error getting documents: ", task.getException());
                                }
                            }
                        });

            }
        });
        //////////////////// for list of items second try////////////////////////

        all.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);

        // FirebaseFirestore db = FirebaseFirestore.getInstance();
        fStore.collection("item")
                .whereEqualTo("isRequested", "no")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                arrayItem.add(new postinfo((String) document.getId(), (String) document.get("User id"), (String) document.get("Image"), (String) document.get("Description"), (String) document.get("Catogery"), (String) document.get("Title") ,(String) document.get("isRequested") ,(String) document.get("Date"),"",(String) document.get("donE")));
                                Log.d(TAG, "SIZE item list => " + arrayItem.size());
                            }
                            ListViewAdaptorBen adapter = new ListViewAdaptorBen(homePage.this, arrayItem);
                            listView = (ListView) findViewById(R.id.postedlistHomePage);
                            listView.setAdapter(adapter);
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }//this comment 2
                    }
                });



        //////////////////// for list of items second try////////////////////////



    }
}