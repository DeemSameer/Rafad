package com.example.rafad.ChatJava;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.rafad.R;
import com.example.rafad.chtNotifications.Data;
import com.example.rafad.chtNotifications.MyResponse;
import com.example.rafad.chtNotifications.Sender;
import com.example.rafad.notification.Client;
import com.example.rafad.notification.Token;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MessageActivity extends AppCompatActivity {

    CircleImageView profile_image;
    TextView username;
    ImageButton btn_send;
    EditText text_send;
    static String receiverUID;
    static String receivername;
    StorageReference storageReference;

    List<Chat> arrayList=new ArrayList<>();
    MessageAdapter adapter;
    ListView recyclerViewChat;
    APIService apiService;

    boolean notify = false;




    String fuser= FirebaseAuth.getInstance().getCurrentUser().getUid();
   // DatabaseReference reference;
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

         ////////////////////////////////////////////////
        apiService= Client.getClient("https://fcm.googleapis.com/").create(APIService.class);
        ////////////////////////////////////////////////




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







        recyclerViewChat = (ListView) findViewById(R.id.allChats);
        adapter = new MessageAdapter(MessageActivity.this, arrayList);
        recyclerViewChat.setAdapter(adapter);

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference ref = database.getReference(fuser+"/People/"+receiverUID+"/Messages");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                arrayList.clear();
                adapter.clear();
                String date="";
                for (final DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Log.d(TAG, "MMMMMMMMMMMMMMM " + snapshot.getValue().toString());
                    //Log.d(TAG, "MMMMMMMMMMMMMMM "+snapshot.getValue().toString().substring(0,snapshot.getValue().toString().indexOf("=")-1));

                    if (snapshot.child("fmsg").getValue() != null) {
                        Log.d(TAG, "MMMMMMMMMMMMMMM message " + snapshot.child("fmsg").child("content").getValue().toString());
                        if (date.equals(snapshot.child("fmsg").child("date").getValue().toString()))
                            arrayList.add(new Chat(snapshot.child("fmsg").child("content").getValue().toString(),snapshot.child("fmsg").child("time").getValue().toString(),snapshot.child("fmsg").child("date").getValue().toString(), 0));
                        else {
                            date=snapshot.child("fmsg").child("date").getValue().toString();
                            arrayList.add(new Chat(snapshot.child("fmsg").child("content").getValue().toString(), snapshot.child("fmsg").child("time").getValue().toString(), snapshot.child("fmsg").child("date").getValue().toString(), 21));
                        }
                        adapter.notifyDataSetChanged();
                    } else if (snapshot.child("tmsg").getValue() != null) {
                        Log.d(TAG, "MMMMMMMMMMMMMMM message " + snapshot.child("tmsg").child("content").getValue().toString());
                        if (date.equals(snapshot.child("tmsg").child("date").getValue().toString()))
                        arrayList.add(new Chat(snapshot.child("tmsg").child("content").getValue().toString(),snapshot.child("tmsg").child("time").getValue().toString(),snapshot.child("tmsg").child("date").getValue().toString(), 1));
                        else {
                            date=snapshot.child("tmsg").child("date").getValue().toString();
                            arrayList.add(new Chat(snapshot.child("tmsg").child("content").getValue().toString(), snapshot.child("tmsg").child("time").getValue().toString(), snapshot.child("tmsg").child("date").getValue().toString(), 22));
                        }
                        adapter.notifyDataSetChanged();
                    }

                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



        ////////////////////////////////////////////////////////////////////////////////////////////////
        final String msg= text_send.getText().toString();
        DatabaseReference reference=FirebaseDatabase.getInstance().getReference(fuser);/////هل هذا الرفرنس صحيح ؟؟
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                PeopleModel peopleModel=dataSnapshot.getValue(PeopleModel.class);
                if (notify) {
                    sendNotification(receiver, peopleModel.getName(), msg);
                }
               notify=false;
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



        ////////////////////////////////////////////////////////////////////////////////////////////////





        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                notify=true;
                String msg=text_send.getText().toString();
                if(!msg.equals("")){
                    sendMessage(fuser, receiverUID, msg);
                }else{
                    Toast.makeText(MessageActivity.this,  "لا يمكنك إسال رسالة فارغة " , Toast.LENGTH_LONG).show();
                }
                text_send.setText("");
            }
        });


    }
    ////////////////////////////////////////////////////////////////////////////////////////////////
    private void sendNotification (String receiver, final String username, final String message){

        DatabaseReference tokens= FirebaseDatabase.getInstance().getReference();
        Query query=tokens.orderByKey().equalTo(receiver);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot: dataSnapshot.getChildren()){
                   Token token = snapshot.getValue(Token.class);
                    Data data= new Data(fuser, R.mipmap.ic_launcher, username+": "+message,"New Message",
                            userid);

                    Sender sender = new Sender(data, token.getToken());

                    apiService.sendNotification(sender)
                            .enqueue(new Callback<MyResponse>() {
                                @Override
                                public void onResponse(Call<MyResponse> call, Response<MyResponse> response) {
                                    if (response.code()==200){
                                        if (response.body().success!=1){
                                            Toast.makeText(MessageActivity.this, "فشل!", Toast.LENGTH_SHORT ).show();

                                        }
                                    }
                                }

                                @Override
                                public void onFailure(Call<MyResponse> call, Throwable t) {

                                }
                            });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////


    private void sendMessage(String sender, String receiver, String message){
        //Get time and date //
        //date
        Calendar calendar=Calendar.getInstance();
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("dd/MM/yyyy");
        String date=simpleDateFormat.format(calendar.getTime());
        //end date
        //time
        Calendar calendar1=Calendar.getInstance();
        SimpleDateFormat simpleDateFormat1=new SimpleDateFormat("h:mm a");
        String time=simpleDateFormat1.format(calendar1.getTime());
        //end time
        //End getting time and date //


        DatabaseReference reference= FirebaseDatabase.getInstance().getReference();
        HashMap<String,Object> hashMap=new HashMap<>();
        hashMap.put("fmsg", new Message(message,date,time));
        reference.child(sender).child("People").child(receiver).child("Messages").push().setValue(hashMap);
        //***************************************************************************//
        DatabaseReference reference2= FirebaseDatabase.getInstance().getReference();
        HashMap<String,Object> hashMap2=new HashMap<>();
        hashMap2.put("tmsg", new Message(message,date,time));
        reference2.child(receiver).child("People").child(sender).child("Messages").push().setValue(hashMap2);

    }




public static void callMe(String UID,String name){
     receiverUID = UID;
    receivername=name;
}

    @Override
    protected void onStart() {
        super.onStart();
        //Retrieve old data + displaying them on the chat

    }
}