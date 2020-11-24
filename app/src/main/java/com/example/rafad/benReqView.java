package com.example.rafad;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.rafad.ChatJava.MainChatAllPeople;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class benReqView extends AppCompatActivity {

    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    List<postinfo> arrayItem=new ArrayList<>();
    ListView listView;
    String benN, donN;
    String benS;
    String BenId;
    String itemId;
    String UID;
    String imageUri;
    String tit;
    String isRe;
    Button back;
    Button homebutton, toPro,button15;
    TextView empty;
    double Rating;
    public static final String TAG = "TAG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ben_req_view);

        fAuth = FirebaseAuth.getInstance();
        listView = (ListView) findViewById(R.id.benReqListView);
        back=findViewById(R.id.benReqback);
        toPro=findViewById(R.id.benReqToprofile);
        homebutton=findViewById(R.id.benReqToHomeicon);
        empty = findViewById(R.id.benReqEmpty);
        fStore= FirebaseFirestore.getInstance();
        button15=findViewById(R.id.button15);



        final AdabterBenReq adapter = new AdabterBenReq(benReqView.this, arrayItem);
        listView = (ListView) findViewById(R.id.benReqListView);
        listView.setAdapter(adapter);



        fStore.collection("item")
                .whereEqualTo("benID",FirebaseAuth.getInstance().getCurrentUser().getUid())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {

                        if (task.isSuccessful()) {
                            for (final QueryDocumentSnapshot document1 : task.getResult()) {
                                Log.d(TAG, document1.getId() + " req=> " + document1.getData());
                                Log.d(TAG,  " (String)document.get(\"benID\") " + (String)document1.get("benID"));

                                itemId=(String) document1.getId();
                                UID=(String) document1.get("User id");
                                imageUri=(String) document1.get("Image");
                                tit=(String) document1.get("Title");
                                isRe=(String) document1.get("isRequested");
                                BenId=(String) document1.get("benID");
                                Log.d(TAG, "isRe data request: " + isRe);
                                
                                Log.d(TAG, "donnN data after request: " + donN);

                                //////////////////////////////////////////////////////////////////////////////////

                                CollectionReference donators = fStore.collection("donators");
                                DocumentReference docRefB = donators.document(UID);

                                docRefB.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                        if (task.isSuccessful()) {
                                            DocumentSnapshot document = task.getResult();
                                            if (document.exists()) {
                                                Log.d(TAG, "inter AdaptorBenReq doc DocumentSnapshot data: " + document.getData());
                                                Rating=document.getDouble("Rate");
                                                arrayItem.add(new postinfo((String) document1.getId(), (String) document1.get("User id"), (String) document1.get("Image"),(String) document1.get("Title"), (String) document1.get("donN"), (String) document1.get("benS"),(String) document1.get("isRequested"), (String) document1.get("benID"),(String) document1.get("IsRated"),Rating));
                                                adapter.notifyDataSetChanged();
                                                Log.d(TAG, "Rate inside request => " + Rating);
                                                Log.d(TAG, "SIZE item list inside request => " + arrayItem.size());
                                            } else {
                                                Log.d(TAG, "No such document");
                                            }
                                        } else {
                                            Log.d(TAG, "get failed with ", task.getException());
                                        }
                                    }
                                });

                                /////////////////////////////////////////////////////////////////////////////////


                                //arrayItem.add(new postinfo((String) document1.getId(), (String) document1.get("User id"), (String) document1.get("Image"),(String) document1.get("Title"), (String) document1.get("donN"), (String) document1.get("benS"),(String) document1.get("isRequested"), (String) document1.get("benID"),(String) document1.get("IsRated"),(double) 0));







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
                            /*AdabterBenReq adapter = new AdabterBenReq(benReqView.this, arrayItem);
                            listView = (ListView) findViewById(R.id.benReqListView);
                            listView.setAdapter(adapter);*/
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Intent i = new Intent(homepageDonator.this, postitem.class);
                startActivity(new Intent(benReqView.this, homePage.class));
                finish();
            }
        });
        homebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(benReqView.this, homePage.class));
                finish();

            }
        });
        toPro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(benReqView.this, BenMainProfile.class));
                finish();

            }
        });

        button15.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(benReqView.this, MainChatAllPeople.class));
                finish();

            }
        });


    }
}