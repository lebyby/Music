package com.example.music.adapter;


import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.music.R;

public class GroupViewHolder extends RecyclerView.ViewHolder {

    public TextView name, place, year, button;
    public ImageView deleteGroup;
    public ImageView editGroup;

    public GroupViewHolder(View itemView) {
        super(itemView);
        button = (TextView)itemView.findViewById(R.id.group_button);
        name = (TextView)itemView.findViewById(R.id.group_name);
        place = (TextView)itemView.findViewById(R.id.place);
        year = (TextView)itemView.findViewById(R.id.years);
        deleteGroup = (ImageView)itemView.findViewById(R.id.delete_group);
        editGroup = (ImageView)itemView.findViewById(R.id.edit_group);
    }
}
