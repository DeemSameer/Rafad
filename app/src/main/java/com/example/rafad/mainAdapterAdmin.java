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

import com.example.rafad.Block.blockBen;

import java.util.ArrayList;

public class mainAdapterAdmin extends RecyclerView.Adapter<mainAdapterAdmin.ViewHolder> {
    ArrayList<MainModelAdmin> mainModels;
    Context context;
    public mainAdapterAdmin(Context context, ArrayList<MainModelAdmin> mainModels){
        this.context=context;
        this.mainModels=mainModels;

    }


    @NonNull
    @Override
    public mainAdapterAdmin.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //create view
        View view= LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_item_admin,parent , false);

        return new mainAdapterAdmin.ViewHolder(view);
    }



    @Override
    public void onBindViewHolder(@NonNull mainAdapterAdmin.ViewHolder holder, final int position) {
        // set pic to image view
        holder.imageView.setImageResource(mainModels.get(position).getLangLogo());
        //set name to text view
        holder.textView.setText(mainModels.get(position).getLangName());

        //set onClick listener
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //create intent
                if(mainModels.get(position).getLangName().equals("إدارة حسابات المستفيدين المعلقة")){
                    Intent intent = new Intent(context, homepageAdmin.class);
                    context.startActivity(intent);
                }
                else if (mainModels.get(position).getLangName().equals("الملف الشخصي")){
                    Intent intent = new Intent(context, adminMainProfile.class);
                    context.startActivity(intent);
                }
                else if (mainModels.get(position).getLangName().equals("حسابات المستفيدين المبلغ عنها")){
                    Intent intent = new Intent(context, blockBen.class);
                    context.startActivity(intent);
                }

                else if (mainModels.get(position).getLangName().equals("تسجيل الخروج")){
                    Intent intent = new Intent(context, login.class);
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
            imageView = itemView.findViewById(R.id.image_view555);
            textView = itemView.findViewById(R.id.text_view555);
            linearLayout = itemView.findViewById(R.id.linear_layout555);


        }
    }
}








