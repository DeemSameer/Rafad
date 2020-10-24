package com.example.rafad;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;
import android.graphics.Paint;
import android.widget.TextView;
import android.widget.Toast;

public class homepageDonator extends AppCompatActivity {
    Button logout ,profile1, post , clothes , furniture,device,all;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;

    //////// for view list of items
    List<postinfo> arrayItem=new ArrayList<>();
    List<postinfo> arrayItemC=new ArrayList<>();
    List<postinfo> arrayItemD=new ArrayList<>();
    List<postinfo> arrayItemF=new ArrayList<>();
    List<postinfo> arrayItemA=new ArrayList<>();
    public static final String TAG = "TAG";
    ListView listView;
    TextView empty;
    //////// above is for view list of items


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage_donator);

        fAuth = FirebaseAuth.getInstance();
        fStore= FirebaseFirestore.getInstance();
        clothes = findViewById(R.id.clothes1);
        furniture = findViewById(R.id.furniture);
        device = findViewById(R.id.device1);
        all = findViewById(R.id.all1);


        logout = findViewById(R.id.logoutButton);
        profile1= findViewById(R.id.profileb);
        post= findViewById(R.id.postItem);
        Button b=findViewById(R.id.button10);
         empty = findViewById(R.id.homepagetext22);
        //////// for view list of items
        listView=(ListView)findViewById(R.id.postedlistDonaterHome);
        //////// above is for view list of items

        clothes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clothes.setPaintFlags(clothes.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
                Query p =  fStore.collection("item").whereEqualTo("Catogery", "ملابس")
                        .whereEqualTo("isRequested", "no" );

                                p.get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        Log.d(TAG, document.getId() + " => " + document.getData());
                                        arrayItemC.add(new postinfo((String) document.getId(), (String) document.get("User id"), (String) document.get("Image"), (String) document.get("Description"), (String) document.get("Catogery"), (String) document.get("Title"),(String) document.get("isRequested") ,(String) document.get("Date"),""));
                                        Log.d(TAG, "SIZE item list => " + arrayItemC.size());
                                    }
                                    if (arrayItemC.size()==0)
                                    {
                                        Toast toast= Toast.makeText(getApplicationContext(),
                                                "عذراً لا يوجد بيانات للعرض", Toast.LENGTH_SHORT);
                                        toast.setGravity(Gravity.TOP|Gravity.CENTER_HORIZONTAL, 50, 50);
                                        toast.show();
                                    }

                                    HistoryItemAdapter adapter = new HistoryItemAdapter(homepageDonator.this, arrayItemC);
                                    listView = (ListView) findViewById(R.id.postedlistDonaterHome);
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

                Query p =  fStore.collection("item").whereEqualTo("Catogery", "أثاث")
                        .whereEqualTo("isRequested", "no" );

                p.get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        Log.d(TAG, document.getId() + " => " + document.getData());
                                        arrayItemF.add(new postinfo((String) document.getId(), (String) document.get("User id"), (String) document.get("Image"), (String) document.get("Description"), (String) document.get("Catogery"), (String) document.get("Title"),(String) document.get("isRequested") ,(String) document.get("Date"),""));
                                        Log.d(TAG, "SIZE item list => " + arrayItemF.size());
                                    }
                                    if (arrayItemF.size()==0)
                                    {
                                        Toast toast= Toast.makeText(getApplicationContext(),
                                                "عذراً لا يوجد بيانات للعرض", Toast.LENGTH_SHORT);
                                        toast.setGravity(Gravity.TOP|Gravity.CENTER_HORIZONTAL, 50, 50);
                                        toast.show();
                                    }
                                    HistoryItemAdapter adapter = new HistoryItemAdapter(homepageDonator.this, arrayItemF);
                                    listView = (ListView) findViewById(R.id.postedlistDonaterHome);
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

                Query p =  fStore.collection("item").whereEqualTo("Catogery", "أجهزة")
                        .whereEqualTo("isRequested", "no" );

                p.get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        Log.d(TAG, document.getId() + " => " + document.getData());
                                        arrayItemD.add(new postinfo((String) document.getId(), (String) document.get("User id"), (String) document.get("Image"), (String) document.get("Description"), (String) document.get("Catogery"), (String) document.get("Title"),(String) document.get("isRequested") ,(String) document.get("Date"),""));
                                        Log.d(TAG, "SIZE item list => " + arrayItemD.size());
                                    }
                                    if (arrayItemD.size()==0)
                                       {
                                           Toast toast= Toast.makeText(getApplicationContext(),
                                                   "عذراً لا يوجد بيانات للعرض", Toast.LENGTH_SHORT);
                                           toast.setGravity(Gravity.TOP|Gravity.CENTER_HORIZONTAL, 50, 50);
                                           toast.show();
                                       }
                                    HistoryItemAdapter adapter = new HistoryItemAdapter(homepageDonator.this, arrayItemD);
                                    listView = (ListView) findViewById(R.id.postedlistDonaterHome);
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

                Query p =  fStore.collection("item")
                        .whereEqualTo("isRequested", "no" );

                p.get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        Log.d(TAG, document.getId() + " => " + document.getData());
                                        arrayItemA.add(new postinfo((String) document.getId(), (String) document.get("User id"), (String) document.get("Image"), (String) document.get("Description"), (String) document.get("Catogery"), (String) document.get("Title"),(String) document.get("isRequested") ,(String) document.get("Date"),""));
                                        Log.d(TAG, "SIZE item list => " + arrayItemA.size());
                                    }
                                    if (arrayItemA.size()==0)
                                    {
                                        Toast toast= Toast.makeText(getApplicationContext(),
                                                "عذراً لا يوجد بيانات للعرض", Toast.LENGTH_SHORT);
                                        toast.setGravity(Gravity.TOP|Gravity.CENTER_HORIZONTAL, 50, 50);
                                        toast.show();
                                    }
                                    HistoryItemAdapter adapter = new HistoryItemAdapter(homepageDonator.this, arrayItemA);
                                    listView = (ListView) findViewById(R.id.postedlistDonaterHome);
                                    listView.setAdapter(adapter);
                                } else {
                                    Log.d(TAG, "Error getting documents: ", task.getException());
                                }
                            }
                        });

            }
        });
        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Intent i = new Intent(homepageDonator.this, postitem.class);
                startActivity(new Intent(homepageDonator.this, post3.class));
                finish();
            }
        });
        profile1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(homepageDonator.this, mainProfile.class));
                finish();

            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fAuth.signOut();
                startActivity(new Intent(homepageDonator.this, login.class));
                finish();
            }
        });
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(homepageDonator.this, requests.class));
                finish();

            }
        });


//////////////////// for list of items second try////////////////////////


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
                                arrayItem.add(new postinfo((String) document.getId(), (String) document.get("User id"), (String) document.get("Image"), (String) document.get("Description"), (String) document.get("Catogery"), (String) document.get("Title"),(String) document.get("isRequested") ,(String) document.get("Date"),""));
                                Log.d(TAG, "SIZE item list => " + arrayItem.size());
                            }
                            HistoryItemAdapter adapter = new HistoryItemAdapter(homepageDonator.this, arrayItem);
                            listView = (ListView) findViewById(R.id.postedlistDonaterHome);
                            listView.setAdapter(adapter);
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });



        //////////////////// for list of items second try////////////////////////


    }


}