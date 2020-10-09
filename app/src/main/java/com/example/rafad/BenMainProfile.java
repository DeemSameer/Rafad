package com.example.rafad;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

public class BenMainProfile extends AppCompatActivity {

    Button backHomeicon , backHomeArrow;
    StorageReference storageReference;
    FirebaseAuth fAuth;
    ImageView profileImageViewben;
    FirebaseFirestore fStore;
    String userId ;
    TextView benName, benEmail ,benPhone;
    Button editBen;


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
                Intent i = new Intent(view.getContext(),donProfile.class);
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
    }
}