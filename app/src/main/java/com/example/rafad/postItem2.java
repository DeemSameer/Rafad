package com.example.rafad;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

public class postItem2 extends AppCompatActivity  {

    public static final String TAG = "TAG";
    FirebaseAuth fAuth;
    Button changePostImage,share;
    ImageView postImage;
    StorageReference storageReference;
    EditText descerption;
    Uri imageUri;
    String itemID;
    String idImage;
    String postRef;
    FirebaseFirestore fStore;
    String cat;
    Spinner dropdown;
    String UID;
    RadioGroup rg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_item2);


        RadioGroup rg = (RadioGroup) findViewById(R.id.cato);
        final String cat =
                ((RadioButton)findViewById(rg.getCheckedRadioButtonId()))
                        .getText().toString();

        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                Toast.makeText(getBaseContext(), cat, Toast.LENGTH_SHORT).show();
            }
        });
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



            }
        });
        share.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                if (imageUri != null)
                    uploadImageToFirebase(imageUri);
                else
                {descerption.setError("الرجاء رفع الصوره");
                    return;}
                String des = descerption.getText().toString();

                FirebaseDatabase database = FirebaseDatabase.getInstance();
                itemID = database.getReference("item").push().getKey();
                DocumentReference documentrefReference = fStore.collection("item").document(itemID);
                //UID
                String UID=FirebaseAuth.getInstance().getCurrentUser().getUid();
                //postRef=idImage;

                if(TextUtils.isEmpty(des)){
                    descerption.setError(" الوصف مطلوب ");
                    return;
                }



                //store data
                Map<String, Object> post = new HashMap<>();

                post.put("Image", idImage);
                post.put("Description", des);
                post.put("Catogery", cat);
                post.put("User id", UID);

                //assign itemID to the person how post it
                postRef=fStore.collection("item").document(itemID).getPath();
                DocumentReference washingtonRef = fStore.collection("donators").document(UID);
                washingtonRef.update("items", FieldValue.arrayUnion(postRef));

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
/*

    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.radio_clothes:
                if (checked)
                    cat = ((RadioButton)findViewById(radio_clothes.getCheckedRadioButtonId()))
                            .getText().toString();
                    break;
            case R.id.radio_device:
                if (checked)
                    // Ninjas rule
                    break;
            case R.id.radio_other:
                if (checked)
                    // Ninjas rule
                    break;
            case R.id.radio_tools:
                if (checked)
                    // Ninjas rule
                    break;
        }
    }*/

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