package com.example.rafad.ChatJava;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.rafad.R;
import com.example.rafad.homepageDonator;
import com.example.rafad.login;
import com.firebase.ui.auth.data.model.User;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class MessageActivity extends AppCompatActivity {

    CircleImageView profile_image;
    TextView username;
    ImageButton btn_send;
    EditText text_send;
    static String receiverUID;
    static String receivername;
    StorageReference storageReference;



    FirebaseUser fuser;
    DatabaseReference reference;
    Intent intent;
    private String TAG;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.message_activity);
        storageReference = FirebaseStorage.getInstance().getReference();
        Toolbar toolbar = findViewById(R.id.toolbar);
         setSupportActionBar(toolbar);
         getSupportActionBar().setTitle("");
         getSupportActionBar().setDisplayHomeAsUpEnabled(true);
         toolbar.setNavigationOnClickListener(new View.OnClickListener(){
             @Override
             public void onClick(View view){
                  finish();
             }
         });

         profile_image=findViewById(R.id.profile_image);
         username=findViewById(R.id.username);
         btn_send=findViewById(R.id.btn_send);
         text_send=findViewById(R.id.text_send);
        Log.d(TAG, "receiverUID  "+receiverUID);

        StorageReference profileRef = storageReference.child("users/" + receiverUID + "profile.jpg");
        Log.d(TAG, "before12 People Adapter" + profileRef);
        profileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(profile_image);
                    Log.d(TAG, "interrrrrr Message Activity");

            }
        });

        username.setText(receivername);


        fuser= FirebaseAuth.getInstance().getCurrentUser();

         btn_send.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 String msg=text_send.getText().toString();
                  if(!msg.equals("")){
                      sendMessage(fuser.getUid(), receiverUID, msg);
                  }else{
                      Toast.makeText(MessageActivity.this,  "لا يمكنك إسال رسالة فارغة " , Toast.LENGTH_LONG).show();
                  }
                  text_send.setText("");
             }
         });




    }

    private void sendMessage(String sender, String receiver, String message){

        DatabaseReference reference= FirebaseDatabase.getInstance().getReference();
        HashMap<String,Object> hashMap=new HashMap<>();
        hashMap.put("fmsg", new Message(message,"1212","11:00"));
        reference.child(sender).child("People").child(receiver).child("Messages").push().setValue(hashMap);
        //***************************************************************************//
        DatabaseReference reference2= FirebaseDatabase.getInstance().getReference();
        HashMap<String,Object> hashMap2=new HashMap<>();
        hashMap2.put("tmsg", new Message(message,"1212","11:00"));
        reference2.child(receiver).child("People").child(sender).child("Messages").push().setValue(hashMap2);

    }




public static void callMe(String UID,String name){
     receiverUID = UID;
    receivername=name;
}

}