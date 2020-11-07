package com.example.rafad.ChatJava;

import android.app.ActionBar;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.rafad.BenMainProfile;
import com.example.rafad.R;
import com.example.rafad.homePage;
import com.example.rafad.homepageDonator;
import com.example.rafad.login;
import com.example.rafad.mainProfile;
import com.example.rafad.post3;
import com.example.rafad.requests;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Scanner;

public class MainChatAllPeople extends AppCompatActivity {
    static int Position;
    Button toHome, toPost, tolist, toProfile;
    private static final String TAG=null ;
    List<PeopleModel> arrayList=new ArrayList<>();
    PeopleAdapter adapter;
    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    String UserId= FirebaseAuth.getInstance().getCurrentUser().getUid();
    final DatabaseReference ref = database.getReference(UserId+"/People");//we can put the path on it like "server/saving-data/fireblog/posts"
    ListView recyclerViewPeople;
    TextView empty;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //PUT CASSES (Donator, Ben)
        if (login.getType().equals("beneficiaries")) {
            setContentView(R.layout.activity_main_chat_all_people);
            recyclerViewPeople = (ListView) findViewById(R.id.allPeople);
            adapter = new PeopleAdapter(MainChatAllPeople.this, arrayList);
            recyclerViewPeople.setAdapter(adapter);
            toHome = findViewById(R.id.bHome2);
            toPost = findViewById(R.id.chatPostItem);
            tolist = findViewById(R.id.button16);
            toProfile = findViewById(R.id.FromChatToProfileButton);
            empty= findViewById(R.id.chatText3);

            toHome.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(MainChatAllPeople.this, homepageDonator.class));
                    finish();
                }
            });
            toPost.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(MainChatAllPeople.this, post3.class));
                    finish();
                }
            });
            tolist.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(MainChatAllPeople.this, requests.class));
                    finish();
                }
            });
            toProfile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(MainChatAllPeople.this, mainProfile.class));
                    finish();
                }
            });

        } else {
            setContentView(R.layout.activity_main_chat_all_people2);
            recyclerViewPeople = (ListView) findViewById(R.id.allPeople);
            adapter = new PeopleAdapter(MainChatAllPeople.this, arrayList);
            recyclerViewPeople.setAdapter(adapter);
            toHome = findViewById(R.id.bHome4);
            toPost = findViewById(R.id.chatPostItem);
            toProfile = findViewById(R.id.fromChatToProfileButton2);
             empty= findViewById(R.id.chatText3);
            tolist = findViewById(R.id.button18);


            toHome.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(MainChatAllPeople.this, homePage.class));
                    finish();
                }
            });

            tolist.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                   // startActivity(new Intent(MainChatAllPeople.this, requests.class));///CHHHANGGGE
                 //   finish();
                }
            });
            toProfile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(MainChatAllPeople.this, BenMainProfile.class));
                    finish();
                }
            });
        }


        //Listener
            ref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull final DataSnapshot dataSnapshot) {

                    adapter.clear();//To clear data and retrive again -- I did not test it yet -_-
                    for (final DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        final String key = snapshot.getKey();
                        Log.d(TAG, key + " Hello from the another world");
                        //Retrieve the name of that ID,
                        FirebaseFirestore db = FirebaseFirestore.getInstance();
                        //Collection path SHOULD CHANGE
                        if (key != null)
                            db.collection(login.getType()).document(key).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                    DocumentSnapshot document = task.getResult();
                                    if (document.exists()) {
                                        final String name = document.get("userName").toString();
                                        final String UID = document.getId();
                                        Log.d(TAG, name + " nameUser");


                                        //Start retrieve from real DB LastMsg + time


                                        final FirebaseDatabase database = FirebaseDatabase.getInstance();
                                        final DatabaseReference ref = database.getReference(UserId + "/People/" + key + "/Messages");
                                        ref.addValueEventListener(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                String msg = "";
                                                String t = "";
                                                String d = "";
                                                String unread = "";




                                                for (final DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                                    Log.d(TAG, "MMMMMMMMMMMMMMM " + snapshot.getValue().toString());


                                                    if (snapshot.child("fmsg").getValue() != null) {
                                                        Log.d(TAG, "MMMMMMMMMMMMMMM message " + snapshot.child("fmsg").child("content").getValue().toString());
                                                        msg = snapshot.child("fmsg").child("content").getValue().toString();
                                                        t = snapshot.child("fmsg").child("time").getValue().toString();
                                                        d = snapshot.child("fmsg").child("date").getValue().toString();
                                                        Calendar calendar = Calendar.getInstance();
                                                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                                                        String Today = simpleDateFormat.format(calendar.getTime());
                                                        if (Today.equals(d))
                                                            d = "اليوم";
                                                        adapter.notifyDataSetChanged();
                                                    } else if (snapshot.child("tmsg").getValue() != null) {
                                                        Log.d(TAG, "MMMMMMMMMMMMMMM message " + snapshot.child("tmsg").child("content").getValue().toString());
                                                        msg = snapshot.child("tmsg").child("content").getValue().toString();
                                                        t = snapshot.child("tmsg").child("time").getValue().toString();
                                                        d = snapshot.child("tmsg").child("date").getValue().toString();

                                                        Calendar calendar = Calendar.getInstance();

                                                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                                                        String Today = simpleDateFormat.format(calendar.getTime());
                                                        if (Today.equals(d))
                                                            d = "اليوم";

                                                        adapter.notifyDataSetChanged();
                                                    }

                                                }
                                                //setting adapter array
                                                //unreadded messages
                                                final FirebaseDatabase database = FirebaseDatabase.getInstance();
                                                final DatabaseReference ref = database.getReference(UserId + "/People/" + key + "/Messages/unread");
                                                final String finalMsg = msg;
                                                final String finalT = t;
                                                final String finalD = d;
                                                //adapter.clear();
                                                ref.addListenerForSingleValueEvent(new ValueEventListener() {
                                                    String unread;

                                                    @Override
                                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                                        if (dataSnapshot.getValue() != null) {
                                                            Log.d(TAG, "dataSnapshot::::  " + dataSnapshot.getValue().toString());
                                                            unread = dataSnapshot.getValue().toString();
                                                            Log.d(TAG, "unreadunread " + unread);
                                                            Log.d(TAG, "unreadunread 2 " + unread);

                                                        }
                                                        arrayList.add(new PeopleModel(name, finalMsg, finalT, finalD, UID, unread));
                                                        adapter.notifyDataSetChanged();
                                                    }

                                                    @Override
                                                    public void onCancelled(DatabaseError databaseError) {
                                                        // ...
                                                    }
                                                });

                                                //end

                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError databaseError) {

                                            }
                                        });


                                        //
                                        String lastMsg;
                                        String date;
                                        String time;
                                        Log.d(TAG, snapshot.child("Messages").child("lastMessage").child("content").getValue() + " VALUVALULOOL" + name);
                                /*if (snapshot.child("Messages").child("lastMessage").child("content").getValue() != null) {
                                    lastMsg = snapshot.child("Messages").child("lastMessage").child("content").getValue().toString();
                                    date = snapshot.child("Messages").child("lastMessage").child("date").getValue().toString();
                                    time = snapshot.child("Messages").child("lastMessage").child("time").getValue().toString();
                                } else {
                                    lastMsg = " ";
                                    date = " ";
                                    time = " ";
                                }*/
                                        //end retrieve


                                    }//End Existing
                                }
                            });
                        //End retrieving db.collection("donators").document(key).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {

                    }
                    adapter.notifyDataSetChanged();
                    //OnclickListener


                }


                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });        //End Listener

        if (arrayList.size() == 0) {
            //Toast.makeText(requests.this, "This is my Toast message!",
            //  Toast.LENGTH_LONG).show();
            empty.setText("لا يوجد شخص للمحادثة");
        } else {
            empty.setText("");
        }
        }//end class


    @Override
    protected void onStart() {
        super.onStart();
        adapter.clear();//To clear data and retrive again -- I did not test it yet -_-
        Log.d(TAG, "arrayaaaaaaaaa "+arrayList.size() );

    }



    /*
                              recyclerViewPeople.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                        Position =i;
                                        Log.d(TAG, "Position  "+ i);
                                        Intent intent = new Intent(MainChatAllPeople.this, MessageActivity.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                });
     */

}
