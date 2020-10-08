package com.example.rafad;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

public class adminMainProfile extends AppCompatActivity {

    StorageReference storageReference;
    FirebaseAuth fAuth;
    ImageView profileImageView;
    FirebaseFirestore fStore;
    String userId ;
    EditText adminFullName  , adminPhone;
    TextView adminEmail;
    Button changeimg , backHomeIcon, backHomArrow, saveChanges;
    FirebaseUser user;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_main_profile);

        storageReference = FirebaseStorage.getInstance().getReference();
        fAuth = FirebaseAuth.getInstance();
        fStore= FirebaseFirestore.getInstance();
        userId = fAuth.getCurrentUser().getUid();
        user = fAuth.getCurrentUser();

        profileImageView = findViewById(R.id.adminprofileImg);
        adminFullName = findViewById(R.id.a2);
        adminEmail =findViewById(R.id.e2);
        adminPhone =findViewById(R.id.ph2);
        changeimg = findViewById(R.id.adminChangeBenImg);
        backHomeIcon = findViewById(R.id.adminbackHome);
        backHomArrow = findViewById(R.id.adminbHome);
        saveChanges = findViewById(R.id.adminEdit);



        DocumentReference documentReference1 = fStore.collection("admins").document(userId);
        documentReference1.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                adminPhone.setText(value.getString("phoneNumber"));
                adminFullName.setText(value.getString("userName"));
                adminEmail.setText(value.getString("email"));

            }
        });



        StorageReference profileRef = storageReference.child("users/"+fAuth.getCurrentUser().getUid()+"profile.jpg");
        profileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(profileImageView);

            }
        });

        saveChanges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //extract what the user entr
                if (adminFullName.getText().toString().isEmpty()){
                    Toast.makeText(adminMainProfile.this, " لا يمكن ترك الإسم فارغًا!  ", Toast.LENGTH_LONG).show();
                    adminFullName.setError("لا يمكن ترك الإسم فارغًا");
                    return;
                }

                if (adminPhone.getText().toString().isEmpty()){
                    Toast.makeText(adminMainProfile.this, " لا يمكن ترك رقم الجوال فارغًا! ", Toast.LENGTH_LONG).show();
                    adminPhone.setError(" لا يمكن ترك رقم الجوال فارغًا ! ");
                    return;

                }
                if (adminPhone.getText().toString().length()!=10){
                    adminPhone.setError(" يجب أن يتكون رقم الجوال من 10 أرقام ");
                    return;
                }

                if (!adminPhone.getText().toString().substring(0,2).equals("05")){
                    adminPhone.setError(" يجب أن يبدأ رقم الجوال بـ 05 ");
                    return;
                }



                //extract the email to change it
               final String email2 = adminEmail.getText().toString();
                final String name2=adminFullName.getText().toString();
                final String phone2=adminPhone.getText().toString();


                user.updateEmail(email2).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        DocumentReference docRef = fStore.collection("admins").document(user.getUid());
                        //put new data to firebase
                        Map<String,Object> edited = new HashMap<>();
                        //edited.put("email", email2);
                        edited.put("userName", adminFullName.getText().toString());
                        edited.put("phoneNumber", adminPhone.getText().toString());
                        docRef.update(edited).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {

                               /* if(adminFullName.getText().toString().equals(name2) && adminPhone.getText().toString().equals(phone2)){
                                    startActivity(new Intent(getApplicationContext(), homepageAdmin.class));
                                    finish();
                                }

                                else {*/
                                    Toast.makeText(adminMainProfile.this, " تم تحديث الملف الشخصي بنجاح ", Toast.LENGTH_LONG).show();
                                    startActivity(new Intent(getApplicationContext(), homepageAdmin.class));
                                    finish();  //}
                            }
                        });

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        String exMsg = e.getMessage();
                        if (exMsg.equals("The email address is already in use by another account.")){
                            Toast.makeText(adminMainProfile.this, "البريد الإلكتروني موجود مسبقًا  ", Toast.LENGTH_LONG).show();
                        }

                        if (exMsg.equals("This operation is sensitive and requires recent authentication. Log in again before retrying this request.")){
                            Toast.makeText(getApplicationContext(),  "هذه العملية حساسة وتتطلب مصادقة حديثة. قم بتسجيل الدخول مرة أخرى قبل إعادة محاولة هذا الطلب.", Toast.LENGTH_LONG).show();
                        }
                        else {
                            Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_LONG).show();
                        }

                    }
                });

            }
        });



        backHomeIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(adminMainProfile.this, homePageAdminBase.class));
                finish();

            }
        });

        backHomArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(adminMainProfile.this, homePageAdminBase.class));
                finish();

            }
        });











        changeimg.setOnClickListener(new View.OnClickListener() {
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
                //profileImage.setImageURI(imageUri);
                //upload to firebase
                uploadImageToFirebase(imageUri);


            }
        }
    }
    private void uploadImageToFirebase(Uri imageUri) {
        //to upload image to firebase
        final StorageReference fileRef = storageReference.child("users/"+fAuth.getCurrentUser().getUid()+"profile.jpg");
        fileRef.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Picasso.get().load(uri).into(profileImageView);
                        Toast.makeText(adminMainProfile.this, "تم تحديث الصورة بنجاح", Toast.LENGTH_LONG).show();

                    }
                });

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(adminMainProfile.this, " فشل تغيير الصورة ", Toast.LENGTH_SHORT).show();
            }
        });




    }

}




