package com.example.music.infogroup.songs;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.widget.Toast;


import com.example.music.MainActivity;
import com.example.music.R;
import com.example.music.infogroup.InfoGroupActivity;
import com.example.music.infogroup.songs.adapter.SongsAdapter;
import com.example.music.infogroup.songs.database.SongsDatabase;

import java.util.ArrayList;
import java.util.Objects;

public class SongsFragment extends Fragment {

    private SongsDatabase mDatabase;
    private ArrayList<Songs> allSongs = new ArrayList<Songs>();
    private SongsAdapter mAdapter;
    int group_id;


    public SongsFragment() {
    }

    public static SongsFragment newInstance() {
        return new SongsFragment();
    }


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_songs, container, false);
        RecyclerView songView = root.findViewById(R.id.text_songs);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        songView.setLayoutManager(linearLayoutManager);
        songView.setHasFixedSize(true);
        mDatabase = new SongsDatabase(getActivity());
        assert getArguments() != null;
        group_id = getArguments().getInt("KEY_GROUP_ID");
        allSongs = mDatabase.listSongsGroup(group_id);


        if(allSongs.size() > 0){
            songView.setVisibility(View.VISIBLE);
            mAdapter = new SongsAdapter(getActivity(), allSongs);
            songView.setAdapter(mAdapter);

        }else {
            songView.setVisibility(View.GONE);
            Toast.makeText(getActivity(), "В базе данных нет песен. Начните добавлять прямо сейчас", Toast.LENGTH_LONG).show();
        }
        FloatingActionButton fab = root.findViewById(R.id.fab_song);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addTaskDialog();
            }
        });

        return root;
    }

    private void addTaskDialog(){
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        View subView = inflater.inflate(R.layout.add_song_layout, null);

        final EditText nameField = (EditText)subView.findViewById(R.id.enter_song);
        final EditText lengthField = (EditText)subView.findViewById(R.id.enter_length);


        AlertDialog.Builder builder = new AlertDialog.Builder(Objects.requireNonNull(getActivity()));
        builder.setTitle("Добавление новую песню");
        builder.setView(subView);
        builder.create();

        builder.setPositiveButton("ДОБАВИТЬ ПЕСНЮ", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                final String name = nameField.getText().toString();
                final String length_s = lengthField.getText().toString();

                if(TextUtils.isEmpty(name)){
                    Toast.makeText(getActivity(), "Что-то пошло не так. Проверьте свои входные данные", Toast.LENGTH_LONG).show();
                }
                else{
                    Songs newSong = new Songs(name,length_s, group_id);
                    mDatabase.addSongs(newSong);

                    getActivity().finish();
                    getActivity().startActivity((getActivity()).getIntent());
                }
            }
        });

        builder.setNegativeButton("ОТМЕНА", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getActivity(), "Отменено", Toast.LENGTH_LONG).show();
            }
        });
        builder.show();
    }



}



