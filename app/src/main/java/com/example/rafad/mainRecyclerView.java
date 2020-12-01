package com.example.rafad;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class mainRecyclerView extends AppCompatActivity {
    RecyclerView recyclerView;
    ArrayList<MAinModel> mainModels;
    MainAdapter mainAdapter;


    @Override
    protected void onCreate (Bundle SsavedInstanceState) {
        super.onCreate(SsavedInstanceState);

        setContentView(R.layout.mainrecyclerview);
        recyclerView=findViewById(R.id.recycler_view);

        //create int array
        Integer[] langlogo= {R.drawable.homepagepurple, R.drawable.profileorange, R.drawable.requests,
                                R.drawable.addorange, R.drawable.chatlightgreen} ;

        //create string array
        String[] langName = { "الصفحة الرئيسية", "الملف الشخصي", "الطلبات المعلقة","إضافة صورة" ,"المحادثات"};


        //initialize arraylist
        mainModels = new ArrayList<>();
        for (int i=0; i<langlogo.length; i++){
            MAinModel model= new MAinModel(langlogo[i], langName[i]);
            mainModels.add(model);
        }


        //design horizontal layout
        LinearLayoutManager layoutManager= new LinearLayoutManager(
                mainRecyclerView.this,LinearLayoutManager.HORIZONTAL, false
        );
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        //initialize MainAdapter
        mainAdapter = new MainAdapter(mainRecyclerView.this, mainModels);
        //set mainAdapter to recycler view
        recyclerView.setAdapter(mainAdapter );


    }
}
