package com.example.rafad;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.lang.ref.Reference;
import java.util.HashMap;
import java.util.Map;

public class postItem2 extends AppCompatActivity {

    public static final String TAG = "TAG";
    FirebaseAuth fAuth;
    Button changePostImage,share;
    ImageView postImage;
    StorageReference storageReference;
    EditText descerption;
    Uri imageUri;
    String itemID;
    String idImage;
    Reference postRef;
    FirebaseFirestore fStore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_item2);


        fAuth = FirebaseAuth.getInstance();
        postImage=findViewById(R.id.postImage);
        changePostImage=findViewById(R.id.chosepicture);
        storageReference = FirebaseStorage.getInstance().getReference();
        descerption=findViewById(R.id.descrption);
        fStore=FirebaseFirestore.getInstance();


        share = findViewById(R.id.button3);

        changePostImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // I want to open gallery
                Intent openGalleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(openGalleryIntent, 1000);
                /*Intent i = new Intent(v.getContext(),EditProfile.class);
                i.putExtra("fullName",fullName.getText().toString());
                i.putExtra("email",email.getText().toString());
                i.putExtra("phone",phone.getText().toString());
                startActivity(i);*/
//

            }
        });

        share.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {

                uploadImageToFirebase(imageUri);

                String des = descerption.getText().toString();
                String cat ="book";
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                itemID = database.getReference("item").push().getKey();
                DocumentReference documentrefReference = fStore.collection("item").document(itemID);
                //postRef=idImage;
                //store data
                Map<String, Object> post = new HashMap<>();
                post.put("image", idImage);
                post.put("description", des);
                post.put("cat", cat);

                //check the add if it's success or not
                documentrefReference.set(post).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "posted successfully " + itemID);
                    }
                });

                startActivity(new Intent(postItem2.this, homepageDonator.class));
                finish();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @androidx.annotation.Nullable Intent data){

        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1000){
            if (resultCode == Activity.RESULT_OK){
                imageUri = data.getData();

                postImage.setImageURI(imageUri);
                //upload to firebase
                //uploadImageToFirebase(imageUri);

            }
        }
    }

    private void uploadImageToFirebase(Uri imageUri) {
        //to upload image to firebase
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        String newID = database.getReference("item").push().getKey();
        idImage="items/"+newID+"post.jpg";
        final StorageReference fileRef = storageReference.child(idImage+"post.jpg");
        fileRef.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Picasso.get().load(uri).into(postImage);
                    }
                });

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(postItem2.this, " فشل تغيير الصورة ", Toast.LENGTH_SHORT).show();
            }
        });




    }
}