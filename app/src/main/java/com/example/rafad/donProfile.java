package com.example.rafad;

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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class donProfile extends AppCompatActivity {

    private static final int GALLERY_INTENT_CODE=1023;
    TextView fullname , email , phone , pass;
    Button changeProfileIMG;
    FirebaseAuth fAuth;
    FirebaseFirestore fstore;
    String userID;
    FirebaseUser user;
    ImageView profileImage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_don_profile);
        phone = findViewById(R.id.phoneNo);
        fullname =findViewById(R.id.name);
        email = findViewById(R.id.userEmail);
        profileImage= findViewById(R.id.profileImg);
        changeProfileIMG=findViewById(R.id.change);


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
            }
        }
   }



}