package com.example.rafad;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class BenMainProfile extends AppCompatActivity {

    Button backHomeicon , backHomeArrow;
    StorageReference storageReference;
    FirebaseAuth fAuth;
    ImageView profileImageViewben;
    FirebaseFirestore fStore;
    String userId ;
    TextView benName, benEmail ,benPhone;
    Button editBen;

    //////// for view list of items
    List<postinfo> arrayItem=new ArrayList<>();
    public static final String TAG = "TAG";
    ListView listView;
    //////// above is for view list of items

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ben_main_profile);

        backHomeArrow = findViewById(R.id.benBackHome);
        backHomeicon = findViewById(R.id.benHomeicon);
        storageReference = FirebaseStorage.getInstance().getReference();
        profileImageViewben =findViewById(R.id.profileImgben);
        benEmail = findViewById(R.id.benEmail);
        benName = findViewById(R.id.benName);
        benPhone = findViewById(R.id.benPhone);
        editBen = findViewById(R.id.editBen);

        fAuth = FirebaseAuth.getInstance();
        fStore= FirebaseFirestore.getInstance();
        userId = fAuth.getCurrentUser().getUid();


        //////// for view list of items
        listView=(ListView)findViewById(R.id.benprofillist);
        //////// above is for view list of items


        StorageReference profileRef = storageReference.child("users/"+fAuth.getCurrentUser().getUid()+"profile.jpg");
        profileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(profileImageViewben);
            }
        });


        DocumentReference documentReference = fStore.collection("beneficiaries").document(userId);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                benPhone.setText(value.getString("phoneNumber"));
                benName.setText(value.getString("userName"));
                benEmail.setText(value.getString("email"));
            }
        });


        editBen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(view.getContext(),benEditProfile.class);
                //pass the data
                i.putExtra("fullName",benName.getText().toString());
                i.putExtra("email",benEmail.getText().toString());
                i.putExtra("phone", benPhone.getText().toString());
                startActivity(i);

            }
        });






        backHomeicon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(BenMainProfile.this, homePage.class));
                finish();
            }
        });


        backHomeArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(BenMainProfile.this, homePage.class));
                finish();
            }
        });

        //////////////////// for list of items second try////////////////////////


        // FirebaseFirestore db = FirebaseFirestore.getInstance();
        fStore.collection("item")
                .whereEqualTo("benID", userId)
                .whereEqualTo("isRequested", "yes")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " BenMainProfile=> " + document.getData());
                                arrayItem.add(new postinfo((String) document.getId(), (String) document.get("User id"), (String) document.get("Image"), (String) document.get("Description"), (String) document.get("Catogery"), (String) document.get("Title"),(String) document.get("isRequested") ,(String) document.get("Date"),"",(String) document.get("Demail")));
                                Log.d(TAG, "SIZE item list => " + arrayItem.size());
                            }
                            HistoryItemAdapter adapter = new HistoryItemAdapter(BenMainProfile.this, arrayItem);
                            listView = (ListView) findViewById(R.id.benprofillist);
                            listView.setAdapter(adapter);
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });



        //////////////////// for list of items second try////////////////////////

    }
}