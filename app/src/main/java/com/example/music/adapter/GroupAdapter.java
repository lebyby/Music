package com.example.music.adapter;


import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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

import java.util.ArrayList;

import com.example.music.Groups;
import com.example.music.MainActivity;
import com.example.music.R;
import com.example.music.database.SqliteDatabase;
import com.example.music.infogroup.InfoGroupActivity;

public class GroupAdapter extends RecyclerView.Adapter<GroupViewHolder> implements Filterable {

    private Context context;
    private ArrayList<Groups> listGroups;
    private ArrayList<Groups> mArrayList;

    private SqliteDatabase mDatabase;

    public GroupAdapter(Context context, ArrayList<Groups> listGroups) {
        this.context = context;
        this.listGroups = listGroups;
        this.mArrayList=listGroups;
        mDatabase = new SqliteDatabase(context);
    }


    @Override
    public GroupViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.group_list_layout, parent, false);
        return new GroupViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final GroupViewHolder holder, final int position) {
        final Groups group = listGroups.get(position);

        holder.name.setText(group.getName());
        holder.place.setText(group.getPlace() + " место.");
        holder.year.setText(group.getYear() + " год.");

        holder.editGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editTaskDialog(group);
            }
        });

        holder.deleteGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //delete row from database

                mDatabase.deleteGroup(group.getId());

                //refresh the activity page.
                ((Activity)context).finish();
                context.startActivity(((Activity) context).getIntent());

            }
        });
        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, InfoGroupActivity.class);
                intent.putExtra("KEY_ID", group.getId());
                context.startActivity(intent);
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

                    listGroups = mArrayList;
                } else {

                    ArrayList<Groups> filteredList = new ArrayList<>();

                    for (Groups groups : mArrayList) {

                        if (groups.getName().toLowerCase().contains(charString)) {

                            filteredList.add(groups);
                        }
                    }

                    listGroups = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = listGroups;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                listGroups = (ArrayList<Groups>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }


    @Override
    public int getItemCount() {
        return listGroups.size();
    }


    private void editTaskDialog(final Groups groups){
        LayoutInflater inflater = LayoutInflater.from(context);
        View subView = inflater.inflate(R.layout.add_group_layout, null);

        final EditText nameField = (EditText)subView.findViewById(R.id.enter_name);
        final EditText placeField = (EditText)subView.findViewById(R.id.enter_place);
        final EditText yearField = (EditText)subView.findViewById(R.id.enter_year);

        if(groups != null){
            nameField.setText(groups.getName());
            placeField.setText(String.valueOf(groups.getPlace()));
            yearField.setText(String.valueOf(groups.getYear()));
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Редактирование группы");
        builder.setView(subView);
        builder.create();

        builder.setPositiveButton("ИЗМЕНИТЬ ГРУППУ", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                final String name = nameField.getText().toString();
                final String place = placeField.getText().toString();
                final String year = yearField.getText().toString();

                if(TextUtils.isEmpty(name)){
                    Toast.makeText(context, "Что-то пошло не так. Проверьте свои входные данные", Toast.LENGTH_LONG).show();
                }
                else{
                    mDatabase.updateGroups(new Groups(groups.getId(), name, place, year));
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
