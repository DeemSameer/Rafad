package com.example.rafad;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class mainRVAdmin extends AppCompatActivity {
    RecyclerView recyclerView;
    ArrayList<MainModelAdmin> mainModels;
    mainAdapterAdmin mainAdapter;


    @Override
    protected void onCreate (Bundle SsavedInstanceState) {
        super.onCreate(SsavedInstanceState);

        setContentView(R.layout.main_recycler_view_admin);
        recyclerView=findViewById(R.id.recycler_view_admin);

        //create int array
        Integer[] langlogo= {R.drawable.managerequests, R.drawable.benblocked, R.drawable.adminprofilecard,R.drawable.logoutcard} ;

        //create string array
        String[] langName = { "إدارة حسابات المستفيدين المعلقة", "حسابات المستفيدين المبلغ عنها", "الملف الشخصي","تسجيل الخروج"};


        //initialize arraylist
        mainModels = new ArrayList<MainModelAdmin>();
        for (int i=0; i<langlogo.length; i++){
            MainModelAdmin model= new MainModelAdmin(langlogo[i], langName[i]);
            mainModels.add(model);
        }


        //design horizontal layout
        LinearLayoutManager layoutManager= new LinearLayoutManager(
                mainRVAdmin.this,LinearLayoutManager.HORIZONTAL, false
        );
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        //initialize MainAdapter
        mainAdapter = new mainAdapterAdmin(mainRVAdmin.this, mainModels);
        //set mainAdapter to recycler view
        recyclerView.setAdapter(mainAdapter );


    }
}

