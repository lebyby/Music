package com.example.music.infogroup;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;

import com.example.music.MainActivity;
import com.example.music.R;
import com.example.music.infogroup.composition.CompositionFragment;
import com.example.music.infogroup.report.ReportFragment;
import com.example.music.infogroup.songs.SongsFragment;

public class InfoGroupActivity extends AppCompatActivity {


    private int group_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_group);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        setTitle("Информация о группе");
        navView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.nav_songs:
                        loadFragment(SongsFragment.newInstance());
                        return true;
                    case R.id.nav_composition:
                        loadFragment(CompositionFragment.newInstance());
                        return true;
                    case R.id.nav_report:
                        loadFragment(ReportFragment.newInstance());
                        return true;
                }

                // Выделяем выбранный пункт меню в шторке
                menuItem.setChecked(true);
                // Выводим выбранный пункт в заголовке
                setTitle(menuItem.getTitle());

                return false;
            }
        });

        navView.setSelectedItemId(R.id.nav_songs);


        FloatingActionButton back = (FloatingActionButton) findViewById(R.id.back_song);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(InfoGroupActivity.this, MainActivity.class);
                startActivity(intent1);
            }
        });

    }



    private void loadFragment(Fragment fragment) {
        Bundle bundle = new Bundle();
        bundle.putInt("KEY_GROUP_ID", getIntent().getExtras().getInt("KEY_ID"));
        fragment.setArguments(bundle);

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.container, fragment).commit();
    }
}