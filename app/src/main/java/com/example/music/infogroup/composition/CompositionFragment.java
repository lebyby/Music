package com.example.music.infogroup.composition;

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
import com.example.music.infogroup.composition.adapter.CompositionAdapter;
import com.example.music.infogroup.composition.database.CompositionDatabase;

import java.util.ArrayList;
import java.util.Objects;

public class CompositionFragment extends Fragment {

    private CompositionDatabase mDatabase;
    private ArrayList<Composition> allComposition = new ArrayList<Composition>();
    private CompositionAdapter mAdapter;
    int group_id;

    public CompositionFragment() {
    }

    public static CompositionFragment newInstance() {
        return new CompositionFragment();
    }


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_composition, container, false);
        RecyclerView compositionView = root.findViewById(R.id.text_composition);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        compositionView.setLayoutManager(linearLayoutManager);
        compositionView.setHasFixedSize(true);
        mDatabase = new CompositionDatabase(getActivity());
        assert getArguments() != null;
        group_id = getArguments().getInt("KEY_GROUP_ID");
        allComposition = mDatabase.listCompositionGroup(group_id);



        if(allComposition.size() > 0){
            compositionView.setVisibility(View.VISIBLE);
            mAdapter = new CompositionAdapter(getActivity(), allComposition);
            compositionView.setAdapter(mAdapter);

        }else {
            compositionView.setVisibility(View.GONE);
            Toast.makeText(getActivity(), "В базе данных нет музыкантов. Начните добавлять прямо сейчас", Toast.LENGTH_LONG).show();
        }
        FloatingActionButton fab = root.findViewById(R.id.fab_people);
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
        View subView = inflater.inflate(R.layout.add_composition_layout, null);

        final EditText snameField = (EditText)subView.findViewById(R.id.enter_surname);
        final EditText fnameField = (EditText)subView.findViewById(R.id.enter_forename);
        final EditText roleField = (EditText)subView.findViewById(R.id.enter_role);


        AlertDialog.Builder builder = new AlertDialog.Builder(Objects.requireNonNull(getActivity()));
        builder.setTitle("Добавление нового музыканта");
        builder.setView(subView);
        builder.create();

        builder.setPositiveButton("ДОБАВИТЬ МУЗЫКАНТА", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                final String sname = snameField.getText().toString();
                final String fname = fnameField.getText().toString();
                final String role = roleField.getText().toString();

                if(TextUtils.isEmpty(sname)){
                    Toast.makeText(getActivity(), "Что-то пошло не так. Проверьте свои входные данные", Toast.LENGTH_LONG).show();
                }
                else{
                    Composition newComposition = new Composition(sname, fname, role, group_id);
                    mDatabase.addComposition(newComposition);

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



