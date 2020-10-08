package com.example.rafad;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.Log;
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
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

public class donProfile extends AppCompatActivity {

    private static final int GALLERY_INTENT_CODE=1023;
    public static final String TAG = "TAG";
    TextView fullname , email , phone , pass;
    Button changeProfileIMG, cancell, resetPassLocal, resendCode, saveBtn, changePassBtn;
    FirebaseAuth fAuth;
    FirebaseFirestore fstore;
    String userID;
    FirebaseUser user;
    ImageView profileImage;
    StorageReference storageRefrence;
    EditText profileFullName , profileEmail, profilePhoneNumber;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_don_profile);

        profileImage= findViewById(R.id.profileImg);
        changeProfileIMG=findViewById(R.id.change);
        cancell=findViewById(R.id.cancel);
        saveBtn = findViewById(R.id.save);
        changePassBtn = findViewById(R.id.changepass);


        profileFullName =findViewById(R.id.newName);
        profileEmail = findViewById(R.id.newEmail);
        profilePhoneNumber = findViewById(R.id.newPhone);

        fAuth = FirebaseAuth.getInstance();
        fstore = FirebaseFirestore.getInstance();
        storageRefrence = FirebaseStorage.getInstance().getReference();
        user = fAuth.getCurrentUser();

        changePassBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), changePasswordDon.class));
                finish();
            }
        });


        //pring data from mainProfile activity
        Intent data = getIntent();
        final String fullName = data.getStringExtra("fullName");
        final String email = data.getStringExtra("email");
        final String phone = data.getStringExtra("phone");

        profileEmail.setText(email);
        profileFullName.setText(fullName);
        profilePhoneNumber.setText(phone);

        //to check if passed or not
        Log.d(TAG, "onCreate: " + fullName + " " + email + " " + phone);


        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //extract what the user entr
                //if (profileFullName.getText().toString().isEmpty() || profileEmail.getText().toString().isEmpty() || profilePhoneNumber.getText().toString().isEmpty()){
                if (profileFullName.getText().toString().isEmpty()){
                    Toast.makeText(donProfile.this, " لا يمكن ترك الإسم فارغًا  ", Toast.LENGTH_LONG).show();
                    return;
                }
                if (profileEmail.getText().toString().isEmpty()){
                    Toast.makeText(donProfile.this, " لا يمكن ترك البريد الإلكتروني فارغًا ", Toast.LENGTH_LONG).show();
                    return;
                }
                if (profilePhoneNumber.getText().toString().isEmpty()){
                    Toast.makeText(donProfile.this, " لا يمكن ترك رقم الجوال فارغًا ", Toast.LENGTH_LONG).show();
                    return;

                }
                if (profilePhoneNumber.getText().toString().length()!=10){
                    profilePhoneNumber.setError(" يجب أن يتكون رقم الجوال من 10 أرقام ");
                    return;
                }

                if (!profilePhoneNumber.getText().toString().substring(0,2).equals("05")){
                    profilePhoneNumber.setError(" يجب أن يبدأ رقم الجوال بـ 05 ");
                    return;
                }


                //}

                //extract the email to change it
                final String email2 = profileEmail.getText().toString();
                user.updateEmail(email2).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        DocumentReference docRef = fstore.collection("donators").document(user.getUid());
                       //put new data to firebase
                        Map<String,Object> edited = new HashMap<>();
                        edited.put("email", email2);
                        edited.put("userName", profileFullName.getText().toString());
                        edited.put("phoneNumber", profilePhoneNumber.getText().toString());
                        docRef.update(edited).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {

                                if(profileFullName.getText().toString().equals(fullName) && profileEmail.getText().toString().equals(email) && profilePhoneNumber.getText().toString().equals(phone)){
                                    startActivity(new Intent(getApplicationContext(), mainProfile.class));
                                    finish();
                                }

                                else {
                                    Toast.makeText(donProfile.this, " تم تحديث الملف الشخصي بنجاح ", Toast.LENGTH_LONG).show();
                                startActivity(new Intent(getApplicationContext(), mainProfile.class));
                                finish();}
                            }
                        });

                        if (!email.equals(email2)){
                            fAuth.getCurrentUser().sendEmailVerification();

                            Toast.makeText(donProfile.this, " تم تحديث البريد الإلكتروني بنجاح، يرجى اتباع الرابط المرسل لتفعيله ", Toast.LENGTH_LONG).show();
                        }

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        String exMsg = e.getMessage();
                        if (exMsg.equals("The email address is already in use by another account.")){
                            Toast.makeText(donProfile.this, "البريد الإلكتروني موجود مسبقًا  ", Toast.LENGTH_LONG).show();
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




        cancell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(donProfile.this, mainProfile.class));
                finish();

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
                Toast.makeText(donProfile.this, " فشل تغيير الصورة ", Toast.LENGTH_SHORT).show();
            }
        });




    }

    }


