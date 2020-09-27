package com.example.rafad;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class donProfile extends AppCompatActivity {

    private static final int GALLERY_INTENT_CODE=1023;
    TextView fullname , email , phone , pass;
    Button changeProfileIMG, resetPassLocal, resendCode;
    FirebaseAuth fAuth;
    FirebaseFirestore fstore;
    String userID;
    FirebaseUser user;
    ImageView profileImage;
    StorageReference storageRefrence;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_don_profile);
        phone = findViewById(R.id.phoneNo);
        fullname =findViewById(R.id.name);
        email = findViewById(R.id.userEmail);
        profileImage= findViewById(R.id.profileImg);
        changeProfileIMG=findViewById(R.id.change);

        fAuth = FirebaseAuth.getInstance();
        fstore = FirebaseFirestore.getInstance();
        storageRefrence = FirebaseStorage.getInstance().getReference();


        changeProfileIMG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //to open the gallery , create new intent
                Intent openGalleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(openGalleryIntent, 1000);

            }
        });

    }

   @Override
   protected void onActivityResult(int requestCode, int resultCode, @androidx.annotation.Nullable Intent data){

        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1000){
            if (resultCode == Activity.RESULT_OK){
                Uri imageUri = data.getData();
                profileImage.setImageURI(imageUri);
                //upload to firebase
                uploadImageToFirebase(imageUri);

            }
        }
   }

    private void uploadImageToFirebase(Uri imageUri) {
        //to upload image to firebase
        StorageReference fileRef = storageRefrence.child("profile.jpg");
        fileRef.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(donProfile.this, " تم تحميل الصورة بنجاح ", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(donProfile.this, " فشل تحميل الصورة ", Toast.LENGTH_SHORT).show();
            }
        });
    }

    }


