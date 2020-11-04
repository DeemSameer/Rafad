package com.example.rafad.ChatJava;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.rafad.R;

import java.util.ArrayList;

public class MainChatActivity extends AppCompatActivity {

    private SwipeMenuListView listView;
    private ArrayList<ListChat> dataArrayList;
    private ChatAdapter listAdapter;
    private ListChat data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = (SwipeMenuListView) findViewById(R.id.listview);
        dataArrayList = new ArrayList<>();


        dataArrayList.add(data = new ListChat("Jenni", "Where are you boy?",R.drawable.girl));
        dataArrayList.add(data = new ListChat("Christa", "I am here dude",R.drawable.girl2));
        dataArrayList.add(data = new ListChat("Lopez", "Tonight at 9pm?",R.drawable.girl3));
        dataArrayList.add(data = new ListChat("Jesse", "Girls night???",R.drawable.girl));
        dataArrayList.add(data = new ListChat("Yokome", "Gonna packup tommorrow",R.drawable.girl2));
        dataArrayList.add(data = new ListChat("Shelly", "Dinner outside? I am not cooking!",R.drawable.girl3));
        dataArrayList.add(data = new ListChat("Jane", "I am coming. Text me right away!",R.drawable.girl));

        listAdapter = new ChatAdapter(this, dataArrayList);
        listView.setAdapter(listAdapter);

        listView.setMenuCreator(creator);

        listView.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                switch (index) {
                    case 0:

                        Toast.makeText(MainChatActivity.this, "Deleted!", Toast.LENGTH_SHORT).show();
                        dataArrayList.remove(position);
                        listAdapter.notifyDataSetChanged();

                        break;
                    case 1:
                        break;
                }
                return false;
            }
        });

    }


    SwipeMenuCreator creator = new SwipeMenuCreator() {

        @Override
        public void create(SwipeMenu menu) {
            // create "delete" item
            SwipeMenuItem deleteItem = new SwipeMenuItem(
                    getApplicationContext());
            // set item background
            deleteItem.setBackground(new ColorDrawable(Color.parseColor("#F45557")));
            // set item width
            deleteItem.setWidth(150);
            deleteItem.setTitle("x");
            deleteItem.setTitleColor(Color.WHITE);
            deleteItem.setTitleSize(15);
            // add to menu
            menu.addMenuItem(deleteItem);
        }
    };
}
