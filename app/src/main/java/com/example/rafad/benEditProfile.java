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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

public class benEditProfile extends AppCompatActivity {
    EditText profileFullNameBEn, profileEmailBEn, profilePhoneNumberBEn;
    Button saveChanges, changeProfileIMG, cancelBtn1 ,changeBenPass;
    FirebaseUser user;
    FirebaseAuth fAuth;
    FirebaseFirestore fstore;
    StorageReference storageRefrence;
    ImageView profileImage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ben_edit_profile);

        profileFullNameBEn =findViewById(R.id.benNewName);
        profileEmailBEn =findViewById(R.id.benNewEmail);
        profilePhoneNumberBEn =findViewById(R.id.benNewPhone);
        saveChanges =findViewById(R.id.saveBenChange);
        profileImage =findViewById(R.id.profileImgBen2);
        changeProfileIMG = findViewById(R.id.changeBenImg);
        cancelBtn1 =findViewById(R.id.c1);
        changeBenPass =findViewById(R.id.changeBenPass);

        fAuth = FirebaseAuth.getInstance();
        user = fAuth.getCurrentUser();
        fstore = FirebaseFirestore.getInstance();
        storageRefrence = FirebaseStorage.getInstance().getReference();


        //pring data from mainProfile activity
        Intent data = getIntent();
        final String fullName = data.getStringExtra("fullName");
        final String email = data.getStringExtra("email");
        final String phone = data.getStringExtra("phone");

        profileEmailBEn.setText(email);
        profileFullNameBEn.setText(fullName);
        profilePhoneNumberBEn.setText(phone);


        cancelBtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(benEditProfile.this, BenMainProfile.class));
                finish();

            }
        });


        changeBenPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(benEditProfile.this, benChangePass.class));
                finish();
            }
        });



        //pring data from mainProfile activity
        Intent data1 = getIntent();
        final String fullName1 = data1.getStringExtra("fullName");
        final String email1 = data1.getStringExtra("email");
        final String phone1 = data1.getStringExtra("phone");

        profileEmailBEn.setText(email1);
        profileFullNameBEn.setText(fullName1);
        profilePhoneNumberBEn.setText(phone1);




        saveChanges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //extract what the user entr
                if (profileFullNameBEn.getText().toString().isEmpty()){
                    Toast.makeText(benEditProfile.this, " لا يمكن ترك الإسم فارغًا  ", Toast.LENGTH_LONG).show();
                    return;
                }
                if (profileEmailBEn.getText().toString().isEmpty()){
                    Toast.makeText(benEditProfile.this, " لا يمكن ترك البريد الإلكتروني فارغًا ", Toast.LENGTH_LONG).show();
                    return;
                }
                if (profilePhoneNumberBEn.getText().toString().isEmpty()){
                    Toast.makeText(benEditProfile.this, " لا يمكن ترك رقم الجوال فارغًا ", Toast.LENGTH_LONG).show();
                    return;

                }
                if (profilePhoneNumberBEn.getText().toString().length()!=10){
                    profilePhoneNumberBEn.setError(" يجب أن يتكون رقم الجوال من 10 أرقام ");
                    return;
                }

                if (!profilePhoneNumberBEn.getText().toString().substring(0,2).equals("05")){
                    profilePhoneNumberBEn.setError(" يجب أن يبدأ رقم الجوال بـ 05 ");
                    return;
                }



                //extract the email to change it
                final String email22 = profileEmailBEn.getText().toString();
                user.updateEmail(email22).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        DocumentReference docRef = fstore.collection("beneficiaries").document(user.getUid());
                        //put new data to firebase
                        Map<String,Object> edited = new HashMap<>();
                        edited.put("email", email22);
                        edited.put("userName", profileFullNameBEn.getText().toString());
                        edited.put("phoneNumber", profilePhoneNumberBEn.getText().toString());
                        docRef.update(edited).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {

                                if(profileFullNameBEn.getText().toString().equals(fullName1) && profileEmailBEn.getText().toString().equals(email1) && profilePhoneNumberBEn.getText().toString().equals(phone1)){
                                    startActivity(new Intent(benEditProfile.this, BenMainProfile.class));
                                    finish();
                                }

                                else {
                                    Toast.makeText(benEditProfile.this, " تم تحديث الملف الشخصي بنجاح ", Toast.LENGTH_LONG).show();
                                    startActivity(new Intent(benEditProfile.this, BenMainProfile.class));
                                    finish();}
                            }
                        });

                        if (!email1.equals(email22)){
                            fAuth.getCurrentUser().sendEmailVerification();

                            Toast.makeText(benEditProfile.this, " تم تحديث البريد الإلكتروني بنجاح، يرجى اتباع الرابط المرسل لتفعيله ", Toast.LENGTH_LONG).show();
                        }

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        String exMsg = e.getMessage();
                        if (exMsg.equals("The email address is already in use by another account.")){
                            Toast.makeText(benEditProfile.this, "البريد الإلكتروني موجود مسبقًا  ", Toast.LENGTH_LONG).show();
                        }

                        if (exMsg.equals("This operation is sensitive and requires recent authentication. Log in again before retrying this request.")){
                            Toast.makeText(benEditProfile.this,  "هذه العملية حساسة وتتطلب مصادقة حديثة. قم بتسجيل الدخول مرة أخرى قبل إعادة محاولة هذا الطلب.", Toast.LENGTH_LONG).show();
                        }
                        else {
                            Toast.makeText(benEditProfile.this,e.getMessage(),Toast.LENGTH_LONG).show();
                        }

                    }
                });

            }
        });



        StorageReference profileRef = storageRefrence.child("users/"+fAuth.getCurrentUser().getUid()+"profile.jpg");
        profileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(profileImage);
            }
        });


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
                //profileImage.setImageURI(imageUri);
                //upload to firebase
                uploadImageToFirebase(imageUri);

            }
        }
    }
    private void uploadImageToFirebase(Uri imageUri) {
        //to upload image to firebase
        final StorageReference fileRef = storageRefrence.child("users/"+fAuth.getCurrentUser().getUid()+"profile.jpg");
        fileRef.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Picasso.get().load(uri).into(profileImage);
                    }
                });

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(benEditProfile.this, " فشل تغيير الصورة ", Toast.LENGTH_SHORT).show();
            }
        });




    }

}