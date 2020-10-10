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

import java.util.ArrayList;
import java.util.List;

public class homepageDonator extends AppCompatActivity {
    Button logout ,profile1, post;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;


    //////// for view list of items
    List<postinfo> arrayItem=new ArrayList<>();
    public static final String TAG = "TAG";
    ListView listView;
    //////// above is for view list of items


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage_donator);

        fAuth = FirebaseAuth.getInstance();
        fStore= FirebaseFirestore.getInstance();


        logout = findViewById(R.id.logoutButton);
        profile1= findViewById(R.id.profileb);
        post= findViewById(R.id.postItem);

        //////// for view list of items
        listView=(ListView)findViewById(R.id.postedlist);
        //////// above is for view list of items


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
//////////////////// for list of items second try////////////////////////


        // FirebaseFirestore db = FirebaseFirestore.getInstance();
        fStore.collection("item")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                arrayItem.add(new postinfo((String) document.getId(), (String) document.get("User id"), (String) document.get("Image"), (String) document.get("Description"), (String) document.get("Catogery"), (String) document.get("Title") ));
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