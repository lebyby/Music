package com.example.music;

import android.content.Context;
import android.content.DialogInterface;
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
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collection;

import com.example.music.adapter.GroupAdapter;
import com.example.music.database.SqliteDatabase;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    private SqliteDatabase mDatabase;
    private ArrayList<Groups> allGroups=new ArrayList<>();
    private GroupAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FrameLayout fLayout = (FrameLayout) findViewById(R.id.activity_to_do);

        RecyclerView groupView = (RecyclerView)findViewById(R.id.product_list);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        groupView.setLayoutManager(linearLayoutManager);
        groupView.setHasFixedSize(true);
        mDatabase = new SqliteDatabase(this);
        allGroups = mDatabase.listGroups();

        if(allGroups.size() > 0){
            groupView.setVisibility(View.VISIBLE);
            mAdapter = new GroupAdapter(this, allGroups);
            groupView.setAdapter(mAdapter);

        }else {
            groupView.setVisibility(View.GONE);
            Toast.makeText(this, "В базе данных нет никакой группы. Начните добавлять прямо сейчас", Toast.LENGTH_LONG).show();
        }
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addTaskDialog();
            }
        });


    }

    private void addTaskDialog(){
        LayoutInflater inflater = LayoutInflater.from(this);
        View subView = inflater.inflate(R.layout.add_group_layout, null);

        final EditText nameField = (EditText)subView.findViewById(R.id.enter_name);
        final EditText placeFieid = (EditText)subView.findViewById(R.id.enter_place);
        final EditText yearFieid = (EditText)subView.findViewById(R.id.enter_year);


        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Добавление новой группы");
        builder.setView(subView);
        builder.create();

        builder.setPositiveButton("ДОБАВИТЬ ГРУППУ", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                final String name = nameField.getText().toString();
                final String place = placeFieid.getText().toString();
                final String year = yearFieid.getText().toString();

                if(TextUtils.isEmpty(name)){
                    Toast.makeText(MainActivity.this, "Что-то пошло не так. Проверьте свои входные данные", Toast.LENGTH_LONG).show();
                }
                else{
                    Groups newGroup = new Groups(name, place, year);
                    mDatabase.addGroups(newGroup);

                    finish();
                    startActivity(getIntent());
                }
            }
        });

        builder.setNegativeButton("ОТМЕНА", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(MainActivity.this, "Отменено", Toast.LENGTH_LONG).show();
            }
        });
        builder.show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mDatabase != null){
            mDatabase.close();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.search, menu);

        MenuItem search = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(search);
        search(searchView);
        return true;
    }

    private void search(SearchView searchView) {

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (mAdapter!=null)
                mAdapter.getFilter().filter(newText);
                return true;
            }
        });
    }
}

