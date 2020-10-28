package com.example.rafad;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class requests extends AppCompatActivity {
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    List<postinfo> arrayItem=new ArrayList<>();
    ListView listView;
    String benN;
    String benS;
    String BenId;
    String itemId;
    String UID;
    String imageUri;
    String tit;
    String isRe;
    Button back;
    Button homebutton;
    TextView empty;






    public static final String TAG = "TAG";
    int postion;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_requests);
        fAuth = FirebaseAuth.getInstance();
        listView = (ListView) findViewById(R.id.postedlistHomePage1);
        back=findViewById(R.id.button5);
        homebutton=findViewById(R.id.bHome);
        empty = findViewById(R.id.homepagetext23);
        fStore= FirebaseFirestore.getInstance();
        fStore.collection("item")
                .whereEqualTo("isRequested", "Pending")
                .whereEqualTo("User id",FirebaseAuth.getInstance().getCurrentUser().getUid())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {

                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " req=> " + document.getData());
                                Log.d(TAG,  " (String)document.get(\"benID\") " + (String)document.get("benID"));

                                itemId=(String) document.getId();
                                UID=(String) document.get("User id");
                                imageUri=(String) document.get("Image");
                                tit=(String) document.get("Title");
                                isRe=(String) document.get("isRequested");
                                BenId=(String) document.get("benID");
                                Log.d(TAG, "isRe data request: " + isRe);






                                Log.d(TAG, "benN data after request: " + benN);
                                arrayItem.add(new postinfo((String) document.getId(), (String) document.get("User id"), (String) document.get("Image"),(String) document.get("Title"), (String) document.get("benN"), (String) document.get("benS"),(String) document.get("isRequested"), (String) document.get("benID")));







                                Log.d(TAG, "SIZE item list request => " + arrayItem.size());
                            }
                            if (arrayItem.size()==0)
                            {
                                //Toast.makeText(requests.this, "This is my Toast message!",
                                      //  Toast.LENGTH_LONG).show();
                                empty.setText("لا يوجد بيانات للعرض");
                            }
                            else{
                                empty.setText("");
                            }
                            AdapterD adapter = new AdapterD(requests.this, arrayItem);
                            listView = (ListView) findViewById(R.id.postedlistHomePage1);
                            listView.setAdapter(adapter);
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                       }
                    }
                });
    

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Intent i = new Intent(homepageDonator.this, postitem.class);
                startActivity(new Intent(requests.this, mainProfile.class));
                finish();
            }
        });
        homebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(requests.this, homepageDonator.class));
                finish();

            }
        });
    }
}