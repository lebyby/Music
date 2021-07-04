package com.example.music.infogroup.report;

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
import com.example.music.infogroup.report.adapter.ReportAdapter;
import com.example.music.infogroup.report.database.ReportDatabase;

import java.util.ArrayList;
import java.util.Objects;

public class ReportFragment extends Fragment {

    private ReportDatabase mDatabase;
    private ArrayList<Report> allReport = new ArrayList<Report>();
    private ReportAdapter mAdapter;
    int group_id;

    public ReportFragment() {
    }

    public static ReportFragment newInstance() {
        return new ReportFragment();
    }


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_report, container, false);
        RecyclerView reportView = root.findViewById(R.id.text_report);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        reportView.setLayoutManager(linearLayoutManager);
        reportView.setHasFixedSize(true);
        mDatabase = new ReportDatabase(getActivity());
        assert getArguments() != null;
        group_id = getArguments().getInt("KEY_GROUP_ID");
        allReport = mDatabase.listReportGroup(group_id);



        if(allReport.size() > 0){
            reportView.setVisibility(View.VISIBLE);
            mAdapter = new ReportAdapter(getActivity(), allReport);
            reportView.setAdapter(mAdapter);

        }else {
            reportView.setVisibility(View.GONE);
            Toast.makeText(getActivity(), "В базе данных нет отчетов. Начните добавлять прямо сейчас", Toast.LENGTH_LONG).show();
        }
        FloatingActionButton fab = root.findViewById(R.id.fab_city);
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
        View subView = inflater.inflate(R.layout.add_report_layout, null);

        final EditText cityField = (EditText)subView.findViewById(R.id.enter_city);
        final EditText countField = (EditText)subView.findViewById(R.id.enter_count);


        AlertDialog.Builder builder = new AlertDialog.Builder(Objects.requireNonNull(getActivity()));
        builder.setTitle("Добавление нового отчета о концерте");
        builder.setView(subView);
        builder.create();

        builder.setPositiveButton("ДОБАВИТЬ ОТЧЕТ", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                final String city = cityField.getText().toString();
                final String count = countField.getText().toString();

                if(TextUtils.isEmpty(city)){
                    Toast.makeText(getActivity(), "Что-то пошло не так. Проверьте свои входные данные", Toast.LENGTH_LONG).show();
                }
                else{
                    Report newReport = new Report(city, count, group_id);
                    mDatabase.addReport(newReport);

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



