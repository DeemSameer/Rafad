package com.example.rafad;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class don_3view extends AppCompatActivity {
    Button all,req,done;
    TextView empty;
    String benN;
    String benS;
    String BenId;
    String itemId;
    String UID;
    String imageUri;
    String tit;
    String isRe;
    Button back;
 Activity context;
    public static final String TAG = "TAG";
    StorageReference storageRef;
    FirebaseFirestore fStore;
    FirebaseAuth fAuth;
    List<postinfo> arrayItem,arrayItem2,arrayItem3;
    ListView listView,listView2,listView3;
    String userId ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_don_3view);
        listView=(ListView)findViewById(R.id.postedlist);
        fAuth = FirebaseAuth.getInstance();
        all = findViewById(R.id.all_don);
        req = findViewById(R.id.req_don);
        done = findViewById(R.id.done_don);
                empty = findViewById(R.id.homepagetext);
        fStore= FirebaseFirestore.getInstance();
        userId = fAuth.getCurrentUser().getUid();
        all.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                arrayItem=new ArrayList<>();
                Query p =  fStore.collection("item")
                        .whereEqualTo("User id",FirebaseAuth.getInstance().getCurrentUser().getUid())
                        .whereEqualTo("isRequested", "no" );

                p.get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        Log.d(TAG, document.getId() + " => " + document.getData());
                                        arrayItem.add(new postinfo((String) document.getId(), (String) document.get("User id"), (String) document.get("Image"), (String) document.get("Description"), (String) document.get("Catogery"), (String) document.get("Title"),(String) document.get("isRequested") ,(String) document.get("Date"),"",(String) document.get("Demail")));
                                        Log.d(TAG, "SIZE item list => " + arrayItem.size());
                                    }
                                    if (arrayItem.size()==0)
                                    {
                                        empty.setText("لا يوجد بيانات للعرض");
                                    }
                                    else{
                                        empty.setText("");
                                    }
                                    Adapter2 adapter = new Adapter2(don_3view.this, arrayItem);
                                    listView = (ListView) findViewById(R.id.postedlistDonaterHome);
                                    listView.setAdapter(adapter);
                                } else {
                                    Log.d(TAG, "Error getting documents: ", task.getException());
                                }
                            }
                        });

            }
        });
        req.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                arrayItem2=new ArrayList<>();
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
                                        arrayItem2.add(new postinfo((String) document.getId(), (String) document.get("User id"), (String) document.get("Image"),(String) document.get("Title"), (String) document.get("benN"), (String) document.get("benS"),(String) document.get("isRequested"), (String) document.get("benID")));







                                        Log.d(TAG, "SIZE item list request => " + arrayItem2.size());
                                    }
                                    if (arrayItem2.size()==0)
                                    {
                                        //Toast.makeText(requests.this, "This is my Toast message!",
                                        //  Toast.LENGTH_LONG).show();
                                        empty.setText("لا يوجد بيانات للعرض");
                                    }
                                    else{
                                        empty.setText("");
                                    }
                                    AdapterD adapter2 = new AdapterD(don_3view.this, arrayItem2);
                                    listView2 = (ListView) findViewById(R.id.postedlistDonaterHome);
                                    listView2.setAdapter(adapter2);
                                } else {
                                    Log.d(TAG, "Error getting documents: ", task.getException());
                                }
                            }
                        });

            }
        });
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                arrayItem3=new ArrayList<>();
                Query p =  fStore.collection("item")
                        .whereEqualTo("User id",FirebaseAuth.getInstance().getCurrentUser().getUid())
                        .whereEqualTo("isRequested", "yes" );

                p.get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        Log.d(TAG, document.getId() + " => " + document.getData());
                                        arrayItem3.add(new postinfo((String) document.getId(), (String) document.get("User id"), (String) document.get("Image"), (String) document.get("Description"), (String) document.get("Catogery"), (String) document.get("Title"),(String) document.get("isRequested") ,(String) document.get("Date"),"",(String) document.get("Demail")));
                                        Log.d(TAG, "SIZE item list => " + arrayItem3.size());
                                    }
                                    if (arrayItem3.size()==0)
                                    {
                                        empty.setText("لا يوجد بيانات للعرض");
                                    }
                                    else{
                                        empty.setText("");
                                    }
                                    HistoryItemAdapter adapter3 = new HistoryItemAdapter(don_3view.this, arrayItem3);
                                    listView3 = (ListView) findViewById(R.id.postedlistDonaterHome);
                                    listView3.setAdapter(adapter3);
                                } else {
                                    Log.d(TAG, "Error getting documents: ", task.getException());
                                }
                            }
                        });

            }
        });


    }
}