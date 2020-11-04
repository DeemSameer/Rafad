package com.example.rafad.ChatJava;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.rafad.R;
import com.example.rafad.benDataModel;
import com.example.rafad.homepageAdmin;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MainChatAllPeople extends AppCompatActivity {

    private static final String TAG=null ;
    List<PeopleModel> arrayList=new ArrayList<>();
    PeopleAdapter adapter;
    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    final DatabaseReference ref = database.getReference("Deem/People");//we can put the path on it like "server/saving-data/fireblog/posts"
    ListView recyclerViewPeople;
    ArrayAdapter arrayAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_chat_all_people);

        recyclerViewPeople =(ListView)findViewById(R.id.allPeople);
        adapter=new PeopleAdapter(MainChatAllPeople.this, arrayList);
        recyclerViewPeople.setAdapter(adapter);


        //Listener
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull final DataSnapshot dataSnapshot) {
                for (final DataSnapshot snapshot: dataSnapshot.getChildren()){
                    final String key = snapshot.getKey();
                    Log.d(TAG, key+" Hello from the another world");
                    //Retrieve the name of that ID,
                    FirebaseFirestore db=FirebaseFirestore.getInstance();
                    db.collection("donators").document(key).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                String name = document.get("userName").toString();
                                String UID = document.getId();
                                Log.d(TAG, name+" nameUser");
                                Log.d(TAG, UID+" UIDUSER");
                                /// End retrieve person info

                                //Start retrieve from real DB LastMsg + time
                                Log.d(TAG, snapshot.child("lastMessage").child("content").getValue() +" VALUVALULOOL" +name);
                                String lastMsg=snapshot.child("lastMessage").child("content").getValue().toString();
                                String date=snapshot.child("lastMessage").child("date").getValue().toString();
                                String time=snapshot.child("lastMessage").child("time").getValue().toString();
                                //end retrieve


                                //setting adapter array
                                arrayList.add(new PeopleModel(name, lastMsg, time, date, UID, "String pic"));
                                adapter.notifyDataSetChanged();

                            }//End Existing
                        }
                    });
                    //End retrieving db.collection("donators").document(key).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {

                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                arrayAdapter.notifyDataSetChanged();
            }
        });
        //End Listener

    }

    private void getUsers(){

    }

}
