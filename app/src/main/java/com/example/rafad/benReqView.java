package com.example.rafad;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
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
    Button homebutton;
    TextView empty;
    public static final String TAG = "TAG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ben_req_view);

        fAuth = FirebaseAuth.getInstance();
        listView = (ListView) findViewById(R.id.benReqListView);
        back=findViewById(R.id.benReqback);
        homebutton=findViewById(R.id.benReqToHomeicon);
        empty = findViewById(R.id.benReqEmpty);
        fStore= FirebaseFirestore.getInstance();






        fStore.collection("item")
                .whereEqualTo("isRequested", "Pending")
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


                                DocumentReference docRef=fStore.collection("donators").document(UID);//.get("User id")
                                docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                        if (task.isSuccessful()){
                                            DocumentSnapshot document = task.getResult();
                                            if (document.exists()) {
                                                Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                                                 donN= (String)document.get("userName");
                                                /*FirebaseFirestore db = FirebaseFirestore.getInstance();
                                                CollectionReference beneficiaries = db.collection("item");
                                                DocumentReference docRefB = beneficiaries.document(itemId);
                                                docRefB.update("donN", donN);
                                                Log.d(TAG, "donnN data after request:0 " + donN);
                                                donN=FirebaseAuth.getInstance().getCurrentUser().getDisplayName();
                                                Log.d(TAG, "donnN data after request:3 " + donN);*/

                                                Log.d(TAG, "donnN data after request: " + donN);
                                                Log.d(TAG, "donnN data after request:1 " + (String) document1.get("donN"));
                                                arrayItem.add(new postinfo((String) document1.getId(), (String) document1.get("User id"), (String) document1.get("Image"),(String) document1.get("Title"), (String) document1.get("donN"), (String) document1.get("benS"),(String) document1.get("isRequested"), (String) document1.get("benID")));






                                            }

                                        }

                                    }


                                });









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
                            AdabterBenReq adapter = new AdabterBenReq(benReqView.this, arrayItem);
                            listView = (ListView) findViewById(R.id.benReqListView);
                            listView.setAdapter(adapter);
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });


    }
}