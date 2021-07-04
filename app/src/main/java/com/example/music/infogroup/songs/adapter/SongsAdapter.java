package com.example.music.infogroup.songs.adapter;


import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.Toast;

import com.example.music.infogroup.songs.Songs;
import com.example.music.R;
import com.example.music.infogroup.songs.database.SongsDatabase;

import java.util.ArrayList;

public class SongsAdapter extends RecyclerView.Adapter<SongsViewHolder> implements Filterable {

    private Context context;
    private ArrayList<Songs> listSongs;
    private ArrayList<Songs> mArrayList;

    private SongsDatabase mDatabase;

    public SongsAdapter(Context context, ArrayList<Songs> listSongs) {
        this.context = context;
        this.listSongs = listSongs;
        this.mArrayList = listSongs;
        mDatabase = new SongsDatabase(context);
    }

    @Override
    public SongsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.song_list_layout, parent, false);
        return new SongsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SongsViewHolder holder, int position) {
        final Songs songs = listSongs.get(position);

        holder.name.setText(songs.getName());
        holder.song_length.setText(songs.getLength());

        holder.editSong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editTaskDialog(songs);
            }
        });

        holder.deleteSong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //delete row from database

                mDatabase.deleteSong(songs.getId());

                //refresh the activity page.
                ((Activity)context).finish();
                context.startActivity(((Activity) context).getIntent());
            }
        });
    }

    @Override
    public Filter getFilter() {

        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {

                String charString = charSequence.toString();

                if (charString.isEmpty()) {

                    listSongs = mArrayList;
                } else {

                    ArrayList<Songs> filteredList = new ArrayList<>();

                    for (Songs songs : mArrayList) {

                        if (songs.getName().toLowerCase().contains(charString)) {

                            filteredList.add(songs);
                        }
                    }

                    listSongs = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = listSongs;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                listSongs = (ArrayList<Songs>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }


    @Override
    public int getItemCount() {
        return listSongs.size();
    }


    private void editTaskDialog(final Songs songs){
        LayoutInflater inflater = LayoutInflater.from(context);
        View subView = inflater.inflate(R.layout.add_song_layout, null);

        final EditText nameField = (EditText)subView.findViewById(R.id.enter_song);
        final EditText lengthField = (EditText)subView.findViewById(R.id.enter_length);

        if(songs != null){
            nameField.setText(songs.getName());
            lengthField.setText(String.valueOf(songs.getLength()));
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Редактирование песни");
        builder.setView(subView);
        builder.create();

        builder.setPositiveButton("ИЗМЕНИТЬ ПЕСНЮ", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                final String name = nameField.getText().toString();
                final String length = lengthField.getText().toString();

                if(TextUtils.isEmpty(name)){
                    Toast.makeText(context, "Что-то пошло не так. Проверьте свои входные данные", Toast.LENGTH_LONG).show();
                }
                else{
                    mDatabase.updateSongs(new Songs(songs.getId(), songs.getG_Id(), name, length));
                    //refresh the activity
                    ((Activity)context).finish();
                    context.startActivity(((Activity)context).getIntent());
                }
            }
        });

        builder.setNegativeButton("ОТМЕНА", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(context, "Отменено", Toast.LENGTH_LONG).show();
            }
        });
        builder.show();
    }
}
