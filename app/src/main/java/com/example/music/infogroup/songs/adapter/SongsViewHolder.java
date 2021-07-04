package com.example.music.infogroup.songs.adapter;


import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.music.R;

public class SongsViewHolder extends RecyclerView.ViewHolder {

    public TextView name,song_length;
    public ImageView deleteSong;
    public ImageView editSong;

    public SongsViewHolder(View itemView) {
        super(itemView);
        name = (TextView)itemView.findViewById(R.id.song_name);
        song_length = (TextView)itemView.findViewById(R.id.song_length);
        deleteSong = (ImageView)itemView.findViewById(R.id.delete_song);
        editSong = (ImageView)itemView.findViewById(R.id.edit_song);
    }
}
