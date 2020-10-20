package com.example.rafad;

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

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;
import android.widget.ListView;
import java.util.ArrayList;
import java.util.List;

public class requests extends AppCompatActivity {
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    List<postinfo> arrayItem=new ArrayList<>();
    ListView listView;
    String BenId;
     String itemId;
     Task<DocumentSnapshot> benN;
     String benS;
    public static final String TAG = "TAG";
    int postion;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_requests);
        fAuth = FirebaseAuth.getInstance();
        listView = (ListView) findViewById(R.id.postedlistHomePage1);
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
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                BenId=(String) document.get("benID");
                                benN=fStore.collection("beneficiaries").document(BenId).get();
                                Log.d(TAG,  "benN"+benN.getResult());
                                arrayItem.add(new postinfo((String) document.getId(), (String) document.get("User id"), (String) document.get("Image"),(String) document.get("Title")));
/*
DocumentReference docRef = fStore.collection("beneficiaries").document((String)document.get("BenId"));
docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
    @Override
    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
        if (task.isSuccessful()) {
            DocumentSnapshot document = task.getResult();

            if (document.exists()) {

                Log.d(TAG, "DocumentSnapshot data: " + document.getData());//Put your get here -- For ben account
                arrayItem.set(arrayItem.get(position),arrayItem.get(poistioin).setBN((String) document.get("userName")));
                arrayItem.set(arrayItem.get(position),arrayItem.get(poistioin).setBS((String) document.get("State")));
            } else {
                Log.d(TAG, "No such document");
            }
        } else {
            Log.d(TAG, "get failed with ", task.getException());
        }
    }
});
*/
                                Log.d(TAG, "SIZE item list1 => " + arrayItem.size());
                            }
                            AdapterD adapter = new AdapterD(requests.this, arrayItem);
                            listView = (ListView) findViewById(R.id.postedlistHomePage1);
                            listView.setAdapter(adapter);
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });

    }
}