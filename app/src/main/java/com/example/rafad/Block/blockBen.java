package com.example.rafad.Block;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.example.rafad.R;
import com.example.rafad.benDataModel;
import com.example.rafad.homePageAdminBase;
import com.example.rafad.homepageAdmin;
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

public class blockBen extends AppCompatActivity {
    FirebaseAuth fAuth;
    List<BenModelBlock> arrayList=new ArrayList<>();
    public static final String TAG = "TAG";
    ListView list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bock_ben);

        fAuth = FirebaseAuth.getInstance();
        final FirebaseFirestore db=FirebaseFirestore.getInstance();

        Button back = (Button) findViewById(R.id.but);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(blockBen.this, homePageAdminBase.class));
                finish();
            }
        });

        final BlockBenList adapter=new BlockBenList(blockBen.this, arrayList);
        list=(ListView)findViewById(R.id.list);
        list.setAdapter(adapter);

        db.collection("Reports").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        final String count=document.get("count").toString();
                        DocumentReference docRef = db.collection("beneficiaries").document((String)document.getId());
                        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                if (task.isSuccessful()) {
                                    DocumentSnapshot document = task.getResult();
                                    if (document.exists()) {
                                        Log.d(TAG, (String)document.get("phoneNumber") + " lalalalal");

                                        arrayList.add(new BenModelBlock((String)document.getId(), count, (String)document.get("phoneNumber"),(String)document.get("userName"),(String)document.get("email")));
                                        adapter.notifyDataSetChanged();
                                    }
                                }
                            }
                        });

                    }

                }
            }
        });




    }
}