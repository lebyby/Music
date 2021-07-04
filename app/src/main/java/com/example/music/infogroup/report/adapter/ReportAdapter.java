package com.example.music.infogroup.report.adapter;


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

import com.example.music.infogroup.report.Report;
import com.example.music.R;
import com.example.music.infogroup.report.adapter.ReportViewHolder;
import com.example.music.infogroup.report.database.ReportDatabase;

import java.util.ArrayList;

public class ReportAdapter extends RecyclerView.Adapter<ReportViewHolder> implements Filterable {

    private Context context;
    private ArrayList<Report> listReport;
    private ArrayList<Report> mArrayList;

    private ReportDatabase mDatabase;

    public ReportAdapter(Context context, ArrayList<Report> listReport) {
        this.context = context;
        this.listReport = listReport;
        this.mArrayList = listReport;
        mDatabase = new ReportDatabase(context);
    }

    @Override
    public ReportViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.report_list_layout, parent, false);
        return new ReportViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ReportViewHolder holder, int position) {
        final Report report = listReport.get(position);

        holder.city.setText("г. " + report.getCity());
        holder.count.setText(report.getCount() + " человек.");

        holder.editReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editTaskDialog(report);
            }
        });

        holder.deleteReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //delete row from database

                mDatabase.deleteReport(report.getId());

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

                    listReport = mArrayList;
                } else {

                    ArrayList<Report> filteredList = new ArrayList<>();

                    for (Report report : mArrayList) {

                        if (report.getCity().toLowerCase().contains(charString)) {

                            filteredList.add(report);
                        }
                    }

                    listReport = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = listReport;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                listReport = (ArrayList<Report>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }


    @Override
    public int getItemCount() {
        return listReport.size();
    }


    private void editTaskDialog(final Report report){
        LayoutInflater inflater = LayoutInflater.from(context);
        View subView = inflater.inflate(R.layout.add_report_layout, null);

        final EditText cityField = (EditText)subView.findViewById(R.id.enter_city);
        final EditText countField = (EditText)subView.findViewById(R.id.enter_count);

        if(report != null){
            cityField.setText(report.getCity());
            countField.setText(String.valueOf(report.getCount()));
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Редактирование отчета о концерте");
        builder.setView(subView);
        builder.create();

        builder.setPositiveButton("ИЗМЕНИТЬ ОТЧЕТ", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                final String city = cityField.getText().toString();
                final String count = countField.getText().toString();

                if(TextUtils.isEmpty(city)){
                    Toast.makeText(context, "Что-то пошло не так. Проверьте свои входные данные", Toast.LENGTH_LONG).show();
                }
                else{
                    mDatabase.updateReport(new Report(report.getId(), report.getG_Id(), city, count));
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
