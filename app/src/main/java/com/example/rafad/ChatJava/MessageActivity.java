package com.example.rafad.ChatJava;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.rafad.R;
import com.example.rafad.login;
import com.example.rafad.report.pop_up_reported;
import com.example.rafad.report.pop_up_typeOfReport;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.core.Repo;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.onesignal.OneSignal;
import com.squareup.picasso.Picasso;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import de.hdodenhof.circleimageview.CircleImageView;


public class MessageActivity extends AppCompatActivity {

    CircleImageView profile_image;
    TextView username;
    ImageButton btn_send;
    EditText text_send;
    static String receiverUID;
    static String receivername;
    StorageReference storageReference;
    static String senderMail;
    FirebaseFirestore fStore;

    List<Chat> arrayList=new ArrayList<>();
    MessageAdapter adapter;
    ListView recyclerViewChat;
    Button report;





    String fuser= FirebaseAuth.getInstance().getCurrentUser().getUid();
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
                final FirebaseDatabase database = FirebaseDatabase.getInstance();
                final DatabaseReference ref = database.getReference(fuser+"/People/"+ receiverUID+"/Messages/unread");
                ref.setValue("0");
                Intent i = new Intent(MessageActivity.this, MainChatAllPeople.class);
                startActivity(i);
                finish();

            }
        });

        //make the report appear just for the donator
        fStore= FirebaseFirestore.getInstance();
        report = (Button) findViewById(R.id.report);
        report.setVisibility(View.VISIBLE);

        if (login.getType() != null)
            if (!login.getType().equals("beneficiaries"))
                report.setVisibility(View.GONE);

            //Add the functionality to the button
            report.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View view) {
                    Log.d(TAG, "report clicked  ");
                    DocumentReference documentrefReference = fStore.collection("Reports").document(receiverUID);
                    //Check if he/she has been reported from the same person?
                    FirebaseFirestore db=FirebaseFirestore.getInstance();
                    CollectionReference beneficiaries = db.collection("Reports");
                    final DocumentReference Reports = beneficiaries.document(receiverUID);
                    Reports.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if (task.isSuccessful()) {
                                DocumentSnapshot document = task.getResult();
                                if (document.exists()) {
                                    List<String> donatorsID = (List<String>) document.get("donatorsID");
                                    boolean reported=false;
                                    if (donatorsID!=null){
                                        for (int i=0;i<donatorsID.size();i++)
                                            if (donatorsID.get(i).equals(fuser)) {//he/she already report to the admin
                                               //Show message you already report
                                                pop_up_reported poped=new pop_up_reported();
                                                poped.showPopupWindow(view);
                                               reported=true;
                                            }
                                    }
                                    if (!reported) {//hasn't been reported
                                        //Show message and let him write 200 character about why he/she want to report to the admin
                                        pop_up_typeOfReport pop=new pop_up_typeOfReport();
                                        pop.showPopupWindow(view);
                                    }
                                }
                            }
                        }
                    });
                    //end checking


                    //store data
                    Map<String,Object> user= new HashMap<>();
                   // user.put("phoneNumber", Phone);


                    // user.put("items",Arrays.asList("array for items"));
                    //check the add if it's success or not
                    documentrefReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                      //      Log.d(TAG,"User id created for"+userID);
                        }
                    });

                }
            });

        //end hidden



        profile_image=findViewById(R.id.profile_image);
        username=findViewById(R.id.username);
        btn_send=findViewById(R.id.btn_send);
        text_send=findViewById(R.id.text_send);


        Log.d(TAG, "receiverUID  "+receiverUID);

        try {

            StorageReference profileRef = storageReference.child("users/" + receiverUID + "profile.jpg");
            Log.d(TAG, "before12 People Adapter" + profileRef);
            profileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    Picasso.get().load(uri).into(profile_image);
                    Log.d(TAG, "interrrrrr Message Activity");

                }
            });
        }catch (Exception e){

        }

        username.setText(receivername);


        //Notification
        OneSignal.setLogLevel(OneSignal.LOG_LEVEL.VERBOSE, OneSignal.LOG_LEVEL.NONE);

        // OneSignal Initialization
        OneSignal.startInit(this)
                .inFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification)
                .unsubscribeWhenNotificationsAreDisabled(true)
                .init();

        String LoggedIn_User_Email =FirebaseAuth.getInstance().getCurrentUser().getEmail();
        OneSignal.sendTag("User_ID",LoggedIn_User_Email);
        //End notify




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



        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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

    private void sendMessage(String sender, String receiver, final String message){
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
        //*************************//
        DatabaseReference reference2= FirebaseDatabase.getInstance().getReference();
        HashMap<String,Object> hashMap2=new HashMap<>();
        hashMap2.put("tmsg", new Message(message,date,time));
        reference2.child(receiver).child("People").child(sender).child("Messages").push().setValue(hashMap2);
        //*********//
         FirebaseDatabase database = FirebaseDatabase.getInstance();

         //0
         DatabaseReference refmsg0 = database.getReference(receiverUID+"/People/"+fuser+"/Messages/content");
        refmsg0.setValue(message);

        DatabaseReference reft0 = database.getReference(receiverUID+"/People/"+fuser+"/Messages/time");
        reft0.setValue(time);

        DatabaseReference refd0 = database.getReference(receiverUID+"/People/"+fuser+"/Messages/date");
        refd0.setValue(date);


        //1
        DatabaseReference refmsg1 = database.getReference(fuser+"/People/"+receiverUID+"/Messages/content");
        refmsg1.setValue(message);

        DatabaseReference reft1 = database.getReference(fuser+"/People/"+receiverUID+"/Messages/time");
        reft1.setValue(time);

        DatabaseReference refd1 = database.getReference(fuser+"/People/"+receiverUID+"/Messages/date");
        refd1.setValue(date);

        //***//
        sendNotification(message,senderMail);//Add the name of the one who send it

        updateUnread();

    }




    public static void callMe(String UID,String name, String sm){
        receiverUID = UID;
        receivername=name;
        senderMail=sm;
    }

    @Override
    protected void onStart() {
        super.onStart();
        //Retrieve old data + displaying them on the chat

    }

    public void updateUnread(){
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference ref = database.getReference(receiverUID+"/People/"+fuser+"/Messages/unread");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            int unread;
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue()!=null){
                    Log.d(TAG, "dataSnapshot::::  "+dataSnapshot.getValue().toString());
                    unread=Integer. parseInt(dataSnapshot.getValue().toString());
                    unread+=1;
                    ref.setValue(""+unread+"");}
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // ...
            }
        });
    }


    //enter the chat and leave - Delete notification
    @Override
    protected void onStop() {
        super.onStop();
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference ref = database.getReference(fuser+"/People/"+ receiverUID+"/Messages/unread");
        ref.setValue("0");
    }


    private void sendNotification(final String message, final String senderMail)
    {
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                int SDK_INT = android.os.Build.VERSION.SDK_INT;
                if (SDK_INT > 8) {
                    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                            .permitAll().build();
                    StrictMode.setThreadPolicy(policy);
                    String send_email;

                    //This is a Simple Logic to Send Notification different Device Programmatically....
                    String LoggedIn_User_Email =FirebaseAuth.getInstance().getCurrentUser().getEmail();
                    OneSignal.sendTag("User_ID",FirebaseAuth.getInstance().getCurrentUser().getEmail());//sender email
                    send_email=senderMail;//reciever email
                    /*
                    if (MainActivity.LoggedIn_User_Email.equals("user1@gmail.com")) {
                        send_email = "user2@gmail.com";
                    } else {
                        send_email = "user1@gmail.com";
                    }*/

                    try {
                        String jsonResponse;

                        URL url = new URL("https://onesignal.com/api/v1/notifications");
                        HttpURLConnection con = (HttpURLConnection) url.openConnection();
                        con.setUseCaches(false);
                        con.setDoOutput(true);
                        con.setDoInput(true);

                        con.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
                        con.setRequestProperty("Authorization", "Basic NTVjOWIxNmYtYjI0YS00NjU0LWE1YmEtYjM5YTM2OWQxZjIx");
                        con.setRequestMethod("POST");

                        String strJsonBody = "{"
                                + "\"app_id\": \"c12cdbbf-7bd8-4c4f-b5ba-df37e6cc2d36\","

                                + "\"filters\": [{\"field\": \"tag\", \"key\": \"User_ID\", \"relation\": \"=\", \"value\": \"" + send_email + "\"}],"

                                + "\"data\": {\"foo\": \"bar\"},"
                                + "\"contents\": {\"en\": \""+message+"\"}"
                                + "}";


                        System.out.println("strJsonBody:\n" + strJsonBody);

                        byte[] sendBytes = strJsonBody.getBytes("UTF-8");
                        con.setFixedLengthStreamingMode(sendBytes.length);

                        OutputStream outputStream = con.getOutputStream();
                        outputStream.write(sendBytes);

                        int httpResponse = con.getResponseCode();
                        System.out.println("httpResponse: " + httpResponse);

                        if (httpResponse >= HttpURLConnection.HTTP_OK
                                && httpResponse < HttpURLConnection.HTTP_BAD_REQUEST) {
                            Scanner scanner = new Scanner(con.getInputStream(), "UTF-8");
                            jsonResponse = scanner.useDelimiter("\\A").hasNext() ? scanner.next() : "";
                            scanner.close();
                        } else {
                            Scanner scanner = new Scanner(con.getErrorStream(), "UTF-8");
                            jsonResponse = scanner.useDelimiter("\\A").hasNext() ? scanner.next() : "";
                            scanner.close();
                        }
                        System.out.println("jsonResponse:\n" + jsonResponse);

                    } catch (Throwable t) {
                        t.printStackTrace();
                    }
                }
            }
        });
    }

}