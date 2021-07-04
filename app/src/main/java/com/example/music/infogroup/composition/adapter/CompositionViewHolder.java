package com.example.music.infogroup.composition.adapter;


import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.music.R;

public class CompositionViewHolder extends RecyclerView.ViewHolder {

    public TextView surname,forename, role;
    public ImageView deleteComposition;
    public ImageView editComposition;

    public CompositionViewHolder(View itemView) {
        super(itemView);
        surname = (TextView)itemView.findViewById(R.id.surname);
        forename = (TextView)itemView.findViewById(R.id.forename);
        role = (TextView)itemView.findViewById(R.id.role);
        deleteComposition = (ImageView)itemView.findViewById(R.id.delete_composition);
        editComposition = (ImageView)itemView.findViewById(R.id.edit_composition);
    }
}
