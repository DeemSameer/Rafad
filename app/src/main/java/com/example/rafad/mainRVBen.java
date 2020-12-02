package com.example.rafad;




import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class mainRVBen extends AppCompatActivity {
    RecyclerView recyclerView;
    ArrayList<MainModelBen> mainModels;
    mainAdapterBen mainAdapter;


    @Override
    protected void onCreate (Bundle SsavedInstanceState) {
        super.onCreate(SsavedInstanceState);

        setContentView(R.layout.main_recyclerview_ben);
        recyclerView=findViewById(R.id.recycler_view777);

        //create int array
        Integer[] langlogo= {R.drawable.homepagepurple, R.drawable.profileorange, R.drawable.requests,R.drawable.chatlightgreen} ;

        //create string array
        String[] langName = { "الصفحة الرئيسية", "الملف الشخصي", "الطلبات المعلقة" ,"المحادثات"};


        //initialize arraylist
        mainModels = new ArrayList<>();
        for (int i=0; i<langlogo.length; i++){
            MainModelBen model= new MainModelBen(langlogo[i], langName[i]);
            mainModels.add(model);
        }


        //design horizontal layout
        LinearLayoutManager layoutManager= new LinearLayoutManager(
                mainRVBen.this,LinearLayoutManager.HORIZONTAL, false
        );
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        //initialize MainAdapter
        mainAdapter = new mainAdapterBen(mainRVBen.this, mainModels);
        //set mainAdapter to recycler view
        recyclerView.setAdapter(mainAdapter );


    }
}
