package com.example.rafad.ChatJava;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.app.Activity;
import android.app.Dialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.rafad.R;

import java.util.ArrayList;
import java.util.List;


/**

 */
public class ChatAdapter extends ArrayAdapter<ListChat> {

    Activity context;
    List<ListChat> items;
    Integer[] imageId = {
            R.drawable.girl2
    };


    public ChatAdapter(Activity mainActivity, ArrayList<ListChat> dataArrayList) {
        super(mainActivity, 0, dataArrayList);

        this.context = mainActivity;
        this.items = dataArrayList;
    }


    private class ViewHolder {

        TextView message, name;
        ImageView image;


    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ChatAdapter.ViewHolder holder = null;

        if (convertView == null) {

            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

            convertView = inflater.inflate(
                    R.layout.chat_list, parent, false);

            holder = new ChatAdapter.ViewHolder();
            holder.name = (TextView) convertView.findViewById(R.id.name);
            holder.message = (TextView) convertView.findViewById(R.id.message);
            holder.image = (ImageView) convertView.findViewById(R.id.image);

            convertView.setTag(holder);

        } else {
            holder = (ChatAdapter.ViewHolder) convertView.getTag();
        }

        ListChat productItems = items.get(position);


        holder.name.setText(productItems.getName());
        holder.message.setText(productItems.getMessage());

        holder.image.setImageResource(productItems.getImageId());

        return convertView;

    }


}