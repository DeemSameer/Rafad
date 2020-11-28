package com.example.rafad;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rafad.ChatJava.MainChatAllPeople;

import java.util.ArrayList;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder> {
    ArrayList<MAinModel> mainModels;
    Context context;
    public MainAdapter(Context context, ArrayList<MAinModel> mainModels){
        this.context=context;
        this.mainModels=mainModels;

    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //create view
        View view= LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_item,parent , false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        // set pic to image view
         holder.imageView.setImageResource(mainModels.get(position).getLangLogo());
         //set name to text view
        holder.textView.setText(mainModels.get(position).getLangName());

        //set onClick listener
         holder.linearLayout.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 //create intent
                 if(mainModels.get(position).getLangName().equals("الصفحة الرئيسية")){
                     Intent intent = new Intent(context, homepageDonator.class);
                     context.startActivity(intent);
                 }
                 else if (mainModels.get(position).getLangName().equals("الملف الشخصي")){
                     Intent intent = new Intent(context, mainProfile.class);
                     context.startActivity(intent);
                 }
                 else if (mainModels.get(position).getLangName().equals("االطلبات المعلقة")){
                     Intent intent = new Intent(context, don_3view.class);
                     context.startActivity(intent);
                 }
                 else if (mainModels.get(position).getLangName().equals("إضافة صورة")){
                     Intent intent = new Intent(context, post3.class);
                     context.startActivity(intent);
                 }
                 else if (mainModels.get(position).getLangName().equals("المحادثات")){
                     Intent intent = new Intent(context, MainChatAllPeople.class);
                     context.startActivity(intent);
                 }
             }
         });

    }

    @Override
    public int getItemCount() {
        return mainModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        //initialize variable
        ImageView imageView;
        TextView textView;
        LinearLayout linearLayout;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            //assign variable
            imageView = itemView.findViewById(R.id.image_view222);
            textView = itemView.findViewById(R.id.text_view222);
            linearLayout = itemView.findViewById(R.id.linear_layout);


        }
    }
}
