package com.example.rafad.ChatJava;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.rafad.R;
import com.example.rafad.benDataModel;
import com.example.rafad.homepageAdmin;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MainChatAllPeople extends AppCompatActivity {

    List<PeopleModel> arrayList=new ArrayList<>();
    PeopleAdapter adapter;
    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    final DatabaseReference ref = database.getReference("Deem");//we can put the path on it like "server/saving-data/fireblog/posts"
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
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot: dataSnapshot.getChildren()){
                //   Iterator<DataSnapshot> dataSnapshotPeople = dataSnapshot.child("People").getChildren().iterator();
                   //while (dataSnapshotPeople.hasNext()){
                    arrayList.add(new PeopleModel("Arob", "String lastMsg", "String time", "String date", "String UID", "String pic"));
                    arrayList.add(new PeopleModel("Nada", "String lastMsg", "String time", "String date", "String UID", "String pic"));

                    // }
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
