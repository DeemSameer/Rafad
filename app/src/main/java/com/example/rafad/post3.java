package com.example.rafad;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

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

public class post3 extends AppCompatActivity {

    public static final String TAG = "TAG";
    FirebaseAuth fAuth;
    Button changePostImage,share,cancel;
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
    RadioButton rg1;
    RadioButton rg2;
    RadioButton rg3;
    RadioButton rg4;
    String des;
    String tit;
    TextView category;
    DocumentReference documentrefReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post3);

        fAuth = FirebaseAuth.getInstance();
        postImage=findViewById(R.id.postImage);
        changePostImage=findViewById(R.id.chosepicture);
        storageReference = FirebaseStorage.getInstance().getReference();
        descerption=findViewById(R.id.descrption);
        fStore=FirebaseFirestore.getInstance();
        share = findViewById(R.id.button3);
        cancel = findViewById(R.id.button4);
        category=findViewById(R.id.textViewcat);





        changePostImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // I want to open gallery
                Intent openGalleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(openGalleryIntent, 1000);
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                startActivity(new Intent(post3.this, homepageDonator.class));
                finish();

            }
        });


        share.setOnClickListener(new View.OnClickListener() {


            public void onClick(View view) {

                rg1 = (RadioButton) findViewById(R.id.radio_device);
                if ((rg1.isChecked() )){
                    cat ="أجهزة";
                }
                rg2 = (RadioButton) findViewById(R.id.radio_clothes);
                if ((rg2.isChecked() )){
                    cat ="ملابس";
                }
                rg3 = (RadioButton) findViewById(R.id.radio_tools);
                if ((rg3.isChecked() )){
                    cat ="أثاث";
                }
                rg4 = (RadioButton) findViewById(R.id.radio_other);
                if ((rg4.isChecked() )){
                    cat ="غير ذلك";
                }
                if (imageUri != null)
                    uploadImageToFirebase(imageUri);
                else
                {changePostImage.setError("الرجاء رفع الصورة");
                    return;}
                des = descerption.getText().toString();
                EditText title=findViewById(R.id.title);
                tit = title.getText().toString();
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                itemID = database.getReference("item").push().getKey();
                documentrefReference = fStore.collection("item").document(itemID);
                //UID
                UID=FirebaseAuth.getInstance().getCurrentUser().getUid();
                //postRef=idImage;
                if(TextUtils.isEmpty(tit)){
                    title.setError(" العنوان مطلوب ");
                    return;
                }
                if(tit.length() >=20){
                    title.setError(" العنوان يجب أن يكون أقل من 20 حرف ");
                    return;
                }
                if(TextUtils.isEmpty(des)){
                    descerption.setError(" الوصف مطلوب ");
                    return;
                }
                if(des.length() >=300){
                    descerption.setError(" الوصف يجب أن يكون أقل من 300 حرف ");
                    return;
                }
                if(cat ==null){
                    category.setError("  الرجاء اختيار الفئة ");
                    return;
                }
                new AlertDialog.Builder(post3.this)
                        .setTitle("نشر ")
                        .setMessage("هل أنت متأكد من النشر؟")
                        .setPositiveButton("نعم", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {




                                //store data
                                Map<String, Object> post = new HashMap<>();

                                post.put("Image", idImage);
                                post.put("Title", tit);
                                post.put("Description", des);
                                post.put("Catogery", cat);
                                post.put("User id", UID);
                                post.put("isRequested", "no");
                                post.put("benID", "123");


                                //assign itemID to the person how post it
                              /*  postRef=fStore.collection("item").document(itemID).getPath();
                                DocumentReference washingtonRef = fStore.collection("donators").document(UID);
                                washingtonRef.update("items", FieldValue.arrayUnion(postRef));*/

                                //check the add if it's success or not
                                documentrefReference.set(post).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Log.d(TAG, "posted successfully " + itemID);
                                    }
                                });

                                startActivity(new Intent(post3.this, homepageDonator.class));
                                finish();



                                Toast.makeText(post3.this, " تم النشر بنجاح", Toast.LENGTH_SHORT).show();


                                //dialog1.dismiss();
                            }
                        }).setNegativeButton("إلغاء", null).show();
                AlertDialog dialog1;



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
        final StorageReference fileRef = storageReference.child(idImage);
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
                Toast.makeText(post3.this, " فشل تغيير الصورة ", Toast.LENGTH_SHORT).show();
            }
        });

    }
}