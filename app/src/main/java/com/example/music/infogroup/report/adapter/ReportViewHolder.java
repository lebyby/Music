package com.example.music.infogroup.report.adapter;


import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.music.R;

public class ReportViewHolder extends RecyclerView.ViewHolder {

    public TextView city, count;
    public ImageView deleteReport;
    public ImageView editReport;

    public ReportViewHolder(View itemView) {
        super(itemView);
        city = (TextView)itemView.findViewById(R.id.city_name);
        count = (TextView)itemView.findViewById(R.id.count);
        deleteReport = (ImageView)itemView.findViewById(R.id.delete_report);
        editReport = (ImageView)itemView.findViewById(R.id.edit_report);
    }
}
